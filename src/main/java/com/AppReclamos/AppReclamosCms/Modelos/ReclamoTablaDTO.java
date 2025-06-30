package com.AppReclamos.AppReclamosCms.Modelos;

import com.AppReclamos.AppReclamosCms.Modelos.Enums.EstadoReclamo;
import com.AppReclamos.AppReclamosCms.Modelos.Enums.MedioRecepcion;
import com.AppReclamos.AppReclamosCms.Modelos.Enums.TipoDocumento;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Value                       // inmutable (≃ Lombok @Getter + final + constructor)
@Builder(toBuilder = true)   // permite .toBuilder() para cambios puntuales
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReclamoTablaDTO {

    /* ---------- Identificadores ---------- */
    Integer id;                      // PK interna para links / acciones
    String  codigoReclamo;           // código único visible

    /* ---------- Fechas ---------- */
    @NotNull(message="Fecha de reclamo es obligatoria")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) // Formato yyyy-MM-dd
    LocalDate fechaReclamo;

    /* ---------- Datos clave para el operador ---------- */
    MedioRecepcion medioRecepcion;  // enum → badge
    EstadoReclamo estado;          // enum → pill/colores

    /* ---------- Usuario afectado ---------- */
    TipoDocumento tipoDocumentoAfectado;
    String        numeroDocumentoAfectado;
    String        nombreAfectado;          // si es persona natural
    String        razonSocialAfectado;     // si es persona jurídica

    /* ---------- UI helpers ---------- */
    @Builder.Default
    boolean seleccionado = false;    // usado solo en el front (checkbox masivo)     // UI helper

}
