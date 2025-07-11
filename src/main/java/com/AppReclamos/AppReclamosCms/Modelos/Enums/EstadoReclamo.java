package com.AppReclamos.AppReclamosCms.Modelos.Enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum EstadoReclamo {
    RESUELTO(1, "Resuelto"),
    EN_TRAMITE(2, "En trámite"),
    TRASLADADO(3, "Trasladado"),
    ARCHIVADO_DUPLICIDAD(4, "Archivado por duplicidad"),
    ACUMULADO(5, "Acumulado"),
    CONCLUIDO(6, "Concluido");

    private final int codigo;
    private final String descripcion;

    EstadoReclamo(int codigo, String descripcion) {
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

    public static EstadoReclamo fromCodigo(Integer codigo) {
        if (codigo == null) {
            return null;
        }
        return Arrays.stream(values())
                .filter(estado -> estado.getCodigo() == codigo)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Código de EstadoReclamo no válido: " + codigo));
    }
}