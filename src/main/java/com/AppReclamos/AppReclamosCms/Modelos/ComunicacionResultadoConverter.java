package com.AppReclamos.AppReclamosCms.Modelos;

import com.AppReclamos.AppReclamosCms.Modelos.Enums.ComunicacionResultado;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ComunicacionResultadoConverter implements AttributeConverter<ComunicacionResultado, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ComunicacionResultado comunicacion) {
        return (comunicacion == null) ? null : comunicacion.getCodigo();
    }

    @Override
    public ComunicacionResultado convertToEntityAttribute(Integer codigo) {
        return (codigo == null) ? null : ComunicacionResultado.fromCodigo(codigo);
    }
}
