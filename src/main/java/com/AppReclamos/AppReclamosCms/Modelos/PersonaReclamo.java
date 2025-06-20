package com.AppReclamos.AppReclamosCms.Modelos;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name="PersonaReclamo")
public class PersonaReclamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPersona;
    private String tipoPersona; // "PRESENTANTE", "USUARIO", "TERCERO"
    private String tipoDocumento;
    private String numeroDocumento;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String razonSocial;
    private String correoElectronico;
    private String telefono;

    @Column(columnDefinition = "TEXT")
    private String domicilio;

    @ManyToOne
    @JoinColumn(name = "id_reclamo")
    private Reclamos reclamo;

}
