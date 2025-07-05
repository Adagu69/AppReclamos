package com.AppReclamos.AppReclamosCms.Modelos;

import com.AppReclamos.AppReclamosCms.Modelos.Enums.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Value                       // inmutable (≃ Lombok @Getter + final + constructor)
@Builder(toBuilder = true)   // permite .toBuilder() para cambios puntuales
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReclamoTablaDTO {

    /* ---------- Identificadores ---------- */
    Integer id;                      // PK interna para links / acciones

    EstadoReclamo estado;



    // --- ¡CLASIFICACION DEL RECLAMO! ---
    @NotNull(message="Fecha de reclamo es obligatoria")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) // Formato yyyy-MM-dd
            LocalDate fechaReclamo;
    private TipoDeclarante tipoDeclarante;
    private String codigoDeclarante;
    private String codigoUgipress;
    private TipoInstitucion tipoInstitucion;
    private String codigoInstitucion;
    MedioRecepcion medioRecepcion;
    String  codigoReclamo;

    // --- ¡IDENTIFICACIÓN DE QUIEN PRESENTA EL RECLAMO! ---
    // --- ¡usuario o tercero! ---
    TipoDocumento tipoDocumentoAfectado;
    String        numeroDocumentoAfectado;
    String        nombreAfectado;
    String        razonSocialAfectado;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private Boolean resultadoPorCorreo;
    private String correoElectronico;
    private String domicilio;
    private String telefono;


    // --- ¡DETALLE DEL RECLAMO! ---
    private LocalDate fechaRecepcion;
    private String descripcion;


    // --- ¡DE LA GESTIÓN DEL RECLAMO! ---
    private GestionServicios servicio;
    private GestionCompetencia competencia;
    private String clasificacion1;
    private String clasificacion2;
    private String clasificacion3;
    private GestionEtapa etapaReclamo;
    private String codigoPrimigenio;
    private TipoDeclarante tipoAdministraTraslado;
    private String codigoAdministraTraslado;



    // --- ¡RESULTADO Y NOTIFICACIÓN DEL RECLAMO! ---
    private ResultadoReclamo resultado;
    private MotivoConclusion motivoConclusion;
    private LocalDate fechaResultado;
    private ComunicacionResultado comunicacionResultado;
    private LocalDate fechaNotificacion;


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
