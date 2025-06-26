package com.AppReclamos.AppReclamosCms.Modelos;

import java.time.LocalDate;

public class ResultadoNotificacionDTO {

    private String resultado;
    private String motivoConclusion;
    private String comunicacionResultado;
    private LocalDate fechaResultado;
    private LocalDate fechaNotificacion;

    // getters and setters..

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getMotivoConclusion() {
        return motivoConclusion;
    }

    public void setMotivoConclusion(String motivoConclusion) {
        this.motivoConclusion = motivoConclusion;
    }

    public String getComunicacionResultado() {
        return comunicacionResultado;
    }

    public void setComunicacionResultado(String comunicacionResultado) {
        this.comunicacionResultado = comunicacionResultado;
    }

    public LocalDate getFechaResultado() {
        return fechaResultado;
    }

    public void setFechaResultado(LocalDate fechaResultado) {
        this.fechaResultado = fechaResultado;
    }

    public LocalDate getFechaNotificacion() {
        return fechaNotificacion;
    }

    public void setFechaNotificacion(LocalDate fechaNotificacion) {
        this.fechaNotificacion = fechaNotificacion;
    }
}
