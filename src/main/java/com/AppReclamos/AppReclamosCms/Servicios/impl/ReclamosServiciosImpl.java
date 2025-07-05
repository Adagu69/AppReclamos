package com.AppReclamos.AppReclamosCms.Servicios.impl;

import com.AppReclamos.AppReclamosCms.Modelos.*;
import com.AppReclamos.AppReclamosCms.Modelos.Enums.TipoDeclarante;
import com.AppReclamos.AppReclamosCms.Modelos.Enums.TipoPersona;
import com.AppReclamos.AppReclamosCms.Repositorios.ReclamosRepositorio;
import com.AppReclamos.AppReclamosCms.Servicios.interfaces.IReclamosServicios;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReclamosServiciosImpl implements IReclamosServicios {

    // CORRECCIÓN 1: Inyección por constructor (final)
    private final ReclamosRepositorio reclamosRepo;
    private final ReclamoMapper reclamoMapper;
    private static final Logger log = LoggerFactory.getLogger(ReclamosServiciosImpl.class);
    // --- IMPLEMENTACIÓN DE LA INTERFAZ ---

    @Override
    @Transactional(readOnly = true)
    public List<ReclamoTablaDTO> buscarFiltrado(String estado, String buscarPor, String query, Integer anio, Integer mes) {
        // 1. Normalizar los parámetros para la consulta
        String estadoFinal = (estado == null || estado.equalsIgnoreCase("TODOS")) ? null : estado.toUpperCase();
        String queryFinal = (query == null || query.isBlank()) ? null : "%" + query + "%";

        // 2. Llamar al método eficiente del repositorio
        List<Reclamos> reclamos = reclamosRepo.findReclamosByFilters(estadoFinal, buscarPor, queryFinal, anio, mes);

        // 3. Mapear los resultados (ya filtrados) al DTO
        return reclamos.stream()
                .map(r -> {
                    PersonaReclamo usuario = r.getPersonas().stream()
                            .filter(p -> p.getTipoPersona() == TipoPersona.USUARIO)
                            .findFirst().orElse(null);

                    DetalleReclamo detalleRec = r.getDetalles().stream()
                            .max(Comparator.comparing(DetalleReclamo::getFechaCreacion, Comparator.nullsLast(Comparator.naturalOrder())))
                            .orElse(null);

                    GestionReclamo gestion = r.getGestion();

                    ResultadoNotificacion resultadoRec = r.getResultados().stream()
                            .max(Comparator.comparing(ResultadoNotificacion::getFechaResultado, Comparator.nullsLast(Comparator.naturalOrder())))
                            .orElse(null);

                    MedidasAdoptadas medidaRec = r.getMedidas().stream()
                            .max(Comparator.comparing(MedidasAdoptadas::getFechaInicioImplementacion, Comparator.nullsLast(Comparator.naturalOrder())))
                            .orElse(null);

                    return reclamoMapper.toTableDTO(r, usuario, detalleRec, gestion, resultadoRec, medidaRec);
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ReclamoDTO buscarPorId(Integer id) {
        return reclamosRepo.findById(id) // Asumiendo que findById carga todo lo necesario o tienes un método findByIdWithDetails
                .map(reclamoMapper::toDetailDTO)
                .orElse(null);
    }

    @Override
    @Transactional
    public ReclamoDTO guardarDesdeDTO(ReclamoDTO dto) {
        Reclamos entity;
        if (dto.getIdReclamo() != null) {
            entity = reclamosRepo.findById(dto.getIdReclamo())
                    .orElseThrow(() -> new EntityNotFoundException("Reclamo no encontrado con id: " + dto.getIdReclamo()));
        } else {
            entity = reclamoMapper.toEntity(dto);
        }

        // Forzar tipos de persona
        if (entity.getPersonas() != null && !entity.getPersonas().isEmpty()) {
            List<PersonaReclamo> personasList = new ArrayList<>(entity.getPersonas());

            personasList.get(0).setTipoPersona(TipoPersona.PRESENTANTE);
            log.info("---[SERVICIO] personas[0].tipoPersona = PRESENTANTE");

            if (personasList.size() > 1) {
                personasList.get(1).setTipoPersona(TipoPersona.USUARIO);
                log.info("---[SERVICIO] personas[1].tipoPersona = USUARIO");
            }
        }

        // Relaciones bidireccionales
        if (entity.getPersonas() != null) entity.getPersonas().forEach(p -> p.setReclamo(entity));
        if (entity.getDetalles() != null) entity.getDetalles().forEach(d -> d.setReclamo(entity));
        if (entity.getGestion() != null) entity.getGestion().setReclamo(entity);
        if (entity.getResultados() != null) entity.getResultados().forEach(r -> r.setReclamo(entity));

        Reclamos saved = reclamosRepo.save(entity);
        return reclamoMapper.toDetailDTO(saved);
    }

    @Override
    @Transactional
    public void eliminarPorId(Integer id) {
        reclamosRepo.deleteById(id);
    }

    @Override
    @Transactional
    public void procesarTrama(MultipartFile file) throws IOException {
        try (InputStream is = file.getInputStream(); Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheet("HOJA DE RECLAMACIÓN");
            if (sheet == null) {
                throw new IllegalArgumentException("La hoja 'HOJA DE RECLAMACIÓN' no fue encontrada en el archivo.");
            }

            // Omitir las 3 primeras filas de cabecera
            for (int i = 3; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue; // Salta filas vacías

                // Crear el DTO a partir de la fila del Excel
                ReclamoDTO dto = new ReclamoDTO();

                // Mapeo de columnas a DTO (ajustar índices según tu Excel)
                // Columna B: FECHA RECLAMO
                dto.setFechaReclamo(row.getCell(1).getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

                // Columna D: TIPO DECLARANTE
                String tipoDeclaranteStr = row.getCell(3).getStringCellValue();
                dto.setTipoDeclarante(TipoDeclarante.valueOf(tipoDeclaranteStr.toUpperCase()));

                dto.setCodigoDeclarante(String.valueOf((int)row.getCell(4).getNumericCellValue()));

                // Guardar el reclamo
                this.guardarDesdeDTO(dto);
            }
        }
    }
    }


