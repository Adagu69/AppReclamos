package com.AppReclamos.AppReclamosCms.Modelos;

import com.AppReclamos.AppReclamosCms.Modelos.Enums.ComunicacionResultado;
import com.AppReclamos.AppReclamosCms.Modelos.Enums.MedioRecepcion;
import com.AppReclamos.AppReclamosCms.Modelos.Enums.MotivoConclusion;
import com.AppReclamos.AppReclamosCms.Modelos.Enums.ResultadoReclamo;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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

    @NotNull
    private LocalDate fechaResultado;

    @NotNull
    private ComunicacionResultado comunicacionResultado;

    @NotNull
    private LocalDate fechaNotificacion;

}
