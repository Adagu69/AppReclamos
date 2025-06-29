package com.AppReclamos.AppReclamosCms.Modelos;

import com.AppReclamos.AppReclamosCms.Modelos.Enums.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GestionReclamoDTO {

    /* ---------- Identificador (solo en respuestas / edición) ---------- */
    private Integer idGestion;

    @NotNull
    @Size(max = 255)
    private GestionServicios servicio;

    @NotNull
    @Size(max = 50)
    private GestionCompetencia competencia;


    @Size(max = 50)
    private String clasificacion1;               // Primer nivel de clasif.
    @Size(max = 50)
    private String clasificacion2;               // Segundo nivel
    @Size(max = 50)
    private String clasificacion3;               // Tercer nivel

    /* ---------- Estado y etapa (usa enums si son catálogos fijos) ---------- */
    private EstadoGestion estado;
    @NotNull private GestionEtapa etapaReclamo;

    /* ---------- Datos de rastreo / códigos ----------------------- */
    @Size(max = 30)
    private String codigoPrimigenio;

    @NotNull
    private TipoDeclarante tipoAdministraTraslado;

    @Size(max = 8)
    private String codigoAdministraTraslado;
}
