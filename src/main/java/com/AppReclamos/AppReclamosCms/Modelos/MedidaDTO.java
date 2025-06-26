package com.AppReclamos.AppReclamosCms.Modelos;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MedidaDTO {

    /* ---------- Identificador (solo al editar / responder) ---------- */
    private Integer id;                       // null en creación

    /* ---------- Datos de la medida ---------- */

    /** Código o tipo de procedimiento administrativo (catálogo interno). */
    @NotBlank
    @Size(max = 50)
    private String tipoProcedimiento;

    /** Descripción concreta de la medida adoptada. */
    @NotBlank
    @Size(max = 500)
    private String descripcion;

    /** Naturaleza de la medida: PREVENTIVA, CORRECTIVA, INFORMATIVA, etc. */
    @Size(max = 30)
    private String naturaleza;

    /** Procedimiento empleado (si aplica): derivación, restitución, resarcimiento… */
    @Size(max = 100)
    private String procedimiento;

    /** Fecha de inicio de la implementación. */
    private LocalDate fechaInicio;

    /** Fecha de culminación o cumplimiento. */
    private LocalDate fechaCulminacion;
}
