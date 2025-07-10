package com.AppReclamos.AppReclamosCms.Modelos.Enums;


import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum MedioRecepcion {

    VIRTUAL(1, "Virtual"),
    FISICO(2, "Físico"),
    TELEFONICO(3, "Telefónico");

    private final int codigo;
    private final String descripcion;

    MedioRecepcion(int codigo, String descripcion) {
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

    public static MedioRecepcion fromCodigo(Integer codigo) {
        if (codigo == null) {
            return null;
        }
        return Arrays.stream(values())
                .filter(medio -> medio.getCodigo() == codigo)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Código de MedioRecepcion no válido: " + codigo));
    }
}