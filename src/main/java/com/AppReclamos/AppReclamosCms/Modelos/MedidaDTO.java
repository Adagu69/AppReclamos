package com.AppReclamos.AppReclamosCms.Modelos;


import com.AppReclamos.AppReclamosCms.Modelos.Enums.NaturalezaMedida;
import com.AppReclamos.AppReclamosCms.Modelos.Enums.ProcesoMedida;
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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedidaDTO {


    private Integer id;

    @NotBlank(message = "El código es obligatorio.")
    private String codigoMedida;


    @NotNull(message = "La naturaleza es obligatoria.")
    private NaturalezaMedida naturaleza;

    @NotNull(message = "El proceso es obligatorio.")
    private ProcesoMedida proceso;


    @NotNull(message = "La fecha de inicio es obligatoria.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fechaInicioImplementacion;

    @NotNull(message = "La fecha de culminación es obligatoria.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fechaCulminacionPrevista;

    @NotBlank(message = "La descripción no puede estar vacía.")
    @Size(max = 2000, message = "La descripción no puede superar los 2000 caracteres.")
    private String descripcionMedida;


    private Integer reclamoId;


}
