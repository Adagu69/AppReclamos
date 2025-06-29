package com.AppReclamos.AppReclamosCms.Servicios.impl;

import com.AppReclamos.AppReclamosCms.Modelos.MedidaDTO;
import com.AppReclamos.AppReclamosCms.Modelos.MedidasAdoptadas;
import com.AppReclamos.AppReclamosCms.Modelos.Reclamos;
import com.AppReclamos.AppReclamosCms.Repositorios.MedidaAdoptadaRepositorio;
import com.AppReclamos.AppReclamosCms.Repositorios.ReclamosRepositorio;
import com.AppReclamos.AppReclamosCms.Servicios.interfaces.IMedidaAdoptadaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedidaAdoptadaServicio implements IMedidaAdoptadaServicio {

    @Autowired
    private MedidaAdoptadaRepositorio medidaAdoptadaRepositorio;

    @Autowired
    private ReclamosRepositorio reclamosRepositorio;

    @Override
    public List<MedidaDTO> findByReclamoId(Integer reclamoId) {
        return medidaAdoptadaRepositorio.findByReclamo_IdReclamo(reclamoId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    @Override
    public MedidaDTO findById(Integer id) {
        // CORRECCIÓN: No es necesario convertir a Long
        return medidaAdoptadaRepositorio.findById(id).map(this::convertToDTO).orElse(null);
    }

    @Override
    public MedidaDTO save(MedidaDTO medidaDTO) {
        MedidasAdoptadas medida = convertToEntity(medidaDTO);
        MedidasAdoptadas savedMedida = medidaAdoptadaRepositorio.save(medida);
        return convertToDTO(savedMedida);
    }

    @Override
    public void delete(Integer id) {
        // CORRECCIÓN: No es necesario convertir a Long
        medidaAdoptadaRepositorio.deleteById(id);
    }

    private MedidaDTO convertToDTO(MedidasAdoptadas medida) {
        // Asumiendo que `getReclamo().getIdReclamo()` devuelve un Integer
        return MedidaDTO.builder()
                .id(medida.getId())
                .descripcion(medida.getDescripcion())
                .fechaMedida(medida.getFechaMedida())
                .responsableEjecucion(medida.getResponsableEjecucion())
                .fechaEjecucion(medida.getFechaEjecucion())
                .reclamoId(medida.getReclamo().getIdReclamo())
                .build();
    }

    private MedidasAdoptadas convertToEntity(MedidaDTO dto) {
        MedidasAdoptadas medida = new MedidasAdoptadas();
        medida.setId(dto.getId());
        medida.setDescripcion(dto.getDescripcion());
        medida.setFechaMedida(dto.getFechaMedida());
        medida.setResponsableEjecucion(dto.getResponsableEjecucion());
        medida.setFechaEjecucion(dto.getFechaEjecucion());

        // CORRECCIÓN: Simplificar la búsqueda del reclamo.
        Reclamos reclamo = reclamosRepositorio.findById(dto.getReclamoId())
                .orElseThrow(() -> new RuntimeException("Reclamo no encontrado con id: " + dto.getReclamoId()));
        medida.setReclamo(reclamo);

        return medida;
    }

}
