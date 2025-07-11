package com.AppReclamos.AppReclamosCms.Modelos;

import com.AppReclamos.AppReclamosCms.Modelos.Enums.EstadoReclamo;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class EstadoReclamoConverter implements AttributeConverter<EstadoReclamo, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EstadoReclamo estadoReclamo) {
        return (estadoReclamo == null) ? null : estadoReclamo.getCodigo();
    }

    @Override
    public EstadoReclamo convertToEntityAttribute(Integer codigo) {
        return (codigo == null) ? null : EstadoReclamo.fromCodigo(codigo);
    }
}