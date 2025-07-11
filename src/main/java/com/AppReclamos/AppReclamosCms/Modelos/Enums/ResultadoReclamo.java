package com.AppReclamos.AppReclamosCms.Modelos.Enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum ResultadoReclamo {
    PENDIENTE(0, "Pendiente"),
    FUNDADO(1, "Fundado"),
    FUNDADO_PARCIAL(2, "Fundado Parcial"),
    INFUNDADO(3, "Infundado"),
    IMPROCEDENTE(4, "Improcedente"),
    CONCLUIDO_ANTICIPADAMENTE(5, "Concluido anticipadamente");

    private final int codigo;
    private final String descripcion;

    ResultadoReclamo(int codigo, String descripcion) {
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

    public static ResultadoReclamo fromCodigo(Integer codigo) {
        if (codigo == null) {
            return null;
        }
        return Arrays.stream(values())
                .filter(resultado -> resultado.getCodigo() == codigo)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Código de ResultadoReclamo no válido: " + codigo));
    }
}
