package com.AppReclamos.AppReclamosCms.Modelos;

import com.AppReclamos.AppReclamosCms.Modelos.Enums.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Value
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReclamoTablaDTO {

    /* ---------- Identificadores ---------- */
    Integer id;
    private Integer estado;

    // --- ¡CLASIFICACION DEL RECLAMO! ---
    @NotNull(message="Fecha de reclamo es obligatoria")
    private String periodoDeclaracion;

    private Integer tipoDeclarante;
    private String codigoDeclarante;
    private String codigoUgipress;
    private Integer tipoInstitucion;
    private String codigoInstitucion;
    private Integer medioRecepcion;
    String  codigoReclamo;

    // --- ¡CAMBIO CLAVE! ---
    // Se reemplazan los objetos anidados por campos planos para la tabla y exportación.

    // --- Datos del Presentante ---
    private Integer tipoDocumento_presentante;
    private String numeroDocumento_presentante;
    private String razonSocial_presentante;
    private String nombres_presentante;
    private String apellidoPaterno_presentante;
    private String apellidoMaterno_presentante;
    private String telefono_presentante;
    private String domicilio_presentante;
    private String correoElectronico_presentante;
    private Integer resultadoPorCorreo_presentante;

    // --- Datos del Afectado ---
    private Integer tipoDocumento_afectado;
    private String numeroDocumento_afectado;
    private String razonSocial_afectado;
    private String nombres_afectado;
    private String apellidoPaterno_afectado;
    private String apellidoMaterno_afectado;


    // --- ¡DETALLE DEL RECLAMO! ---
    private String fechaRecepcion;
    private String descripcion;
    private Integer medioPresentacion;


    // --- ¡DE LA GESTIÓN DEL RECLAMO! ---
    private String servicio;
    private Integer competencia;
    private String clasificacion1;
    private String clasificacion2;
    private String clasificacion3;
    private Integer etapaReclamo;
    private String codigoPrimigenio;
    private Integer tipoAdministraTraslado;
    private String codigoAdministraTraslado;



    // --- ¡RESULTADO Y NOTIFICACIÓN DEL RECLAMO! ---
    private Integer resultado;
    private Integer motivoConclusion;
    private String fechaResultado;      // Formato AAAAMMDD
    private String fechaNotificacion;   // Formato AAAAMMDD
    private Integer comunicacionResultado;


    private String codigoMedida;
    private NaturalezaMedida naturaleza;
    private ProcesoMedida proceso;
    private LocalDate fechaInicioImplementacion;
    private LocalDate fechaCulminacionPrevista;
    private String descripcionMedida;


    /* ---------- UI helpers ---------- */
    @Builder.Default
    boolean seleccionado = false;    // usado solo en el front (checkbox masivo)     // UI helper

}
