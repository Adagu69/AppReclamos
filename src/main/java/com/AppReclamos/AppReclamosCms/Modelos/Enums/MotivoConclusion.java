package com.AppReclamos.AppReclamosCms.Modelos.Enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum MotivoConclusion {
    DESISTIMIENTO_ESCRITO(1, "Desistimiento por escrito"),
    TRATO_DIRECTO(2, "Trato Directo"),
    CONCILIACION(3, "Conciliación"),
    TRANSACCION_EXTRAJUDICIAL(4, "Transacción Extrajudicial"),
    LAUDO_ARBITRAL(5, "Laudo Arbitral");

    private final int codigo;
    private final String descripcion;

    MotivoConclusion(int codigo, String descripcion) {
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

    public static MotivoConclusion fromCodigo(Integer codigo) {
        if (codigo == null) {
            return null;
        }
        return Arrays.stream(values())
                .filter(motivo -> motivo.getCodigo() == codigo)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Código de MotivoConclusion no válido: " + codigo));
    }
}