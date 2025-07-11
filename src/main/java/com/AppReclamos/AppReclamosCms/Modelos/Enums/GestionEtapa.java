package com.AppReclamos.AppReclamosCms.Modelos.Enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum GestionEtapa {
    ADMISION_Y_REGISTRO(1, "Admisión y Registro"),
    EVALUACION_E_INVESTIGACION(2, "Evaluación e Investigación"),
    RESULTADO_Y_NOTIFICACION(3, "Resultado y Notificación"),
    ARCHIVO_Y_CUSTODIA(4, "Archivo y Custodia del Expediente");

    private final int codigo;
    private final String descripcion;

    GestionEtapa(int codigo, String descripcion) {
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

    public static GestionEtapa fromCodigo(Integer codigo) {
        if (codigo == null) {
            return null;
        }
        return Arrays.stream(values())
                .filter(etapa -> etapa.getCodigo() == codigo)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Código de GestionEtapa no válido: " + codigo));
    }
}