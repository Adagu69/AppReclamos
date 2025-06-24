package com.AppReclamos.AppReclamosCms.Modelos;

import java.time.LocalDate;

public class DetalleReclamoDTO {
    private String medioRecepcion;
    private LocalDate fechaRecepcion;
    private String detalle;

    public String getMedioRecepcion() {
        return medioRecepcion;
    }

    public void setMedioRecepcion(String medioRecepcion) {
        this.medioRecepcion = medioRecepcion;
    }

    public LocalDate getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(LocalDate fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }
}
