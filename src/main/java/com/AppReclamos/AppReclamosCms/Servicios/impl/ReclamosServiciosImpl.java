package com.AppReclamos.AppReclamosCms.Servicios.impl;

import com.AppReclamos.AppReclamosCms.Modelos.*;
import com.AppReclamos.AppReclamosCms.Repositorios.ReclamosRepositorio;
import com.AppReclamos.AppReclamosCms.Servicios.interfaces.IReclamosServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ReclamosServiciosImpl implements IReclamosServicios {
    @Override
    public List<ReclamoTablaDTO> buscarFiltrado(String estado, String buscarPor, String query, Integer anio, Integer mes) {
        List<ReclamoTablaDTO> all = listarTodosParaTabla();

        return all.stream()
                .filter(r -> (estado == null || estado.equals("TODOS") || r.getEstado().equalsIgnoreCase(estado)))
                .filter(r -> {
                    if (buscarPor == null || buscarPor.equals("TODO") || query == null || query.isBlank()) return true;
                    if (buscarPor.equals("CODIGO")) return r.getCodigoReclamo() != null && r.getCodigoReclamo().contains(query);
                    if (buscarPor.equals("NOMBRE")) return r.getNumeroDocumentoAfectado() != null && r.getNumeroDocumentoAfectado().toLowerCase().contains(query.toLowerCase());
                    return true;
                })
                .filter(r -> (anio == null || (r.getFechaReclamo() != null && r.getFechaReclamo().getYear() == anio)))
                .filter(r -> (mes == null || (r.getFechaReclamo() != null && r.getFechaReclamo().getMonthValue() == mes)))
                .toList();
    }

    @Autowired
    private ReclamosRepositorio reclamosRepositorio;


    @Override
    public List<ReclamoDTO> listarTodosDTO() {
        return List.of();
    }

    @Override
    public ReclamoDTO buscarDTOporId(Integer id) {
        return null;
    }

    @Override
    public void guardarDesdeDTO(ReclamoDTO reclamo) {

    }

    @Override
    public List<ReclamoTablaDTO> listarTodosParaTabla() {
        List<Reclamos> lista = reclamosRepositorio.findAll();
        List<ReclamoTablaDTO> dtos = new ArrayList<>();
        for (Reclamos r : lista) {
            ReclamoTablaDTO dto = new ReclamoTablaDTO();
            dto.setFechaReclamo(r.getFechaReclamo());
            dto.setCodigoReclamo(r.getCodigoReclamo());
            dto.setEstado(r.getEstadoReclamo());

            // TOMAR EL DETALLE MÁS RECIENTE (por fechaCreacion)
            if (r.getDetalles() != null && !r.getDetalles().isEmpty()) {
                DetalleReclamo detalleReciente = r.getDetalles().stream()
                        .max(Comparator.comparing(DetalleReclamo::getFechaCreacion))
                        .orElse(r.getDetalles().get(0));
                dto.setMedioReclamo(detalleReciente.getMedioRecepcion());
            }

            // Lógica para usuario afectado igual que antes
            if (r.getPersonas() != null && !r.getPersonas().isEmpty()) {
                for (PersonaReclamo p : r.getPersonas()) {
                    if ("USUARIO_AFECTADO".equalsIgnoreCase(p.getTipoPersona())
                            || "USUARIO".equalsIgnoreCase(p.getTipoPersona())
                            || "AFECTADO".equalsIgnoreCase(p.getTipoPersona())) {
                        dto.setTipoDocumentoAfectado(p.getTipoDocumento());
                        dto.setNumeroDocumentoAfectado(p.getNumeroDocumento());
                        dto.setRazonSocialAfectado(p.getRazonSocial());
                        dto.setNombreAfectado(p.getNombres());
                        break;
                    }
                }
            }
            dtos.add(dto);
        }
        return dtos;
    }
}


