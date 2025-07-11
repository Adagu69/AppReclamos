package com.AppReclamos.AppReclamosCms.Modelos;

import com.AppReclamos.AppReclamosCms.Modelos.Enums.MotivoConclusion;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class MotivoConclusionConverter implements AttributeConverter<MotivoConclusion, Integer> {

    @Override
    public Integer convertToDatabaseColumn(MotivoConclusion motivo) {
        return (motivo == null) ? null : motivo.getCodigo();
    }

    @Override
    public MotivoConclusion convertToEntityAttribute(Integer codigo) {
        return (codigo == null) ? null : MotivoConclusion.fromCodigo(codigo);
    }
}
