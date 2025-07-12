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


    PersonaReclamo toPersonaEntity(PersonaReclamoDTO personaDTO);
    PersonaReclamoDTO toPersonaDto(PersonaReclamo persona);


    @Mapping(target = "id", source = "entity.idReclamo")
    @Mapping(target = "estado", expression = "java(entity.getEstadoReclamo() != null ? entity.getEstadoReclamo().ordinal() : null)")

    // ¡CAMBIO CLAVE! Añadimos un mapeo explícito para la conversión
    @Mapping(target = "tipoDeclarante", expression = "java(entity.getTipoDeclarante() != null ? entity.getTipoDeclarante().ordinal() : null)")
    @Mapping(target = "tipoInstitucion", expression = "java(entity.getTipoInstitucion() != null ? entity.getTipoInstitucion().ordinal() : null)")
    @Mapping(target = "medioRecepcion", expression = "java(entity.getMedioRecepcion() != null ? entity.getMedioRecepcion().ordinal() : null)")

    // --- Mapeos para Presentante ---
    @Mapping(target = "tipoDocumento_presentante", expression = "java(entity.getPresentante() != null && entity.getPresentante().getTipoDocumento() != null ? entity.getPresentante().getTipoDocumento().getCodigo() : null)")
    @Mapping(target = "numeroDocumento_presentante", source = "entity.presentante.numeroDocumento")
    @Mapping(target = "nombres_presentante", source = "entity.presentante.nombres")
    @Mapping(target = "apellidoPaterno_presentante", source = "entity.presentante.apellidoPaterno")
    @Mapping(target = "apellidoMaterno_presentante", source = "entity.presentante.apellidoMaterno")
    @Mapping(target = "razonSocial_presentante", source = "entity.presentante.razonSocial")
    @Mapping(target = "telefono_presentante", source = "entity.presentante.telefono")
    @Mapping(target = "domicilio_presentante", source = "entity.presentante.domicilio")
    @Mapping(target = "correoElectronico_presentante", source = "entity.presentante.correoElectronico")
    @Mapping(target = "resultadoPorCorreo_presentante", expression = "java(entity.getPresentante() != null && entity.getPresentante().getResultadoPorCorreo() != null ? entity.getPresentante().getResultadoPorCorreo().getCodigo() : null)")

    // ... y así para todos los demás campos del presentante ...

    // --- Mapeos para Afectado ---
    @Mapping(target = "tipoDocumento_afectado", expression = "java(entity.getAfectado() != null && entity.getAfectado().getTipoDocumento() != null ? entity.getAfectado().getTipoDocumento().getCodigo() : null)")
    @Mapping(target = "numeroDocumento_afectado", source = "entity.afectado.numeroDocumento")
    @Mapping(target = "nombres_afectado", source = "entity.afectado.nombres")
    @Mapping(target = "apellidoPaterno_afectado", source = "entity.afectado.apellidoPaterno")
    @Mapping(target = "apellidoMaterno_afectado", source = "entity.afectado.apellidoMaterno")
    @Mapping(target = "razonSocial_afectado", source = "entity.afectado.razonSocial")

    // ... y así para todos los demás campos del afectado ...

    // Mapeos para DetalleReclamo
    @Mapping(target = "medioPresentacion", expression = "java(detalleReciente != null && detalleReciente.getMedioPresentacion() != null ? detalleReciente.getMedioPresentacion().getCodigo() : null)")
    @Mapping(target = "fechaRecepcion", expression = "java(detalleReciente != null ? detalleReciente.getFechaRecepcion() : null)")
    @Mapping(target = "descripcion", expression = "java(detalleReciente != null ? detalleReciente.getDescripcion() : null)")

    // --- 4. Mapeos desde 'gestion' (GestionReclamo) ---
    @Mapping(target = "servicio", expression = "java(gestion != null && gestion.getServicio() != null ? gestion.getServicio().getCodigo() : null)")
    @Mapping(target = "competencia", expression = "java(gestion != null && gestion.getCompetencia() != null ? gestion.getCompetencia().getCodigo() : null)")
    @Mapping(target = "clasificacion1",          expression = "java(gestion != null ? gestion.getClasificacion1() : null)")
    @Mapping(target = "clasificacion2",          expression = "java(gestion != null ? gestion.getClasificacion2() : null)")
    @Mapping(target = "clasificacion3",          expression = "java(gestion != null ? gestion.getClasificacion3() : null)")
    @Mapping(target = "etapaReclamo", expression = "java(gestion != null && gestion.getEtapaReclamo() != null ? gestion.getEtapaReclamo().getCodigo() : null)")
    @Mapping(target = "codigoPrimigenio", expression = "java(gestion != null ? gestion.getCodigoPrimigenio() : null)")
    @Mapping(target = "tipoAdministraTraslado", expression = "java(gestion != null && gestion.getTipoAdministraTraslado() != null ? gestion.getTipoAdministraTraslado().getCodigo() : null)")
    @Mapping(target = "codigoAdministraTraslado", expression = "java(gestion != null ? gestion.getCodigoAdministraTraslado() : null)")

    // --- 5. Mapeos desde 'resultadoReciente' (ResultadoNotificacion) ---
    @Mapping(target = "resultado", expression = "java(resultadoReciente != null && resultadoReciente.getResultado() != null ? resultadoReciente.getResultado().getCodigo() : null)")
    @Mapping(target = "motivoConclusion", expression = "java(resultadoReciente != null && resultadoReciente.getMotivoConclusionAnticipada() != null ? resultadoReciente.getMotivoConclusionAnticipada().getCodigo() : null)")
    @Mapping(target = "fechaResultado",          expression = "java(resultadoReciente != null ? resultadoReciente.getFechaResultado() : null)")
    @Mapping(target = "comunicacionResultado", expression = "java(resultadoReciente != null && resultadoReciente.getComunicacionResultado() != null ? resultadoReciente.getComunicacionResultado().getCodigo() : null)")
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
            @Context DetalleReclamo detalleReciente,
            @Context GestionReclamo gestion,
            @Context ResultadoNotificacion resultadoReciente,
            @Context MedidasAdoptadas medidaReciente

    );

    // --- MAPEADORES PARA FORMULARIO: DTO <-> Entidad ---
    // Estos ahora también son más simples y automáticos.
    @Mapping(target = "estado", source = "estadoReclamo")
    ReclamoDTO toDetailDTO(Reclamos entity);

    @Mapping(target = "estadoReclamo", source = "estado")
    Reclamos toEntity(ReclamoDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "estadoReclamo", source = "estado")
    void updateFromDto(ReclamoDTO dto, @MappingTarget Reclamos entity);


    // --- MÉTODO DE UTILIDAD (sin cambios) ---
    default <T extends Enum<T>> T toEnum(String value, Class<T> enumClass) {
        if (value == null || value.isBlank() || enumClass == null) {
            return null;
        }
        try {
            return Enum.valueOf(enumClass, value.trim());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
