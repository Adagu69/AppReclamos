package com.AppReclamos.AppReclamosCms.Servicios.impl;

import com.AppReclamos.AppReclamosCms.Modelos.*;
import com.AppReclamos.AppReclamosCms.Modelos.Enums.TipoPersona;
import com.AppReclamos.AppReclamosCms.Repositorios.ReclamosRepositorio;
import com.AppReclamos.AppReclamosCms.Servicios.interfaces.IReclamosServicios;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReclamosServiciosImpl implements IReclamosServicios {

    private final ReclamosRepositorio reclamosRepo;
    private final ReclamoMapper      mapper;


    /* ========== LISTAR TODOS PARA TABLA ========== */
    @Override
    public List<ReclamoTablaDTO> listarTodosParaTabla() {

        List<Reclamos> lista = reclamosRepo.findAll();

        return lista.stream()
                .map(r -> {
                    /* Detalle más reciente */
                    DetalleReclamo detalleRec = r.getDetalles() == null ? null :
                            r.getDetalles().stream()
                                    .max(Comparator.comparing(DetalleReclamo::getFechaCreacion))
                                    .orElse(null);

                    /* Usuario afectado */
                    PersonaReclamo usuario = r.getPersonas() == null ? null :
                            r.getPersonas().stream()
                                    .filter(p -> p.getTipoPersona() == TipoPersona.USUARIO)   // ← enum global
                                    .findFirst()
                                    .orElse(null);

                    return mapper.toTableDTO(r, detalleRec, usuario);
                })
                .collect(Collectors.toList());
    }

    /* ========== FILTRO DINÁMICO PARA TABLA ========== */
    @Override
    public List<ReclamoTablaDTO> buscarFiltrado(String estado,
                                                String buscarPor,
                                                String query,
                                                Integer anio,
                                                Integer mes) {

        List<ReclamoTablaDTO> all = listarTodosParaTabla();

        return all.stream()
                .filter(r -> (estado == null
                        || estado.equalsIgnoreCase("TODOS")
                        || r.getEstado().name().equalsIgnoreCase(estado)))
                .filter(r -> {
                    if (buscarPor == null || buscarPor.equalsIgnoreCase("TODO") || query == null || query.isBlank())
                        return true;

                    return switch (buscarPor.toUpperCase()) {
                        case "CODIGO"  -> r.getCodigoReclamo() != null
                                && r.getCodigoReclamo().contains(query);
                        case "NOMBRE"  -> r.getNombreAfectado() != null
                                && r.getNombreAfectado().toLowerCase().contains(query.toLowerCase());
                        default        -> true;
                    };
                })
                .filter(r -> anio == null
                        || (r.getFechaReclamo() != null
                        && r.getFechaReclamo().getYear() == anio))
                .filter(r -> mes == null
                        || (r.getFechaReclamo() != null
                        && r.getFechaReclamo().getMonthValue() == mes))
                .toList();
    }

    /* ========== LISTAR TODOS DETALLE DTO ========== */
    @Override
    public List<ReclamoDTO> listarTodosDTO() {
        return reclamosRepo.findAll()
                .stream()
                .map(mapper::toDetailDTO)
                .toList();
    }

    /* ========== BUSCAR POR ID ========== */
    @Override
    public ReclamoDTO buscarDTOporId(Integer id) {
        return reclamosRepo.findById(id)
                .map(mapper::toDetailDTO)
                .orElse(null);
    }

    /* ========== GUARDAR / ACTUALIZAR DESDE DTO ========== */
    @Override
    @Transactional
    public void guardarDesdeDTO(ReclamoDTO dto) {

        Reclamos entity = mapper.toEntity(dto);

        // Bidireccionales: setear padre en hijos (ejemplo con personas)
        if (entity.getPersonas() != null) {
            entity.getPersonas().forEach(p -> p.setReclamo(entity));
        }
        if (entity.getDetalles() != null) {
            entity.getDetalles().forEach(d -> d.setReclamo(entity));
        }
        if (entity.getMedidas() != null) {
            entity.getMedidas().forEach(m -> m.setReclamo(entity));
        }
        if (entity.getResultados() != null) {
            entity.getResultados().forEach(r -> r.setReclamo(entity));
        }
        if (entity.getGestion() != null) {
            entity.getGestion().setReclamo(entity);
        }
        if (entity.getGestionClinica() != null) {
            entity.getGestionClinica().setReclamo(entity);
        }

        reclamosRepo.save(entity);
    }
}


