package com.AppReclamos.AppReclamosCms.Modelos;

import com.AppReclamos.AppReclamosCms.Modelos.Enums.EstadoReclamo;
import com.AppReclamos.AppReclamosCms.Modelos.Enums.MedioRecepcion;
import com.AppReclamos.AppReclamosCms.Modelos.Enums.TipoDeclarante;
import com.AppReclamos.AppReclamosCms.Modelos.Enums.TipoInstitucion;
import com.AppReclamos.AppReclamosCms.Validations.ValidCodigoDeclarante;
import com.AppReclamos.AppReclamosCms.Validations.ValidCodigoInstitucion;
import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ValidCodigoDeclarante     // valida codigoDeclarante ↔ tipoDeclarante
@ValidCodigoInstitucion   // valida codigoInstitucion ↔ tipoInstitucion
public class ReclamoDTO {

    /* ---------- Identificación & clasificación ---------- */
    private Integer idReclamo;                       // PK

    @NotNull(message="Fecha de reclamo es obligatoria")
    @Column(name = "periodo_declaracion", nullable = false, length = 6)
    private String periodoDeclaracion;

    @NotNull(message="Tipo de declarante es obligatorio")
    private TipoDeclarante tipoDeclarante;

    @NotBlank(message="Código declarante no puede ser vacío")
    private String       codigoDeclarante;

    @NotNull(message="Tipo de institución es obligatorio")
    private TipoInstitucion tipoInstitucion;

    @NotBlank(message="Código de institución no puede ser vacío")
    private String       codigoInstitucion;

    /** UGIPRESS al que pertenece la IPRESS (o igual al declarante si no aplica) */
    @NotBlank(message="Código UGIPRESS no puede ser vacío")
    private String       codigoUgipress;

    /** Código interno / primigenio asignado por el sistema */
    @NotBlank(message="Código de reclamo no puede ser vacío")
    private String       codigoReclamo;

    /** Código correlativo visible para el usuario (si lo manejas aparte) */
    private String       nroReclamo;

    @NotNull(message="Medio de recepción principal es obligatorio")
    private MedioRecepcion medioRecepcion;

    @NotNull(message="Estado es obligatorio")
    private EstadoReclamo estado;

    /* ---------- Personas involucradas ---------- */
    // En ReclamoDTO.java
    @Valid // Añade validación en cascada
    private PersonaReclamoDTO presentante = new PersonaReclamoDTO();

    @Valid // Añade validación en cascada
    private PersonaReclamoDTO afectado = new PersonaReclamoDTO();

    /* ---------- Detalles del reclamo (puede haber varios) ---------- */
    private List<DetalleReclamoDTO> detalles = new ArrayList<>();

    /* ---------- Gestión interna ---------- */
    private GestionReclamoDTO gestion;
    private GestionClinicaDTO gestionClinica;

    /* ---------- Medidas adoptadas ---------- */
    private List<MedidaDTO> medidas = new ArrayList<>();

    /* ---------- Resultado y notificaciones ---------- */
    private List<ResultadoNotificacionDTO> resultados = new ArrayList<>();

    /**
     * Según C1/C2 de UGIPRESS:
     * Si es IPRESS y tiene una UGIPRESS asignada, devuelvo esa UGIPRESS,
     * en otro caso el mismo codigoDeclarante/UGIPRESS según corresponda.
     */
    public String getCodigoUgipressEfectivo() {
        if (tipoDeclarante == TipoDeclarante.IPRESS
                && codigoUgipress != null
                && !codigoUgipress.isBlank()) {
            return codigoUgipress;
        }
        // si es UGIPRESS o IAFAS, asumo que codigoUgipress ya trae el valor correcto
        return codigoUgipress;
    }


}

