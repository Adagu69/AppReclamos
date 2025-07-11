package com.AppReclamos.AppReclamosCms.Modelos.Enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum ComunicacionResultado {
    DOMICILIO_LIBRO_RECLAMACIONES(1, "Domicilio consignado en el Libro de Reclamaciones en Salud"),
    CORREO_ELECTRONICO(2, "Correo electrónico"),
    OTRA_DIRECCION(3, "Otra dirección proporcionada por el usuario o tercero legitimado"),
    NOTIFICACION_EN_LIBRO(4, "Notificación expresada por el usuario en el Libro de Reclamaciones"),
    TELEFONO_ACREDITADO(5, "Al teléfono acreditado por el usuario"),
    NO_SE_LOGRA_NOTIFICAR(6, "No se logra notificar debido a imprecisiones en dirección-correo atribuibles al usuario");

    private final int codigo;
    private final String descripcion;

    ComunicacionResultado(int codigo, String descripcion) {
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

    public static ComunicacionResultado fromCodigo(Integer codigo) {
        if (codigo == null) {
            return null;
        }
        return Arrays.stream(values())
                .filter(com -> com.getCodigo() == codigo)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Código de ComunicacionResultado no válido: " + codigo));
    }
}
