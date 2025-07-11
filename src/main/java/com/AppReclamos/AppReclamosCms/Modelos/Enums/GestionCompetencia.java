package com.AppReclamos.AppReclamosCms.Modelos.Enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum GestionCompetencia {
    SI(1, "Sí"),
    NO(2, "No"),
    COMPARTIDA(3, "Compartida");

    private final int codigo;
    private final String descripcion;

    GestionCompetencia(int codigo, String descripcion) {
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

    public static GestionCompetencia fromCodigo(Integer codigo) {
        if (codigo == null) {
            return null;
        }
        return Arrays.stream(values())
                .filter(c -> c.getCodigo() == codigo)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Código de GestionCompetencia no válido: " + codigo));
    }
}