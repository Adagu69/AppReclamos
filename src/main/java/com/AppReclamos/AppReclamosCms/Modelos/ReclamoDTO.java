package com.AppReclamos.AppReclamosCms.Modelos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReclamoDTO {
    // --- Reclamos ---
    private Integer idReclamo;
    private LocalDate fechaReclamo;
    private String tipoDeclarante, codigoDeclarante, tipoInstitucion, codigoInstitucion, codigoUgipress, codigoReclamo, estadoReclamo;
    private String medioRecepcion;
    private LocalDate fechaRecepcion;
    private String detalle;
    private String resultado;
    private String motivoConclusion;
    private String comunicacionResultado;
    private LocalDate fechaResultado;
    private LocalDate fechaNotificacion;

    // --- Persona que presenta ---
    private String tipoDocumentoPresentante, numeroDocumentoPresentante;
    private String nombresPresentante, apellidoPaternoPresentante, apellidoMaternoPresentante, razonSocialPresentante;
    private String correoPresentante, telefonoPresentante, domicilioPresentante;

    // --- Usuario/Tercero
    private String tipoDocumentoTercero, numeroDocumentoTercero;
    private String nombresTercero, apellidoPaternoTercero, apellidoMaternoTercero, razonSocialTercero;

    // --- Gestión del reclamo
    private String servicioHecho, competencia, clasificacion1, clasificacion2, clasificacion3;
    private String estadoGestion, etapaGestion, codigoPrimigenio;
    private String tipoAdministradoraDerivada, codigoAdministradoraDerivada;

    // --- Gestión clínica
    private String compania, parentesco, area, origen, personalInvolucrado;

    // --- Medidas adoptadas (lista) ---
    private List<MedidaDTO> medidas = new ArrayList<>();

    // getters and setters..

    public Integer getIdReclamo() {
        return idReclamo;
    }

    public void setIdReclamo(Integer idReclamo) {
        this.idReclamo = idReclamo;
    }

    public LocalDate getFechaReclamo() {
        return fechaReclamo;
    }

    public void setFechaReclamo(LocalDate fechaReclamo) {
        this.fechaReclamo = fechaReclamo;
    }

    public String getTipoDeclarante() {
        return tipoDeclarante;
    }

    public void setTipoDeclarante(String tipoDeclarante) {
        this.tipoDeclarante = tipoDeclarante;
    }

    public String getCodigoDeclarante() {
        return codigoDeclarante;
    }

    public void setCodigoDeclarante(String codigoDeclarante) {
        this.codigoDeclarante = codigoDeclarante;
    }

    public String getTipoInstitucion() {
        return tipoInstitucion;
    }

    public void setTipoInstitucion(String tipoInstitucion) {
        this.tipoInstitucion = tipoInstitucion;
    }

    public String getCodigoInstitucion() {
        return codigoInstitucion;
    }

    public void setCodigoInstitucion(String codigoInstitucion) {
        this.codigoInstitucion = codigoInstitucion;
    }

    public String getCodigoUgipress() {
        return codigoUgipress;
    }

    public void setCodigoUgipress(String codigoUgipress) {
        this.codigoUgipress = codigoUgipress;
    }

    public String getCodigoReclamo() {
        return codigoReclamo;
    }

    public void setCodigoReclamo(String codigoReclamo) {
        this.codigoReclamo = codigoReclamo;
    }

    public String getEstadoReclamo() {
        return estadoReclamo;
    }

    public void setEstadoReclamo(String estadoReclamo) {
        this.estadoReclamo = estadoReclamo;
    }

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

    public String getTipoDocumentoPresentante() {
        return tipoDocumentoPresentante;
    }

    public void setTipoDocumentoPresentante(String tipoDocumentoPresentante) {
        this.tipoDocumentoPresentante = tipoDocumentoPresentante;
    }

    public String getNumeroDocumentoPresentante() {
        return numeroDocumentoPresentante;
    }

    public void setNumeroDocumentoPresentante(String numeroDocumentoPresentante) {
        this.numeroDocumentoPresentante = numeroDocumentoPresentante;
    }

    public String getNombresPresentante() {
        return nombresPresentante;
    }

    public void setNombresPresentante(String nombresPresentante) {
        this.nombresPresentante = nombresPresentante;
    }

    public String getApellidoPaternoPresentante() {
        return apellidoPaternoPresentante;
    }

    public void setApellidoPaternoPresentante(String apellidoPaternoPresentante) {
        this.apellidoPaternoPresentante = apellidoPaternoPresentante;
    }

    public String getApellidoMaternoPresentante() {
        return apellidoMaternoPresentante;
    }

    public void setApellidoMaternoPresentante(String apellidoMaternoPresentante) {
        this.apellidoMaternoPresentante = apellidoMaternoPresentante;
    }

    public String getRazonSocialPresentante() {
        return razonSocialPresentante;
    }

    public void setRazonSocialPresentante(String razonSocialPresentante) {
        this.razonSocialPresentante = razonSocialPresentante;
    }

    public String getCorreoPresentante() {
        return correoPresentante;
    }

    public void setCorreoPresentante(String correoPresentante) {
        this.correoPresentante = correoPresentante;
    }

    public String getTelefonoPresentante() {
        return telefonoPresentante;
    }

    public void setTelefonoPresentante(String telefonoPresentante) {
        this.telefonoPresentante = telefonoPresentante;
    }

    public String getDomicilioPresentante() {
        return domicilioPresentante;
    }

    public void setDomicilioPresentante(String domicilioPresentante) {
        this.domicilioPresentante = domicilioPresentante;
    }

    public String getTipoDocumentoTercero() {
        return tipoDocumentoTercero;
    }

    public void setTipoDocumentoTercero(String tipoDocumentoTercero) {
        this.tipoDocumentoTercero = tipoDocumentoTercero;
    }

    public String getNumeroDocumentoTercero() {
        return numeroDocumentoTercero;
    }

    public void setNumeroDocumentoTercero(String numeroDocumentoTercero) {
        this.numeroDocumentoTercero = numeroDocumentoTercero;
    }

    public String getNombresTercero() {
        return nombresTercero;
    }

    public void setNombresTercero(String nombresTercero) {
        this.nombresTercero = nombresTercero;
    }

    public String getApellidoPaternoTercero() {
        return apellidoPaternoTercero;
    }

    public void setApellidoPaternoTercero(String apellidoPaternoTercero) {
        this.apellidoPaternoTercero = apellidoPaternoTercero;
    }

    public String getApellidoMaternoTercero() {
        return apellidoMaternoTercero;
    }

    public void setApellidoMaternoTercero(String apellidoMaternoTercero) {
        this.apellidoMaternoTercero = apellidoMaternoTercero;
    }

    public String getRazonSocialTercero() {
        return razonSocialTercero;
    }

    public void setRazonSocialTercero(String razonSocialTercero) {
        this.razonSocialTercero = razonSocialTercero;
    }

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

    public String getCompania() {
        return compania;
    }

    public void setCompania(String compania) {
        this.compania = compania;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getPersonalInvolucrado() {
        return personalInvolucrado;
    }

    public void setPersonalInvolucrado(String personalInvolucrado) {
        this.personalInvolucrado = personalInvolucrado;
    }

    public List<MedidaDTO> getMedidas() {
        return medidas;
    }

    public void setMedidas(List<MedidaDTO> medidas) {
        this.medidas = medidas;
    }
}
