package com.AppReclamos.AppReclamosCms.Modelos;


import com.AppReclamos.AppReclamosCms.Modelos.Enums.NaturalezaMedida;
import com.AppReclamos.AppReclamosCms.Modelos.Enums.ProcesoMedida;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedidaDTO {


    private Integer id;

    // El código se genera automáticamente, no se edita manualmente
    private String codigoMedida;


    @NotNull(message = "La naturaleza es obligatoria.")
    private NaturalezaMedida naturaleza;

    @NotNull(message = "El proceso es obligatorio.")
    private ProcesoMedida proceso;

    // Campos de fecha como String en formato AAAAMMDD
    @NotBlank(message = "La fecha de inicio es obligatoria.")
    @Pattern(regexp = "^\\d{8}$", message = "La fecha debe tener formato AAAAMMDD (8 dígitos)")
    private String fechaInicioImplementacion;

    @NotBlank(message = "La fecha de culminación es obligatoria.")
    @Pattern(regexp = "^\\d{8}$", message = "La fecha debe tener formato AAAAMMDD (8 dígitos)")
    private String fechaCulminacionPrevista;

    @NotBlank(message = "La descripción no puede estar vacía.")
    @Size(max = 2000, message = "La descripción no puede superar los 2000 caracteres.")
    private String descripcionMedida;


    private Integer reclamoId;

    // Métodos para convertir de String AAAAMMDD a LocalDate y viceversa
    public LocalDate getFechaInicioAsLocalDate() {
        if (fechaInicioImplementacion == null || fechaInicioImplementacion.isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(fechaInicioImplementacion, DateTimeFormatter.ofPattern("yyyyMMdd"));
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public LocalDate getFechaCulminacionAsLocalDate() {
        if (fechaCulminacionPrevista == null || fechaCulminacionPrevista.isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(fechaCulminacionPrevista, DateTimeFormatter.ofPattern("yyyyMMdd"));
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public void setFechaInicioFromLocalDate(LocalDate fecha) {
        this.fechaInicioImplementacion = fecha != null
            ? fecha.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
            : "";
    }

    public void setFechaCulminacionFromLocalDate(LocalDate fecha) {
        this.fechaCulminacionPrevista = fecha != null
            ? fecha.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
            : "";
    }
}
