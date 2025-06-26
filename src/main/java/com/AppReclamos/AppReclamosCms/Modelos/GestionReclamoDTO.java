package com.AppReclamos.AppReclamosCms.Modelos;

import com.AppReclamos.AppReclamosCms.Modelos.Enums.EstadoGestion;
import com.AppReclamos.AppReclamosCms.Modelos.Enums.EtapaGestion;
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
public class GestionReclamoDTO {
    /* ---------- Identificador (solo en respuestas / edición) ---------- */
    private Integer id;                          // null cuando se crea

    /* ---------- Servicio o área donde ocurrió el hecho ---------- */
    @NotBlank
    @Size(max = 255)
    private String servicio;                     // Ej.: “Hospitalización pediátrica”

    /* ---------- Competencia y clasificación ---------- */
    @Size(max = 50)
    private String competencia;                  // Ej.: “SUSALUD”, “MINSA”, etc.

    @Size(max = 50)
    private String clasificacion1;               // Primer nivel de clasif.
    @Size(max = 50)
    private String clasificacion2;               // Segundo nivel
    @Size(max = 50)
    private String clasificacion3;               // Tercer nivel

    /* ---------- Estado y etapa (usa enums si son catálogos fijos) ---------- */
    private EstadoGestion estado;                // REGISTRADO | EN_PROCESO | CONCLUIDO ...
    private EtapaGestion etapa;                 // INICIAL | APELACIÓN | ...

    /* ---------- Datos de rastreo / códigos ----------------------- */
    @Size(max = 30)
    private String codigoPrimigenio;             // Código original del reclamo

    @Size(max = 30)
    private String tipoAdministradoraDerivada;   // EPS, IPRESS, etc.

    @Size(max = 30)
    private String codigoAdministradoraDerivada; // Código de la administradora
}
