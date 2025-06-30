package com.AppReclamos.AppReclamosCms.Servicios.impl;

import com.AppReclamos.AppReclamosCms.Modelos.*;
import com.AppReclamos.AppReclamosCms.Modelos.Enums.TipoPersona;
import com.AppReclamos.AppReclamosCms.Repositorios.ReclamosRepositorio;
import com.AppReclamos.AppReclamosCms.Servicios.interfaces.IReclamosServicios;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Comparator;
import java.util.List;

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
        return reclamosRepo.findAll().stream()
                .map(r -> {
                    DetalleReclamo detalleRec = r.getDetalles() == null
                            ? null
                            : r.getDetalles().stream()
                            .max(Comparator.comparing(DetalleReclamo::getFechaCreacion))
                            .orElse(null);
                    PersonaReclamo usuario = r.getPersonas() == null
                            ? null
                            : r.getPersonas().stream()
                            .filter(p -> p.getTipoPersona() == TipoPersona.USUARIO)
                            .findFirst()
                            .orElse(null);
                    return reclamoMapper.toTableDTO(r, detalleRec, usuario);
                })
                .filter(r -> estado == null || estado.equalsIgnoreCase("TODOS") || r.getEstado().name().equalsIgnoreCase(estado))
                .filter(r -> {
                    if (buscarPor == null || buscarPor.equalsIgnoreCase("TODO") || query == null || query.isBlank()) return true;
                    return switch (buscarPor.toUpperCase()) {
                        case "CODIGO" -> r.getCodigoReclamo() != null && r.getCodigoReclamo().contains(query);
                        case "NOMBRE" -> r.getNombreAfectado() != null && r.getNombreAfectado().toLowerCase().contains(query.toLowerCase());
                        default -> true;
                    };
                })
                .filter(r -> anio == null || (r.getFechaReclamo() != null && r.getFechaReclamo().getYear() == anio))
                .filter(r -> mes == null || (r.getFechaReclamo() != null && r.getFechaReclamo().getMonthValue() == mes))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ReclamoDTO buscarPorId(Integer id) {
        return reclamosRepo.findById(id)
                .map(reclamoMapper::toDetailDTO)
                .orElse(null);
    }

    @Override
    @Transactional
    public ReclamoDTO guardarDesdeDTO(ReclamoDTO dto) {
        Reclamos entity = reclamoMapper.toEntity(dto);

        // Forzar tipos de persona
        if (entity.getPersonas() != null) {
            log.info("---[SERVICIO] Forzando asignación manual de TipoPersona.");
            if (!entity.getPersonas().isEmpty()) {
                entity.getPersonas().get(0).setTipoPersona(TipoPersona.PRESENTANTE);
                log.info("---[SERVICIO] personas[0].tipoPersona = PRESENTANTE");
            }
            if (entity.getPersonas().size() > 1) {
                entity.getPersonas().get(1).setTipoPersona(TipoPersona.USUARIO);
                log.info("---[SERVICIO] personas[1].tipoPersona = USUARIO");
            }
        }

        // Relaciones bidireccionales
        if (entity.getPersonas() != null)    entity.getPersonas().forEach(p -> p.setReclamo(entity));
        if (entity.getDetalles() != null)    entity.getDetalles().forEach(d -> d.setReclamo(entity));
        if (entity.getGestion() != null)     entity.getGestion().setReclamo(entity);
        if (entity.getResultados() != null)  entity.getResultados().forEach(r -> r.setReclamo(entity));

        Reclamos saved = reclamosRepo.save(entity);
        return reclamoMapper.toDetailDTO(saved);
    }

    @Override
    @Transactional
    public void eliminarPorId(Integer id) {
        reclamosRepo.deleteById(id);
    }
}


