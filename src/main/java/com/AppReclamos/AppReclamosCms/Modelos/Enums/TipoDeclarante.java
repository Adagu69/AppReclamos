package com.AppReclamos.AppReclamosCms.Modelos.Enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoDeclarante {
    IPRESS(1, "IPRESS"),
    UGIPRESS(2, "UGIPRESS"),
    IAFAS(3, "IAFAS");

    private final int codigo;
    private final String descripcion;

    TipoDeclarante(int codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    // --- MÉTODOS EXISTENTES ---
    @JsonValue
    public int getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    // --- ¡NUEVO MÉTODO PARA LA GUÍA! ---
    /**
     * Devuelve el texto formateado para mostrar en los dropdowns del formulario.
     * Ejemplo: "1 - IPRESS"
     * @return El texto de la guía para el usuario.
     */
    public String getGuiaUsuario() {
        return this.codigo + " - " + this.descripcion;
    }

    // Método fromCodigo (se mantiene igual)
    public static TipoDeclarante fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        for (TipoDeclarante tipo : TipoDeclarante.values()) {
            if (tipo.getCodigo() == codigo) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Código no válido: " + codigo);
    }
}