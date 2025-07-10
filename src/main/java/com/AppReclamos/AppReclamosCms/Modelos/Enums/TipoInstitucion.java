package com.AppReclamos.AppReclamosCms.Modelos.Enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum TipoInstitucion {
    // Definimos cada constante con su código y descripción
    IPRESS(1, "IPRESS"),
    UGIPRESS(2, "UGIPRESS"),
    IAFAS(3, "IAFAS");

    private final int codigo;
    private final String descripcion;

    TipoInstitucion(int codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    /**
     * Devuelve el código numérico. Esencial para el converter y las APIs.
     */
    @JsonValue
    public int getCodigo() {
        return codigo;
    }

    /**
     * Devuelve la descripción textual.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Devuelve el texto para mostrar en los dropdowns del formulario.
     * Ejemplo: "1 - IPRESS"
     */
    public String getGuiaUsuario() {
        return this.codigo + " - " + this.descripcion;
    }

    /**
     * Método estático para encontrar un enum a partir de su código.
     */
    public static TipoInstitucion fromCodigo(Integer codigo) {
        if (codigo == null) {
            return null;
        }
        return Arrays.stream(values())
                .filter(tipo -> tipo.getCodigo() == codigo)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Código de TipoInstitucion no válido: " + codigo));
    }
}
