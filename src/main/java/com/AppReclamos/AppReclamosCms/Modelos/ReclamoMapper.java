package com.AppReclamos.AppReclamosCms.Modelos;

import com.AppReclamos.AppReclamosCms.Modelos.Enums.*;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Context;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.*;
import org.slf4j.LoggerFactory;
import java.util.Comparator;
import com.AppReclamos.AppReclamosCms.Modelos.*;
import java.util.List;


@Mapper(
        componentModel = "spring",
        imports = {
LoggerFactory.class,
EstadoReclamo.class,
TipoDocumento.class,
MedioRecepcion.class,
GestionServicios.class,
GestionCompetencia.class,
GestionEtapa.class,
ResultadoReclamo.class,
MotivoConclusion.class,
ComunicacionResultado.class,
NaturalezaMedida.class,
ProcesoMedida.class,
                TipoDeclarante.class

    },
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ReclamoMapper {


    // --- 1. Mapeos desde 'Reclamos' (entity) ---
    @Mapping(target = "id", source = "entity.idReclamo")
    @Mapping(target = "estado",
            expression = "java(toEnum(entity.getEstadoReclamo(), EstadoReclamo.class))")

    // --- 2. Mapeos desde 'usuarioAfectado' (PersonaReclamo) ---
    @Mapping(target = "tipoDocumentoAfectado",
            expression = "java(usuarioAfectado != null ? usuarioAfectado.getTipoDocumento() : null)")
    @Mapping(target = "numeroDocumentoAfectado", expression = "java(usuarioAfectado != null ? usuarioAfectado.getNumeroDocumento() : null)")
    @Mapping(target = "nombreAfectado",          expression = "java(usuarioAfectado != null ? usuarioAfectado.getNombres() : null)")
    @Mapping(target = "razonSocialAfectado",
            expression = "java(usuarioAfectado != null ? usuarioAfectado.getRazonSocial() : null)")
    @Mapping(target = "apellidoPaterno",         expression = "java(usuarioAfectado != null ? usuarioAfectado.getApellidoPaterno() : null)")
    @Mapping(target = "apellidoMaterno",         expression = "java(usuarioAfectado != null ? usuarioAfectado.getApellidoMaterno() : null)")
    @Mapping(target = "resultadoPorCorreo",      expression = "java(usuarioAfectado != null ? usuarioAfectado.getResultadoPorCorreo() : null)") // Este es Boolean, no enum. Se queda igual.
    @Mapping(target = "correoElectronico",       expression = "java(usuarioAfectado != null ? usuarioAfectado.getCorreoElectronico() : null)")
    @Mapping(target = "domicilio",               expression = "java(usuarioAfectado != null ? usuarioAfectado.getDomicilio() : null)")
    @Mapping(target = "telefono",                expression = "java(usuarioAfectado != null ? usuarioAfectado.getTelefono() : null)")

    // --- 3. Mapeos desde 'detalleReciente' (DetalleReclamo) ---
    @Mapping(target = "medioRecepcion",
            expression = "java(detalleReciente != null ? detalleReciente.getMedioRecepcion() : null)")
    @Mapping(target = "fechaRecepcion",
            expression = "java(detalleReciente != null ? detalleReciente.getFechaRecepcion() : null)")
    @Mapping(target = "descripcion",
            expression = "java(detalleReciente != null ? detalleReciente.getDescripcion() : null)")

    // --- 4. Mapeos desde 'gestion' (GestionReclamo) ---
    @Mapping(target = "servicio",
            expression = "java(gestion != null ? gestion.getServicio() : null)")
    @Mapping(target = "competencia",
            expression = "java(gestion != null ? gestion.getCompetencia() : null)")
    @Mapping(target = "clasificacion1",          expression = "java(gestion != null ? gestion.getClasificacion1() : null)")
    @Mapping(target = "clasificacion2",          expression = "java(gestion != null ? gestion.getClasificacion2() : null)")
    @Mapping(target = "clasificacion3",          expression = "java(gestion != null ? gestion.getClasificacion3() : null)")
    @Mapping(target = "etapaReclamo",
            expression = "java(gestion != null ? gestion.getEtapaReclamo() : null)")
    @Mapping(target = "codigoPrimigenio",        expression = "java(gestion != null ? gestion.getCodigoPrimigenio() : null)")
    @Mapping(target = "tipoAdministraTraslado",
            expression = "java(gestion != null ? gestion.getTipoAdministraTraslado() : null)")
    @Mapping(target = "codigoAdministraTraslado",expression = "java(gestion != null ? gestion.getCodigoAdministraTraslado() : null)")

    // --- 5. Mapeos desde 'resultadoReciente' (ResultadoNotificacion) ---
    @Mapping(target = "resultado",
            expression = "java(resultadoReciente != null ? toEnum(resultadoReciente.getResultado(), ResultadoReclamo.class) : null)")
    @Mapping(target = "motivoConclusion",
            expression = "java(resultadoReciente != null ? toEnum(resultadoReciente.getMotivoConclusionAnticipada(), MotivoConclusion.class) : null)")
    @Mapping(target = "fechaResultado",          expression = "java(resultadoReciente != null ? resultadoReciente.getFechaResultado() : null)")
    @Mapping(target = "comunicacionResultado",
            expression = "java(resultadoReciente != null ? toEnum(resultadoReciente.getComunicacionResultado(), ComunicacionResultado.class) : null)")
    @Mapping(target = "fechaNotificacion",       expression = "java(resultadoReciente != null ? resultadoReciente.getFechaNotificacion() : null)")

    // --- 6. Mapeos desde 'medidaReciente' (MedidasAdoptadas) ---
    @Mapping(target = "codigoMedida",
            expression = "java(medidaReciente != null ? medidaReciente.getCodigoMedida() : null)")
    @Mapping(target = "naturaleza",
            expression = "java(medidaReciente != null ? medidaReciente.getNaturaleza() : null)")
    @Mapping(target = "proceso",
            expression = "java(medidaReciente != null ? medidaReciente.getProceso() : null)")
    @Mapping(target = "fechaInicioImplementacion", expression = "java(medidaReciente != null ? medidaReciente.getFechaInicioImplementacion() : null)")
    @Mapping(target = "fechaCulminacionPrevista",  expression = "java(medidaReciente != null ? medidaReciente.getFechaCulminacionPrevista() : null)")
    @Mapping(target = "descripcionMedida",         expression = "java(medidaReciente != null ? medidaReciente.getDescripcionMedida() : null)")

    ReclamoTablaDTO toTableDTO(
            Reclamos entity,
            @Context PersonaReclamo usuarioAfectado,
            @Context DetalleReclamo detalleReciente,
            @Context GestionReclamo gestion,
            @Context ResultadoNotificacion resultadoReciente,
            @Context MedidasAdoptadas medidaReciente

    );

            // --- Métodos de mapeo para formularios (DTO anidado <-> Entidad) ---
    ReclamoDTO toDetailDTO(Reclamos entity);
    Reclamos toEntity(ReclamoDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(ReclamoDTO dto, @MappingTarget Reclamos entity);


    default <T extends Enum<T>> T toEnum(String value, Class<T> enumClass) {
        if (value == null || value.isBlank() || enumClass == null) {
            return null;
        }
        try {
            // Se elimina el .toUpperCase() para que busque el nombre exacto
            return Enum.valueOf(enumClass, value.trim());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
