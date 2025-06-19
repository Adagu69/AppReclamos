package com.AppReclamos.AppReclamosCms.Modelos;


import java.time.LocalDate;

public class MedidaDTO {

    private String tipoCodigo, descripcion, natMedado, procMedado;
    private LocalDate fechaIniImp, fechaCulPre;

    //getters and setters...


    public String getTipoCodigo() {
        return tipoCodigo;
    }

    public void setTipoCodigo(String tipoCodigo) {
        this.tipoCodigo = tipoCodigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNatMedado() {
        return natMedado;
    }

    public void setNatMedado(String natMedado) {
        this.natMedado = natMedado;
    }

    public String getProcMedado() {
        return procMedado;
    }

    public void setProcMedado(String procMedado) {
        this.procMedado = procMedado;
    }

    public LocalDate getFechaIniImp() {
        return fechaIniImp;
    }

    public void setFechaIniImp(LocalDate fechaIniImp) {
        this.fechaIniImp = fechaIniImp;
    }

    public LocalDate getFechaCulPre() {
        return fechaCulPre;
    }

    public void setFechaCulPre(LocalDate fechaCulPre) {
        this.fechaCulPre = fechaCulPre;
    }
}
