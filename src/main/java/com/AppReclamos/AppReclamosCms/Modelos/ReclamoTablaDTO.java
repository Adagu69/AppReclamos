package com.AppReclamos.AppReclamosCms.Modelos;

import java.time.LocalDate;

public class ReclamoTablaDTO {

    // Perdec: Fecha del reclamo
    private LocalDate fechaReclamo;
    // Medprerecl: Medio (ej: "Físico")
    private String medioReclamo;
    // Codunirecl: Código del reclamo
    private String codigoReclamo;
    // Tipodocusuafec: Tipo doc usuario afectado
    private String tipoDocumentoAfectado;
    // Numdocusuafec: N° doc usuario afectado
    private String numeroDocumentoAfectado;
    // Razsocusuafec: Razón social usuario afectado
    private String razonSocialAfectado;
    // Nomusuafec: Nombre usuario afectado
    private String nombreAfectado;

    private String estado;

    // GETTERS AND SETTERS

    public LocalDate getFechaReclamo() {
        return fechaReclamo;
    }

    public void setFechaReclamo(LocalDate fechaReclamo) {
        this.fechaReclamo = fechaReclamo;
    }

    public String getMedioReclamo() {
        return medioReclamo;
    }

    public void setMedioReclamo(String medioReclamo) {
        this.medioReclamo = medioReclamo;
    }

    public String getCodigoReclamo() {
        return codigoReclamo;
    }

    public void setCodigoReclamo(String codigoReclamo) {
        this.codigoReclamo = codigoReclamo;
    }

    public String getTipoDocumentoAfectado() {
        return tipoDocumentoAfectado;
    }

    public void setTipoDocumentoAfectado(String tipoDocumentoAfectado) {
        this.tipoDocumentoAfectado = tipoDocumentoAfectado;
    }

    public String getNumeroDocumentoAfectado() {
        return numeroDocumentoAfectado;
    }

    public void setNumeroDocumentoAfectado(String numeroDocumentoAfectado) {
        this.numeroDocumentoAfectado = numeroDocumentoAfectado;
    }

    public String getRazonSocialAfectado() {
        return razonSocialAfectado;
    }

    public void setRazonSocialAfectado(String razonSocialAfectado) {
        this.razonSocialAfectado = razonSocialAfectado;
    }

    public String getNombreAfectado() {
        return nombreAfectado;
    }

    public void setNombreAfectado(String nombreAfectado) {
        this.nombreAfectado = nombreAfectado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
