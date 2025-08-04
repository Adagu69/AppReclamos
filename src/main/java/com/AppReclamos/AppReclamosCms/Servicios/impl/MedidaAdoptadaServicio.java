package com.AppReclamos.AppReclamosCms.Servicios.impl;

import com.AppReclamos.AppReclamosCms.Modelos.MedidaDTO;
import com.AppReclamos.AppReclamosCms.Modelos.MedidasAdoptadas;
import com.AppReclamos.AppReclamosCms.Modelos.Reclamos;
import com.AppReclamos.AppReclamosCms.Repositorios.MedidaAdoptadaRepositorio;
import com.AppReclamos.AppReclamosCms.Repositorios.ReclamosRepositorio;
import com.AppReclamos.AppReclamosCms.Servicios.interfaces.IMedidaAdoptadaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        // Validación de fechas
        LocalDate fechaInicio = medidaDTO.getFechaInicioAsLocalDate();
        LocalDate fechaCulminacion = medidaDTO.getFechaCulminacionAsLocalDate();

        if (fechaInicio != null && fechaCulminacion != null) {
            if (fechaCulminacion.isBefore(fechaInicio)) {
                throw new RuntimeException("La fecha de culminación no puede ser anterior a la fecha de inicio.");
            }
        }

        // Verificamos si es una actualización (tiene ID) o una creación (no tiene ID)
        MedidasAdoptadas medidaEntity;

        if (medidaDTO.getId() != null) {
            // --- LÓGICA DE ACTUALIZACIÓN ---
            // 1. Buscamos la entidad existente en la base de datos.
            medidaEntity = medidaAdoptadaRepositorio.findById(medidaDTO.getId())
                    .orElseThrow(() -> new RuntimeException("No se encontró la medida con ID: " + medidaDTO.getId()));

            // 2. Actualizamos los campos de la entidad encontrada con los datos del DTO.
            // No actualizamos el código porque ya fue generado
            medidaEntity.setNaturaleza(medidaDTO.getNaturaleza());
            medidaEntity.setProceso(medidaDTO.getProceso());
            medidaEntity.setDescripcionMedida(medidaDTO.getDescripcionMedida());
            medidaEntity.setFechaInicioImplementacion(medidaDTO.getFechaInicioAsLocalDate());
            medidaEntity.setFechaCulminacionPrevista(medidaDTO.getFechaCulminacionAsLocalDate());
            // No cambiamos el reclamo al que pertenece.

        } else {
            // --- LÓGICA DE CREACIÓN ---
            // 1. Creamos una entidad nueva.
            medidaEntity = new MedidasAdoptadas();

            // 2. Generamos el código correlativo automáticamente
            String codigoGenerado = generarCodigoCorrelativo(medidaDTO.getReclamoId());
            medidaEntity.setCodigoMedida(codigoGenerado);

            // 3. Asignamos los datos del DTO a la nueva entidad.
            medidaEntity.setNaturaleza(medidaDTO.getNaturaleza());
            medidaEntity.setProceso(medidaDTO.getProceso());
            medidaEntity.setDescripcionMedida(medidaDTO.getDescripcionMedida());
            medidaEntity.setFechaInicioImplementacion(medidaDTO.getFechaInicioAsLocalDate());
            medidaEntity.setFechaCulminacionPrevista(medidaDTO.getFechaCulminacionAsLocalDate());

            // 4. Buscamos y asignamos el reclamo padre.
            Reclamos reclamo = reclamosRepositorio.findById(medidaDTO.getReclamoId())
                    .orElseThrow(() -> new RuntimeException("Reclamo no encontrado con id: " + medidaDTO.getReclamoId()));
            medidaEntity.setReclamo(reclamo);
        }

        // Guardamos la entidad (nueva o actualizada) y devolvemos su DTO.
        MedidasAdoptadas savedMedida = medidaAdoptadaRepositorio.save(medidaEntity);
        return convertToDTO(savedMedida);
    }

    /**
     * Genera un código correlativo de 2 dígitos para una medida de un reclamo específico.
     * Ejemplo: 01, 02, 03, etc.
     */
    private String generarCodigoCorrelativo(Integer reclamoId) {
        // Contamos cuántas medidas ya tiene este reclamo
        Long cantidadMedidas = medidaAdoptadaRepositorio.countByReclamoId(reclamoId);

        // El próximo correlativo será el contador actual + 1
        int proximoCorrelativo = cantidadMedidas.intValue() + 1;

        // Formateamos a 2 dígitos con ceros a la izquierda
        return String.format("%02d", proximoCorrelativo);
    }

    @Override
    public void delete(Integer id) {
        // CORRECCIÓN: No es necesario convertir a Long
        medidaAdoptadaRepositorio.deleteById(id);
    }

    private MedidaDTO convertToDTO(MedidasAdoptadas medida) {
        MedidaDTO dto = MedidaDTO.builder()
                .id(medida.getId())
                .codigoMedida(medida.getCodigoMedida())
                .naturaleza(medida.getNaturaleza())
                .proceso(medida.getProceso())
                .descripcionMedida(medida.getDescripcionMedida())
                .reclamoId(medida.getReclamo().getIdReclamo())
                .build();

        // Convertir las fechas LocalDate a String formato AAAAMMDD
        dto.setFechaInicioFromLocalDate(medida.getFechaInicioImplementacion());
        dto.setFechaCulminacionFromLocalDate(medida.getFechaCulminacionPrevista());

        return dto;
    }

}
