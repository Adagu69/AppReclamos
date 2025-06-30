package com.AppReclamos.AppReclamosCms.Modelos;

import com.AppReclamos.AppReclamosCms.Modelos.Enums.MedioRecepcion;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class DetalleReclamoDTO {
    /* ---------- Identificador (solo al editar / responder) ---------- */
    private Integer idDetalle;                        // null durante la creación

    /* ---------- Datos del detalle ---------- */

    /** Medio por el que se recibió la comunicación (catálogo fijo). */
    private MedioRecepcion medioRecepcion;     // VENTANILLA | CORREO | WEB | TELEFONICO | OTRO

    /** Fecha en que se registró o se recibió el reclamo. */
    @NotNull(message="Fecha de reclamo es obligatoria")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) // Formato yyyy-MM-dd
    private LocalDate fechaRecepcion;

    /** Descripción libre del hecho o ampliación del reclamo. */
    @NotBlank
    @Size(max = 1_000)
    private String descripcion;
}
