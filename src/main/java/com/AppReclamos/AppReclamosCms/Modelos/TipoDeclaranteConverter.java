package com.AppReclamos.AppReclamosCms.Modelos;

import com.AppReclamos.AppReclamosCms.Modelos.Enums.TipoDeclarante;
import jakarta.persistence.AttributeConverter;

public class TipoDeclaranteConverter implements AttributeConverter<TipoDeclarante, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoDeclarante tipoDeclarante) {
        // Convierte el Enum a un Integer para guardarlo en la BD
        if (tipoDeclarante == null) {
            return null;
        }
        return tipoDeclarante.getCodigo();
    }

    @Override
    public TipoDeclarante convertToEntityAttribute(Integer codigo) {
        // Convierte el Integer de la BD de vuelta a un Enum
        if (codigo == null) {
            return null;
        }
        return TipoDeclarante.fromCodigo(codigo);
    }
}