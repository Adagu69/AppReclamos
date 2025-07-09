package com.AppReclamos.AppReclamosCms.Modelos;

import com.AppReclamos.AppReclamosCms.Modelos.Enums.Parentesco;
import com.AppReclamos.AppReclamosCms.Modelos.Enums.TipoDocumento;
import com.AppReclamos.AppReclamosCms.Modelos.Enums.TipoPersona;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

@Builder
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonaReclamoDTO {

    /* ---------- Identificador (solo en edición / respuesta) ---------- */
    private Integer idPersona;                             // null cuando se crea

    /* ---------- Datos obligatorios ---------- */
    @NotNull
    private TipoPersona tipoPersona;              // PRESENTANTE | USUARIO | TERCERO

    // sólo se usa si tipoPersona == TERCERO
    private Parentesco parentesco;

    @NotNull
    private TipoDocumento tipoDocumento;            // DNI | CE | PASAPORTE | RUC

    @NotBlank
    @Pattern(regexp = "\\d+", message = "Sólo dígitos")
    @Size(min = 1, max = 15, message = "Máximo 15 dígitos")
    private String numeroDocumento;

    /* ---------- Datos opcionales ---------- */
    @Size(max = 100)
    @Pattern(regexp = "[A-Za-zÁÉÍÓÚáéíóúñÑ ]+", message = "Sólo letras y espacios")
    @Size(max = 150, message = "Máximo 150 caracteres")
    private String nombres;

    @Size(max = 50)
    @Pattern(regexp = "[A-Za-zÁÉÍÓÚáéíóúñÑ ]*", message = "Sólo letras y espacios")
    @Size(max = 150, message = "Máximo 150 caracteres")
    private String apellidoPaterno;

    @Size(max = 50)
    @Pattern(regexp = "[A-Za-zÁÉÍÓÚáéíóúñÑ ]*", message = "Sólo letras y espacios")
    @Size(max = 150, message = "Máximo 150 caracteres")
    private String apellidoMaterno;

    @Size(max=150, message="Máximo 150 caracteres")
    private String razonSocial;

    @Size(max = 20)
    private String telefono;

    @Email
    @Size(max = 255)
    private String correoElectronico;

    // NUEVO campo: si se envió el resultado por correo
    @NotNull
    private Boolean     resultadoPorCorreo;

    // NUEVO campo: domicilio
    @Size(max=200)
    private String      domicilio;



}
