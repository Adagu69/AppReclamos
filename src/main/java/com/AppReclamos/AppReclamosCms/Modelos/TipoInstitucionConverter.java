package com.AppReclamos.AppReclamosCms.Modelos;

import com.AppReclamos.AppReclamosCms.Modelos.Enums.TipoInstitucion;
import jakarta.persistence.AttributeConverter;

public class TipoInstitucionConverter implements AttributeConverter<TipoInstitucion, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoInstitucion tipoInstitucion) {
        return (tipoInstitucion == null) ? null : tipoInstitucion.getCodigo();
    }

    @Override
    public TipoInstitucion convertToEntityAttribute(Integer codigo) {
        return (codigo == null) ? null : TipoInstitucion.fromCodigo(codigo);
    }
}
