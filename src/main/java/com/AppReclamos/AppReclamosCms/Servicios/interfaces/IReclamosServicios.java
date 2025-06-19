package com.AppReclamos.AppReclamosCms.Servicios.interfaces;

import com.AppReclamos.AppReclamosCms.Modelos.ReclamoDTO;
import com.AppReclamos.AppReclamosCms.Modelos.ReclamoTablaDTO;

import java.util.List;

public interface IReclamosServicios {
    List<ReclamoDTO> listarTodosDTO();
    ReclamoDTO buscarDTOporId(Integer id);
    void guardarDesdeDTO(ReclamoDTO reclamo);

    List<ReclamoTablaDTO> listarTodosParaTabla();

    List<ReclamoTablaDTO> buscarFiltrado(
            String estado,
            String buscarPor,
            String query,
            Integer anio,
            Integer mes
    );
}
