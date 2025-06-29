package com.AppReclamos.AppReclamosCms.Modelos;

import com.AppReclamos.AppReclamosCms.Modelos.Enums.EstadoReclamo;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ReclamoMapper {

    // --- MAPEO PARA LA TABLA PRINCIPAL (Se mantiene igual) ---
    @Mapping(target="medioRecepcion", expression="java(detalleReciente!=null? detalleReciente.getMedioRecepcion():null)")
    @Mapping(target="estado", expression="java(toEstadoEnum(entity.getEstadoReclamo()))")
    @Mapping(target="tipoDocumentoAfectado", expression="java(usuarioAfectado!=null? usuarioAfectado.getTipoDocumento():null)")
    @Mapping(target="numeroDocumentoAfectado", expression="java(usuarioAfectado!=null? usuarioAfectado.getNumeroDocumento():null)")
    @Mapping(target="razonSocialAfectado", expression="java(usuarioAfectado!=null? usuarioAfectado.getRazonSocial():null)")
    @Mapping(target="nombreAfectado", expression="java(usuarioAfectado!=null? usuarioAfectado.getNombres():null)")
    ReclamoTablaDTO toTableDTO(
            Reclamos entity,
            @Context DetalleReclamo detalleReciente,
            @Context PersonaReclamo usuarioAfectado
    );


    // --- MAPEO DETALLADO: DTO -> Entidad (CORRECCIÓN PRINCIPAL) ---
    @Mapping(target = "idReclamo", ignore = true)
    // --- ¡ESTAS LÍNEAS SON LA CLAVE DE LA SOLUCIÓN! ---
    // Le decimos a MapStruct que use explícitamente los métodos de abajo para cada objeto anidado.
    @Mapping(source = "gestion", target = "gestion") // Usa gestionDTOToGestion
    @Mapping(source = "personas", target = "personas") // Usa personaDTOsToPersonas
    @Mapping(source = "detalles", target = "detalles") // Usa detalleDTOsToDetalles
    @Mapping(source = "resultados", target = "resultados") // Usa resultadoDTOsToResultados
    Reclamos toEntity(ReclamoDTO dto);


    // --- MAPEO DETALLADO: Entidad -> DTO (Se mantiene igual) ---
    ReclamoDTO toDetailDTO(Reclamos entity);


    // --- MÉTODOS AUXILIARES PARA MAPEAR OBJETOS ANIDADOS (Se mantienen igual) ---
    // MapStruct los usará porque se lo indicamos en los @Mapping de arriba.

    GestionReclamo gestionDTOToGestion(GestionReclamoDTO dto);

    List<PersonaReclamo> personaDTOsToPersonas(List<PersonaReclamoDTO> dtos);

    List<DetalleReclamo> detalleDTOsToDetalles(List<DetalleReclamoDTO> dtos);

    List<ResultadoNotificacion> resultadoDTOsToResultados(List<ResultadoNotificacionDTO> dtos);

    // Mapeo individual para los elementos de las listas (necesario para las conversiones de listas)
    @Mapping(source = "idPersona", target = "idPersona")
    PersonaReclamo personaDTOToPersona(PersonaReclamoDTO dto);

    DetalleReclamo detalleDTOToDetalle(DetalleReclamoDTO dto);

    ResultadoNotificacion resultadoDTOToResultado(ResultadoNotificacionDTO dto);


    // --- HELPER PARA ENUM (Se mantiene igual) ---
    default EstadoReclamo toEstadoEnum(String v){
        return v == null ? null : EstadoReclamo.valueOf(v);
    }
}
