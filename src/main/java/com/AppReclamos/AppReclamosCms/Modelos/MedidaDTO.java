package com.AppReclamos.AppReclamosCms.Modelos;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedidaDTO {


    private Integer id;

    @NotBlank
    @Size(max = 50)
    private String tipoProcedimiento;

    @NotBlank(message = "La descripción no puede estar vacía.")
    @Size(max = 2000, message = "La descripción no puede superar los 2000 caracteres.")
    private String descripcion;

    @NotNull(message = "La fecha de la medida es obligatoria.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fechaMedida;

    @NotBlank(message = "El responsable de la ejecución no puede estar vacío.")
    private String responsableEjecucion;

    @NotNull(message = "La fecha de ejecución es obligatoria.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fechaEjecucion;

    private Integer reclamoId;

    @Size(max = 30)
    private String naturaleza;

    @Size(max = 100)
    private String procedimiento;

}
