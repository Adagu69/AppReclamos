package com.AppReclamos.AppReclamosCms.Modelos;

import com.AppReclamos.AppReclamosCms.Modelos.Enums.GestionServicios;
import jakarta.persistence.AttributeConverter;

public class GestionServiciosConverter implements AttributeConverter<GestionServicios, String> {

    @Override
    public String convertToDatabaseColumn(GestionServicios gestionServicios) {
        // Del Enum -> Al String numérico para la BD
        return (gestionServicios == null) ? null : gestionServicios.getCodigo();
    }

    @Override
    public GestionServicios convertToEntityAttribute(String codigo) {
        // Del String numérico de la BD -> Al Enum
        return (codigo == null) ? null : GestionServicios.fromCodigo(codigo);
    }
}
