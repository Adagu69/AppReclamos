package com.AppReclamos.AppReclamosCms.Modelos;

import com.AppReclamos.AppReclamosCms.Modelos.Enums.ComunicacionResultado;
import com.AppReclamos.AppReclamosCms.Modelos.Enums.MedioRecepcion;
import com.AppReclamos.AppReclamosCms.Modelos.Enums.MotivoConclusion;
import com.AppReclamos.AppReclamosCms.Modelos.Enums.ResultadoReclamo;
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
public class ResultadoNotificacionDTO {

    private Integer id;

    @NotNull
    private ResultadoReclamo resultado;


    private MotivoConclusion motivoConclusion;

    @NotBlank(message="La fecha de resultado es obligatoria")
    @Pattern(regexp = "^(20)\\d{6}$", message = "El formato debe ser AAAAMMDD")
    @Size(min = 8, max = 8, message = "La fecha debe tener exactamente 8 dígitos")
    private String fechaResultado;

    @NotBlank(message="La fecha de notificación es obligatoria")
    @Pattern(regexp = "^(20)\\d{6}$", message = "El formato debe ser AAAAMMDD")
    @Size(min = 8, max = 8, message = "La fecha debe tener exactamente 8 dígitos")
    private String fechaNotificacion;

    @NotNull
    private ComunicacionResultado comunicacionResultado;


}
