package com.AppReclamos.AppReclamosCms.Modelos;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="Reclamos")
public class Reclamos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idReclamo;

    private LocalDate fechaReclamo;
    private String tipoDeclarante;
    private String codigoDeclarante;
    private String tipoInstitucion;
    private String codigoInstitucion;
    private String codigoUgipress;
    private String codigoReclamo;
    private String estadoReclamo;

    @OneToMany(mappedBy = "reclamo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PersonaReclamo> personas = new ArrayList<>();


    @OneToOne(mappedBy = "reclamo", cascade = CascadeType.ALL)
    private GestionReclamo gestion;

    @OneToOne(mappedBy = "reclamo", cascade = CascadeType.ALL)
    private GestionClinica gestionClinica;

    @OneToMany(mappedBy = "reclamo", cascade = CascadeType.ALL)
    private List<MedidasAdoptadas> medidas;

    @OneToMany(mappedBy = "reclamo", cascade = CascadeType.ALL)
    private List<ResultadoNotificacion> resultados;

    @OneToMany(mappedBy = "reclamo", cascade = CascadeType.ALL)
    private List<DetalleReclamo> detalles;
}
