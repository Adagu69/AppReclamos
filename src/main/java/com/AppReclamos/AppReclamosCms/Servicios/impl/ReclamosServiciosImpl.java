package com.AppReclamos.AppReclamosCms.Servicios.impl;

import com.AppReclamos.AppReclamosCms.Modelos.*;
import com.AppReclamos.AppReclamosCms.Modelos.Enums.EstadoReclamo;
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
        // Procesar los parámetros de filtrado
        EstadoReclamo estadoEnum = null;
        if (estado != null && !estado.equalsIgnoreCase("TODOS")) {
            try {
                estadoEnum = EstadoReclamo.valueOf(estado.toUpperCase());
            } catch (IllegalArgumentException e) {
                log.warn("Estado inválido recibido: {}. Usando null para mostrar todos.", estado);
                estadoEnum = null;
            }
        }

        String queryFinal = (query == null || query.trim().isEmpty()) ? null : query.trim();

        // Construir el período en formato YYYYMM si ambos parámetros están presentes
        String periodoFinal = null;
        if (anio != null && mes != null) {
            periodoFinal = String.format("%d%02d", anio, mes);
        }

        // Validar y ajustar el tipo de búsqueda
        if (buscarPor == null || buscarPor.trim().isEmpty()) {
            buscarPor = "TODO";
        }

        log.info("Filtros aplicados - Estado: {}, BuscarPor: {}, Query: {}, Periodo: {}",
                estadoEnum, buscarPor, queryFinal, periodoFinal);

        // Ejecutar la consulta con los filtros (ahora pasamos el enum)
        List<Reclamos> reclamos = reclamosRepo.findReclamosByFilters(estadoEnum, buscarPor, queryFinal, periodoFinal);

        // Mapear a DTOs para la tabla
        return reclamos.stream()
                .map(r -> {
                    // Obtener el detalle más reciente
                    DetalleReclamo detalleRec = r.getDetalles().stream()
                            .max(Comparator.comparing(DetalleReclamo::getFechaCreacion, Comparator.nullsLast(Comparator.naturalOrder())))
                            .orElse(null);

                    // Obtener la gestión
                    GestionReclamo gestion = r.getGestion();

                    // Obtener el resultado más reciente
                    ResultadoNotificacion resultadoRec = r.getResultados().stream()
                            .max(Comparator.comparing(ResultadoNotificacion::getFechaResultado, Comparator.nullsLast(Comparator.naturalOrder())))
                            .orElse(null);

                    // Obtener la medida más reciente
                    MedidasAdoptadas medidaRec = r.getMedidas().stream()
                            .max(Comparator.comparing(MedidasAdoptadas::getFechaInicioImplementacion, Comparator.nullsLast(Comparator.naturalOrder())))
                            .orElse(null);

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

        // 1. LÓGICA DE NEGOCIO PREVIA AL GUARDADO
        // ==========================================

        // Criterio para Código UGIPRESS: Se auto-asigna si el declarante es UGIPRESS o IAFAS.
        TipoDeclarante tipo = dto.getTipoDeclarante();
        if (tipo == TipoDeclarante.UGIPRESS || tipo == TipoDeclarante.IAFAS) {
            dto.setCodigoUgipress(dto.getCodigoDeclarante());
            log.info("Auto-asignando Código UGIPRESS desde Código Declarante para tipo: {}", tipo);
        }

        // Criterio para Código Reclamo (se formatea)
        // Asumimos que el JS ya lo formatea como "código-correlativo", pero el backend lo asegura.
        // Aquí se podría añadir una lógica más compleja si el correlativo debe generarse en el servidor.


        // 2. LÓGICA DE PERSISTENCIA (Creación o Actualización)
        // ========================================================
        Reclamos reclamoEntity;
        if (dto.getIdReclamo() != null && dto.getIdReclamo() > 0) {
            // --- LÓGICA DE ACTUALIZACIÓN ---
            reclamoEntity = reclamosRepo.findById(dto.getIdReclamo())
                    .orElseThrow(() -> new EntityNotFoundException("Reclamo no encontrado con id: " + dto.getIdReclamo()));

            reclamoEntity.getDetalles().clear();
            reclamoEntity.getResultados().clear();
            reclamoMapper.updateFromDto(dto, reclamoEntity);

        } else {
            // --- LÓGICA DE CREACIÓN ---
            reclamoEntity = reclamoMapper.toEntity(dto);

            // Aquí podrías añadir la lógica para generar un correlativo único si es necesario.
            // Ejemplo simple:
            // long correlativo = reclamosRepo.count() + 1;
            // String codigoReclamoGenerado = dto.getCodigoDeclarante() + "-" + correlativo;
            // reclamoEntity.setCodigoReclamo(codigoReclamoGenerado);
        }

        // 3. ASIGNACIÓN DE TIPOS Y RELACIONES (Lógica que ya teníamos)
        // =============================================================

        // Asignamos el tipo a cada persona
        if (reclamoEntity.getPresentante() != null) {
            reclamoEntity.getPresentante().setTipoPersona(TipoPersona.PRESENTANTE);
        }
        if (reclamoEntity.getAfectado() != null) {
            reclamoEntity.getAfectado().setTipoPersona(TipoPersona.USUARIO);
        }

        // Establecemos las relaciones bidireccionales
        final Reclamos finalEntity = reclamoEntity;
        if (finalEntity.getDetalles() != null) {
            finalEntity.getDetalles().forEach(detalle -> detalle.setReclamo(finalEntity));
        }
        if (finalEntity.getResultados() != null) {
            finalEntity.getResultados().forEach(resultado -> resultado.setReclamo(finalEntity));
        }
        if (finalEntity.getGestion() != null) {
            finalEntity.getGestion().setReclamo(finalEntity);
        }

        // 4. GUARDADO FINAL
        // ==================
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
