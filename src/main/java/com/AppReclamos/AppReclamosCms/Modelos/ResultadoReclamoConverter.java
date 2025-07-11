package com.AppReclamos.AppReclamosCms.Modelos;

import com.AppReclamos.AppReclamosCms.Modelos.Enums.ResultadoReclamo;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ResultadoReclamoConverter implements AttributeConverter<ResultadoReclamo, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ResultadoReclamo resultado) {
        return (resultado == null) ? null : resultado.getCodigo();
    }

    @Override
    public ResultadoReclamo convertToEntityAttribute(Integer codigo) {
        return (codigo == null) ? null : ResultadoReclamo.fromCodigo(codigo);
    }
}
