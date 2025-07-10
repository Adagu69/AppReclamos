package com.AppReclamos.AppReclamosCms.Modelos;

import com.AppReclamos.AppReclamosCms.Modelos.Enums.TipoDocumento;
import jakarta.persistence.AttributeConverter;

public class TipoDocumentoConverter implements AttributeConverter<TipoDocumento, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoDocumento tipoDocumento) {
        // Del Enum -> Al número para la BD
        if (tipoDocumento == null) {
            return null;
        }
        return tipoDocumento.getCodigo();
    }

    @Override
    public TipoDocumento convertToEntityAttribute(Integer codigo) {
        // Del número de la BD -> Al Enum
        if (codigo == null) {
            return null;
        }
        return TipoDocumento.fromCodigo(codigo);
    }
}
