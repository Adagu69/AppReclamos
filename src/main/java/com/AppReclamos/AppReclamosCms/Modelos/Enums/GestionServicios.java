package com.AppReclamos.AppReclamosCms.Modelos.Enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum GestionServicios {
    CONSULTA_EXTERNA("01", "Consulta Externa"),
    HOSPITALIZACION("02", "Hospitalización"),
    EMERGENCIA("03", "Emergencia"),
    CENTRO_QUIRURGICO("04", "Centro Quirúrgico"),
    CENTRO_OBSTETRICO("05", "Centro Obstétrico"),
    UCI_UCIN("06", "UCI o UCIN"),
    FARMACIA("07", "Farmacia"),
    SERVICIOS_MEDICOS_APOYO("08", "Servicios Médicos de Apoyo"),
    ATENCION_DOMICILIO_AMBULATORIA("09", "Atención a domicilio, consulta ambulatoria"),
    ATENCION_DOMICILIO_URGENCIA("10", "Atención a domicilio, urgencia o emergencia"),
    OFICINAS_ADMINISTRATIVAS("11", "Oficinas o áreas administrativas de IAFAS o IPRESS o UGIPRESS"),
    INFRAESTRUCTURA("12", "Infraestructura"),
    REFERENCIA_CONTRAREFERENCIA("13", "Referencia y Contrareferencia");

    private final String codigo;
    private final String descripcion;

    GestionServicios(String codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    @JsonValue
    public String getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getGuiaUsuario() {
        return this.codigo + " - " + this.descripcion;
    }

    public static GestionServicios fromCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            return null;
        }
        return Arrays.stream(values())
                .filter(servicio -> servicio.getCodigo().equals(codigo))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Código de GestionServicios no válido: " + codigo));
    }
}