package com.AppReclamos.AppReclamosCms.Modelos;

import com.AppReclamos.AppReclamosCms.Modelos.Enums.TipoDocumento;
import com.AppReclamos.AppReclamosCms.Modelos.Enums.TipoPersona;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
    private Integer id;                             // null cuando se crea

    /* ---------- Datos obligatorios ---------- */
    @NotNull
    private TipoPersona tipoPersona;              // PRESENTANTE | USUARIO | TERCERO

    @NotNull
    private TipoDocumento tipoDocumento;            // DNI | CE | PASAPORTE | RUC

    @NotBlank
    @Size(max = 20)
    private String numeroDocumento;

    /* ---------- Datos opcionales ---------- */
    @Size(max = 100)
    private String nombres;

    @Size(max = 50)
    private String apellidoPaterno;

    @Size(max = 50)
    private String apellidoMaterno;

    @Size(max = 150)
    private String razonSocial;

    @Size(max = 20)
    private String telefono;

    @Email
    @Size(max = 255)
    private String correoElectronico;


}
