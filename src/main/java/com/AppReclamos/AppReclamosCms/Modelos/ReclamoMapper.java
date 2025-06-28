package com.AppReclamos.AppReclamosCms.Modelos;

import com.AppReclamos.AppReclamosCms.Modelos.Enums.EstadoReclamo;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.*;

@Mapper(
        componentModel       = "spring",
        injectionStrategy    = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ReclamoMapper {

    // helper para convertir String→EstadoReclamo
    default EstadoReclamo toEstadoEnum(String v){
        return v==null?null:EstadoReclamo.valueOf(v);
    }

    @Mapping(target="medioRecepcion",
            expression="java(detalleReciente!=null? detalleReciente.getMedioRecepcion():null)")
    @Mapping(target="estado",
            expression="java(toEstadoEnum(entity.getEstadoReclamo()))")
    @Mapping(target="tipoDocumentoAfectado",
            expression="java(usuarioAfectado!=null? usuarioAfectado.getTipoDocumento():null)")
    @Mapping(target="numeroDocumentoAfectado",
            expression="java(usuarioAfectado!=null? usuarioAfectado.getNumeroDocumento():null)")
    @Mapping(target="razonSocialAfectado",
            expression="java(usuarioAfectado!=null? usuarioAfectado.getRazonSocial():null)")
    @Mapping(target="nombreAfectado",
            expression="java(usuarioAfectado!=null? usuarioAfectado.getNombres():null)")
    ReclamoTablaDTO toTableDTO(
            Reclamos entity,
            @Context DetalleReclamo detalleReciente,
            @Context PersonaReclamo usuarioAfectado
    );

    /* ========== DETAIL / CREAR ========== */
    ReclamoDTO toDetailDTO(Reclamos entity);

    @Mapping(target="idReclamo", ignore=true)
    Reclamos toEntity(ReclamoDTO dto);
}
