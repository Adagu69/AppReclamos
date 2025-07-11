package com.AppReclamos.AppReclamosCms.Modelos;

import com.AppReclamos.AppReclamosCms.Modelos.Enums.ResultadoCorreo;
import jakarta.persistence.AttributeConverter;

public class ResultadoCorreoConverter implements AttributeConverter<ResultadoCorreo, Integer> {
    @Override
    public Integer convertToDatabaseColumn(ResultadoCorreo resultadoCorreo) {
        return (resultadoCorreo == null) ? null : resultadoCorreo.getCodigo();
    }

    @Override
    public ResultadoCorreo convertToEntityAttribute(Integer codigo) {
        return (codigo == null) ? null : ResultadoCorreo.fromCodigo(codigo);
    }
}