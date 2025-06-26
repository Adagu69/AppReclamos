package com.AppReclamos.AppReclamosCms.Modelos;

import com.AppReclamos.AppReclamosCms.Modelos.Enums.EstadoReclamo;
import com.AppReclamos.AppReclamosCms.Modelos.Enums.MedioRecepcion;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReclamoMapper {

    /* helper para EstadoReclamo */
    default EstadoReclamo toEstadoEnum(String value) {
        return value == null ? null : EstadoReclamo.valueOf(value);
    }

    /* ——— forward: de DetalleReclamo.MedioRecepcion → tu enum MedioRecepcion ——— */
    @ValueMappings({
            @ValueMapping(source = "VENTANILLA", target = "FISICO"),
            @ValueMapping(source = "CORREO",     target = "VIRTUAL"),
            @ValueMapping(source = "WEB",        target = "VIRTUAL"),
            @ValueMapping(source = "OTRO",       target = "TELEFONICO")
    })
    MedioRecepcion toMedioRecepcion(DetalleReclamo.MedioRecepcion medio);


    /* ——— inverse: de tu enum MedioRecepcion → DetalleReclamo.MedioRecepcion ——— */
    @ValueMappings({
            @ValueMapping(source = "FISICO",     target = "VENTANILLA"),
            @ValueMapping(source = "VIRTUAL",    target = "CORREO"),       // elige a qué viejo constante quieres que virtual vuelva
            @ValueMapping(source = "TELEFONICO", target = "TELEFONICO")
    })
    DetalleReclamo.MedioRecepcion fromMedioRecepcion(MedioRecepcion medio);




    /* ---------- TABLA (list view) ---------- */
    /* ——— mapeo para la tabla ——— */
    @Mapping(
            target     = "medioRecepcion",
            expression = "java(detalleReciente != null ? toMedioRecepcion(detalleReciente.getMedioRecepcion()) : null)"
    )

    @Mapping(
            target     = "estado",
            expression = "java(toEstadoEnum(entity.getEstadoReclamo()))"
    )
    @Mapping(
            target     = "tipoDocumentoAfectado",
            expression = "java(usuarioAfectado != null ? usuarioAfectado.getTipoDocumento() : null)"
    )
    @Mapping(
            target     = "numeroDocumentoAfectado",
            expression = "java(usuarioAfectado != null ? usuarioAfectado.getNumeroDocumento() : null)"
    )
    @Mapping(
            target     = "razonSocialAfectado",
            expression = "java(usuarioAfectado != null ? usuarioAfectado.getRazonSocial() : null)"
    )
    @Mapping(
            target     = "nombreAfectado",
            expression = "java(usuarioAfectado != null ? usuarioAfectado.getNombres() : null)"
    )
    ReclamoTablaDTO toTableDTO(
            Reclamos entity,
            @Context DetalleReclamo detalleReciente,
            @Context PersonaReclamo usuarioAfectado);

    /* ---------- DETALLE / CREACIÓN ---------- */
    ReclamoDTO toDetailDTO(Reclamos entity);

    @Mapping(target = "idReclamo", ignore = true)
    Reclamos toEntity(ReclamoDTO dto);
}
