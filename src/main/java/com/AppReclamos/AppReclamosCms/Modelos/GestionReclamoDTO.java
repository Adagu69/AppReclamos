package com.AppReclamos.AppReclamosCms.Modelos;

public class GestionReclamoDTO {
    private String servicioHecho;
    private String competencia, clasificacion1, clasificacion2, clasificacion3;
    private String estadoGestion, etapaGestion, codigoPrimigenio, tipoAdministradoraDerivada, codigoAdministradoraDerivada;

    public String getServicioHecho() {
        return servicioHecho;
    }

    public void setServicioHecho(String servicioHecho) {
        this.servicioHecho = servicioHecho;
    }

    public String getCompetencia() {
        return competencia;
    }

    public void setCompetencia(String competencia) {
        this.competencia = competencia;
    }

    public String getClasificacion1() {
        return clasificacion1;
    }

    public void setClasificacion1(String clasificacion1) {
        this.clasificacion1 = clasificacion1;
    }

    public String getClasificacion2() {
        return clasificacion2;
    }

    public void setClasificacion2(String clasificacion2) {
        this.clasificacion2 = clasificacion2;
    }

    public String getClasificacion3() {
        return clasificacion3;
    }

    public void setClasificacion3(String clasificacion3) {
        this.clasificacion3 = clasificacion3;
    }

    public String getEstadoGestion() {
        return estadoGestion;
    }

    public void setEstadoGestion(String estadoGestion) {
        this.estadoGestion = estadoGestion;
    }

    public String getEtapaGestion() {
        return etapaGestion;
    }

    public void setEtapaGestion(String etapaGestion) {
        this.etapaGestion = etapaGestion;
    }

    public String getCodigoPrimigenio() {
        return codigoPrimigenio;
    }

    public void setCodigoPrimigenio(String codigoPrimigenio) {
        this.codigoPrimigenio = codigoPrimigenio;
    }

    public String getTipoAdministradoraDerivada() {
        return tipoAdministradoraDerivada;
    }

    public void setTipoAdministradoraDerivada(String tipoAdministradoraDerivada) {
        this.tipoAdministradoraDerivada = tipoAdministradoraDerivada;
    }

    public String getCodigoAdministradoraDerivada() {
        return codigoAdministradoraDerivada;
    }

    public void setCodigoAdministradoraDerivada(String codigoAdministradoraDerivada) {
        this.codigoAdministradoraDerivada = codigoAdministradoraDerivada;
    }
}
