package com.AppReclamos.AppReclamosCms.Modelos;


import com.AppReclamos.AppReclamosCms.Modelos.Enums.ResultadoCorreo;
import com.AppReclamos.AppReclamosCms.Modelos.Enums.TipoDocumento;
import com.AppReclamos.AppReclamosCms.Modelos.Enums.TipoPersona;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = "reclamo")
@Entity
@Table(name="PersonaReclamo")
public class PersonaReclamo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_persona")
    @EqualsAndHashCode.Include
    private int idPersona;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_persona", length = 15, nullable = false)
    private TipoPersona tipoPersona;


    @Column(name = "tipo_documento", nullable = false)
    private TipoDocumento tipoDocumento;



    private String numeroDocumento;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String razonSocial;

    @Column(name = "correo_electronico", length = 255)
    private String correoElectronico;

    @Column(name = "resultado_por_correo")
    private ResultadoCorreo resultadoPorCorreo;

    private String telefono;

    @Column(columnDefinition = "TEXT")
    private String domicilio;

}
