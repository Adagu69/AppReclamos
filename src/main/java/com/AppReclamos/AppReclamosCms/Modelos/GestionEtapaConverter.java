package com.AppReclamos.AppReclamosCms.Modelos;

import com.AppReclamos.AppReclamosCms.Modelos.Enums.GestionEtapa;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class GestionEtapaConverter implements AttributeConverter<GestionEtapa, Integer> {

    @Override
    public Integer convertToDatabaseColumn(GestionEtapa etapa) {
        return (etapa == null) ? null : etapa.getCodigo();
    }

    @Override
    public GestionEtapa convertToEntityAttribute(Integer codigo) {
        return (codigo == null) ? null : GestionEtapa.fromCodigo(codigo);
    }
}
