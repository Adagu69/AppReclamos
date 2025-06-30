package com.AppReclamos.AppReclamosCms.Servicios.interfaces;

import com.AppReclamos.AppReclamosCms.Modelos.ReclamoDTO;
import com.AppReclamos.AppReclamosCms.Modelos.ReclamoTablaDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.util.List;

public interface IReclamosServicios {

    /**
     * Busca y filtra los reclamos para la tabla principal.
     */
    List<ReclamoTablaDTO> buscarFiltrado(String estado, String buscarPor, String query, Integer anio, Integer mes);

    /**
     * Busca un único reclamo por su ID para ver o editar su detalle.
     */
    ReclamoDTO buscarPorId(Integer id);

    /**
     * Guarda un reclamo nuevo o actualiza uno existente a partir de un DTO.
     * @return El DTO del reclamo guardado, con su ID actualizado.
     */
    ReclamoDTO guardarDesdeDTO(ReclamoDTO dto);

    /**
     * Elimina un reclamo por su ID.
     */
    void eliminarPorId(Integer id);
}

