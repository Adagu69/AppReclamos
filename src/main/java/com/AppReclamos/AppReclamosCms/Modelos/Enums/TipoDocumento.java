package com.AppReclamos.AppReclamosCms.Modelos.Enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum TipoDocumento {
    DNI(1, "DNI"),
    CE(2, "Carnet de Extranjería"),
    PASAPORTE(3, "Pasaporte"),
    DIE(4, "Documento de Identidad Extranjero"),
    CUI(5, "Código Único de Identificación"),
    CNV(6, "Certificado de Nacido Vivo"),
    PTP(10, "Permiso Temporal de Permanencia"),
    RUC(11, "Registro Único de Contribuyentes"),
    OTROS(12, "Otros");

    private final int codigo;
    private final String descripcion;

    TipoDocumento(int codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el código numérico del tipo de documento.
     * Usado para guardar en la BD y para respuestas JSON.
     * @return El código numérico.
     */
    @JsonValue
    public int getCodigo() {
        return codigo;
    }

    /**
     * Obtiene la descripción completa del tipo de documento.
     * @return La descripción textual.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Devuelve el texto formateado para mostrar en los dropdowns del formulario.
     * Ejemplo: "1 - DNI"
     * @return El texto de la guía para el usuario.
     */
    public String getGuiaUsuario() {
        return this.codigo + " - " + this.descripcion;
    }

    /**
     * Busca el enum a partir de su código numérico.
     * @param codigo El código a buscar.
     * @return El enum correspondiente o lanza una excepción si no lo encuentra.
     */
    public static TipoDocumento fromCodigo(Integer codigo) {
        if (codigo == null) {
            return null;
        }
        return Arrays.stream(values())
                .filter(tipo -> tipo.getCodigo() == codigo)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Código de TipoDocumento no válido: " + codigo));
    }
}
