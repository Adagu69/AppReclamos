package com.AppReclamos.AppReclamosCms.Modelos;

import com.AppReclamos.AppReclamosCms.Modelos.Enums.OrigenGestion;
import com.AppReclamos.AppReclamosCms.Modelos.Enums.Parentesco;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GestionClinicaDTO {
    /* ---------- Identificador (solo en respuestas / edición) ---------- */
    private Integer id;                       // null cuando se crea

    /* ---------- Campos principales ---------- */

    /** Razón social de la compañía aseguradora o EPS. */
    @Size(max = 100)
    private String compania;

    /** Área de la clínica que gestiona la respuesta (Ej.: Atención al Paciente). */
    @Size(max = 100)
    private String area;

    /** Origen: cómo ingresa el caso a la clínica (call center, ventanilla, web, etc.). */
    private OrigenGestion origen;             // enum

    /** Parentesco respecto al paciente (PADRE, MADRE, APODERADO, OTRO…). */
    private Parentesco parentesco;            // enum

    /** Personal que intervino (médicos, enfermeras, etc.). */
    @Size(max = 255)
    private String personalInvolucrado;
}
