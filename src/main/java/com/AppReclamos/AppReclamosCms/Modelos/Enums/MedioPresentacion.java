package com.AppReclamos.AppReclamosCms.Modelos.Enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum MedioPresentacion {
    LIBRO_VIRTUAL(1, "Libro de Reclamaciones Virtual"),
    LIBRO_FISICO(2, "Libro de Reclamaciones Físico"),
    LLAMADA_TELEFONICA(3, "Llamada telefónica"),
    RECLAMO_PRESENCIAL(4, "Reclamo presencial"),
    DOCUMENTO_ESCRITO(5, "Documento escrito"),
    RECLAMO_TRASLADADO(6, "Reclamo trasladado de otra administrada"),
    RECLAMO_COPARTICIPADO(7, "Reclamo coparticipado con otra administrada");

    private final int codigo;
    private final String descripcion;

    MedioPresentacion(int codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    @JsonValue
    public int getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getGuiaUsuario() {
        return this.codigo + " - " + this.descripcion;
    }

    public static MedioPresentacion fromCodigo(Integer codigo) {
        if (codigo == null) {
            return null;
        }
        return Arrays.stream(values())
                .filter(medio -> medio.getCodigo() == codigo)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Código de MedioPresentacion no válido: " + codigo));
    }
}
