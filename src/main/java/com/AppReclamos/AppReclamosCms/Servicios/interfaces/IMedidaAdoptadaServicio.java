package com.AppReclamos.AppReclamosCms.Servicios.interfaces;

import com.AppReclamos.AppReclamosCms.Modelos.MedidaDTO;

import java.util.List;

public interface IMedidaAdoptadaServicio {
    List<MedidaDTO> findByReclamoId(Integer reclamoId);

    MedidaDTO findById(Integer id);
    MedidaDTO save(MedidaDTO medidaDTO);
    void delete(Integer id);
}
