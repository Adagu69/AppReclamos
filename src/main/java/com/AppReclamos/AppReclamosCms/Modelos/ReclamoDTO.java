package com.AppReclamos.AppReclamosCms.Modelos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReclamoDTO {
    // --- Reclamos ---
    private Integer idReclamo;
    // CLASIFICACIÓN DEL RECLAMO
    private LocalDate fechaReclamo;
    private String tipoDeclarante, codigoDeclarante, tipoInstitucion, codigoInstitucion, codigoUgipress, codigoReclamo, estadoReclamo;

    // PRESENTANTE, USUARIO, TERCERO (varios tipos de personas)
    private List<PersonaReclamoDTO> personas = new ArrayList<>(); // [0] presentante, [1] usuario, [2] tercero (según tu lógica de front)

    // DETALLE DEL RECLAMO (puede ser uno o varios, pero normalmente solo uno)
    private DetalleReclamoDTO detalle;

    // GESTIÓN DEL RECLAMO
    private GestionReclamoDTO gestion;

    // RESULTADO Y NOTIFICACIÓN DEL RECLAMO
    private ResultadoNotificacionDTO resultado;

    // GESTIÓN CLÍNICA
    private GestionClinicaDTO gestionClinica;

    // MEDIDAS ADOPTADAS
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

    public List<PersonaReclamoDTO> getPersonas() {
        return personas;
    }

    public void setPersonas(List<PersonaReclamoDTO> personas) {
        this.personas = personas;
    }

    public DetalleReclamoDTO getDetalle() {
        return detalle;
    }

    public void setDetalle(DetalleReclamoDTO detalle) {
        this.detalle = detalle;
    }

    public GestionReclamoDTO getGestion() {
        return gestion;
    }

    public void setGestion(GestionReclamoDTO gestion) {
        this.gestion = gestion;
    }

    public ResultadoNotificacionDTO getResultado() {
        return resultado;
    }

    public void setResultado(ResultadoNotificacionDTO resultado) {
        this.resultado = resultado;
    }

    public GestionClinicaDTO getGestionClinica() {
        return gestionClinica;
    }

    public void setGestionClinica(GestionClinicaDTO gestionClinica) {
        this.gestionClinica = gestionClinica;
    }

    public List<MedidaDTO> getMedidas() {
        return medidas;
    }

    public void setMedidas(List<MedidaDTO> medidas) {
        this.medidas = medidas;
    }
}
