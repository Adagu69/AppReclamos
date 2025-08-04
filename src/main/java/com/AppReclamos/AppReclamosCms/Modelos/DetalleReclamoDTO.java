package com.AppReclamos.AppReclamosCms.Modelos;

import com.AppReclamos.AppReclamosCms.Modelos.Enums.MedioPresentacion;
import com.AppReclamos.AppReclamosCms.Modelos.Enums.MedioRecepcion;
import com.AppReclamos.AppReclamosCms.Validations.ValidDetalle;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ValidDetalle
public class DetalleReclamoDTO {
    /* ---------- Identificador (solo al editar / responder) ---------- */
    private Integer idDetalle;                        // null durante la creación

    /* ---------- Datos del detalle ---------- */

    /** Medio por el que se recibió la comunicación (catálogo fijo). */
    private MedioPresentacion medioPresentacion;  // VENTANILLA | CORREO | WEB | TELEFONICO | OTRO

    /** Fecha en que se registró o se recibió el reclamo, en formato AAAAMMDD. */
    @NotBlank(message="La fecha de recepción es obligatoria")
    @Pattern(regexp = "^(20)\\d{6}$", message = "El formato debe ser AAAAMMDD y el año debe empezar con 20")
    @Size(min = 8, max = 8, message = "La fecha debe tener exactamente 8 dígitos")
    private String fechaRecepcion;

    /** Descripción libre del hecho o ampliación del reclamo. */
    @NotBlank
    @Size(max = Integer.MAX_VALUE)
    private String descripcion;
}
