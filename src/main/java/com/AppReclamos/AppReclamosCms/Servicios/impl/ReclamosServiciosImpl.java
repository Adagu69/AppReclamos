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
        // La lógica de filtrado inicial se mantiene
        String estadoFinal = (estado == null || estado.equalsIgnoreCase("TODOS")) ? null : estado.toUpperCase();
        String queryFinal = (query == null || query.isBlank()) ? null : query;
        String periodoFinal = null;
        if (anio != null && mes != null) {
            periodoFinal = String.format("%d%02d", anio, mes);
        }

        List<Reclamos> reclamos = reclamosRepo.findReclamosByFilters(estadoFinal, buscarPor, queryFinal, periodoFinal);

        // --- ¡LÓGICA DE MAPEO SIMPLIFICADA! ---
        return reclamos.stream()
                .map(r -> {
                    // La búsqueda manual de 'usuario' se elimina.

                    // La lógica para encontrar el detalle, gestión, etc., más reciente se mantiene.
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

                    // ¡La llamada al mapper es más limpia! Ya no necesita el parámetro de la persona.
                    return reclamoMapper.toTableDTO(r, detalleRec, gestion, resultadoRec, medidaRec);
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ReclamoDTO buscarPorId(Integer id) {
        // Este método no necesita cambios.
        return reclamosRepo.findById(id)
                .map(reclamoMapper::toDetailDTO)
                .orElse(null);
    }


    @Override
    @Transactional
    public ReclamoDTO guardarDesdeDTO(ReclamoDTO dto) {
        Reclamos reclamoEntity;

        if (dto.getIdReclamo() != null && dto.getIdReclamo() > 0) {
            // --- LÓGICA DE ACTUALIZACIÓN ---
            reclamoEntity = reclamosRepo.findById(dto.getIdReclamo())
                    .orElseThrow(() -> new EntityNotFoundException("Reclamo no encontrado con id: " + dto.getIdReclamo()));

            // Limpiamos las colecciones para reemplazarlas con las del DTO
            reclamoEntity.getDetalles().clear();
            reclamoEntity.getResultados().clear();

            reclamoMapper.updateFromDto(dto, reclamoEntity);

        } else {
            // --- LÓGICA DE CREACIÓN ---
            reclamoEntity = reclamoMapper.toEntity(dto);
        }

        // Asignamos el tipo a cada persona
        if (reclamoEntity.getPresentante() != null) {
            reclamoEntity.getPresentante().setTipoPersona(TipoPersona.PRESENTANTE);
        }
        if (reclamoEntity.getAfectado() != null) {
            reclamoEntity.getAfectado().setTipoPersona(TipoPersona.USUARIO);
        }

        // --- ¡CORRECCIÓN FINAL Y CRUCIAL! ---
        // Establecemos la relación bidireccional para TODAS las entidades hijas.
        // Esto asegura que la FK 'id_reclamo' nunca sea nula.
        final Reclamos finalEntity = reclamoEntity; // Necesario para usar dentro de lambdas

        if (finalEntity.getDetalles() != null) {
            finalEntity.getDetalles().forEach(detalle -> detalle.setReclamo(finalEntity));
        }
        if (finalEntity.getResultados() != null) {
            finalEntity.getResultados().forEach(resultado -> resultado.setReclamo(finalEntity));
        }
        if (finalEntity.getGestion() != null) {
            finalEntity.getGestion().setReclamo(finalEntity);
        }
        // ... añadir aquí para 'medidas' si también es una relación que manejas en este DTO.

        // Guardamos la entidad principal. Cascade se encargará del resto.
        Reclamos reclamoGuardado = reclamosRepo.save(reclamoEntity);
        log.info("Reclamo guardado/actualizado con ID: {}", reclamoGuardado.getIdReclamo());

        return reclamoMapper.toDetailDTO(reclamoGuardado);
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
//                dto.setFechaReclamo(row.getCell(1).getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

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


