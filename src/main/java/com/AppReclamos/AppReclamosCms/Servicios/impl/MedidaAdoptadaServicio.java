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
        // Verificamos si es una actualización (tiene ID) o una creación (no tiene ID)
        MedidasAdoptadas medidaEntity;

        if (medidaDTO.getId() != null) {
            // --- LÓGICA DE ACTUALIZACIÓN ---
            // 1. Buscamos la entidad existente en la base de datos.
            medidaEntity = medidaAdoptadaRepositorio.findById(medidaDTO.getId())
                    .orElseThrow(() -> new RuntimeException("No se encontró la medida con ID: " + medidaDTO.getId()));

            // 2. Actualizamos los campos de la entidad encontrada con los datos del DTO.
            medidaEntity.setCodigoMedida(medidaDTO.getCodigoMedida());
            medidaEntity.setNaturaleza(medidaDTO.getNaturaleza());
            medidaEntity.setProceso(medidaDTO.getProceso());
            medidaEntity.setDescripcion(medidaDTO.getDescripcion());
            medidaEntity.setFechaInicioImplementacion(medidaDTO.getFechaInicioImplementacion());
            medidaEntity.setFechaCulminacionPrevista(medidaDTO.getFechaCulminacionPrevista());
            // No cambiamos el reclamo al que pertenece.

        } else {
            // --- LÓGICA DE CREACIÓN (la que ya teníamos) ---
            // 1. Creamos una entidad nueva.
            medidaEntity = new MedidasAdoptadas();

            // 2. Asignamos los datos del DTO a la nueva entidad.
            medidaEntity.setCodigoMedida(medidaDTO.getCodigoMedida());
            medidaEntity.setNaturaleza(medidaDTO.getNaturaleza());
            medidaEntity.setProceso(medidaDTO.getProceso());
            medidaEntity.setDescripcion(medidaDTO.getDescripcion());
            medidaEntity.setFechaInicioImplementacion(medidaDTO.getFechaInicioImplementacion());
            medidaEntity.setFechaCulminacionPrevista(medidaDTO.getFechaCulminacionPrevista());

            // 3. Buscamos y asignamos el reclamo padre.
            Reclamos reclamo = reclamosRepositorio.findById(medidaDTO.getReclamoId())
                    .orElseThrow(() -> new RuntimeException("Reclamo no encontrado con id: " + medidaDTO.getReclamoId()));
            medidaEntity.setReclamo(reclamo);
        }

        // Guardamos la entidad (nueva o actualizada) y devolvemos su DTO.
        MedidasAdoptadas savedMedida = medidaAdoptadaRepositorio.save(medidaEntity);
        return convertToDTO(savedMedida);
    }

    @Override
    public void delete(Integer id) {
        // CORRECCIÓN: No es necesario convertir a Long
        medidaAdoptadaRepositorio.deleteById(id);
    }

    private MedidaDTO convertToDTO(MedidasAdoptadas medida) {
        return MedidaDTO.builder()
                .id(medida.getId())
                .codigoMedida(medida.getCodigoMedida())
                .naturaleza(medida.getNaturaleza())
                .proceso(medida.getProceso())
                .fechaInicioImplementacion(medida.getFechaInicioImplementacion())
                .fechaCulminacionPrevista(medida.getFechaCulminacionPrevista())
                .descripcion(medida.getDescripcion())
                .reclamoId(medida.getReclamo().getIdReclamo())
                .build();
    }

    private MedidasAdoptadas convertToEntity(MedidaDTO dto) {
        MedidasAdoptadas medida = new MedidasAdoptadas();
        medida.setId(dto.getId());
        medida.setCodigoMedida(dto.getCodigoMedida());
        medida.setNaturaleza(dto.getNaturaleza());
        medida.setProceso(dto.getProceso());
        medida.setFechaInicioImplementacion(dto.getFechaInicioImplementacion());
        medida.setFechaCulminacionPrevista(dto.getFechaCulminacionPrevista());
        medida.setDescripcion(dto.getDescripcion());

        Reclamos reclamo = reclamosRepositorio.findById(dto.getReclamoId())
                .orElseThrow(() -> new RuntimeException("Reclamo no encontrado con id: " + dto.getReclamoId()));
        medida.setReclamo(reclamo);

        return medida;
    }

}
