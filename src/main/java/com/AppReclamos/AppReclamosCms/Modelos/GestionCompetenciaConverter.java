package com.AppReclamos.AppReclamosCms.Modelos;

import com.AppReclamos.AppReclamosCms.Modelos.Enums.GestionCompetencia;
import jakarta.persistence.AttributeConverter;

public class GestionCompetenciaConverter implements AttributeConverter<GestionCompetencia, Integer> {

    @Override
    public Integer convertToDatabaseColumn(GestionCompetencia competencia) {
        return (competencia == null) ? null : competencia.getCodigo();
    }

    @Override
    public GestionCompetencia convertToEntityAttribute(Integer codigo) {
        return (codigo == null) ? null : GestionCompetencia.fromCodigo(codigo);
    }
}

