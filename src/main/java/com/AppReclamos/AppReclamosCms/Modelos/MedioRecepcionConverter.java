package com.AppReclamos.AppReclamosCms.Modelos;

import com.AppReclamos.AppReclamosCms.Modelos.Enums.MedioRecepcion;
import jakarta.persistence.AttributeConverter;

public class MedioRecepcionConverter implements AttributeConverter<MedioRecepcion, Integer> {

    @Override
    public Integer convertToDatabaseColumn(MedioRecepcion medioRecepcion) {
        return (medioRecepcion == null) ? null : medioRecepcion.getCodigo();
    }

    @Override
    public MedioRecepcion convertToEntityAttribute(Integer codigo) {
        return (codigo == null) ? null : MedioRecepcion.fromCodigo(codigo);
    }
}