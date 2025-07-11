package com.AppReclamos.AppReclamosCms.Modelos;

import com.AppReclamos.AppReclamosCms.Modelos.Enums.MedioPresentacion;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class MedioPresentacionConverter implements AttributeConverter<MedioPresentacion, Integer> {

    @Override
    public Integer convertToDatabaseColumn(MedioPresentacion medio) {
        return (medio == null) ? null : medio.getCodigo();
    }

    @Override
    public MedioPresentacion convertToEntityAttribute(Integer codigo) {
        return (codigo == null) ? null : MedioPresentacion.fromCodigo(codigo);
    }
}
