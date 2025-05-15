package com.AppReclamos.AppReclamosCms.Modelos;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

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

    private String medioRecepcion;
    private LocalDate fechaRecepcion;

    @Column(columnDefinition = "TEXT")
    private String detalle;

    private String resultado;

    @Column(columnDefinition = "TEXT")
    private String motivoConclusion;

    @Column(columnDefinition = "TEXT")
    private String comunicacionResultado;

    private LocalDate fechaResultado;
    private LocalDate fechaNotificacion;

    @OneToOne(mappedBy = "reclamo", cascade = CascadeType.ALL)
    private PersonaReclamo persona;

    @OneToOne(mappedBy = "reclamo", cascade = CascadeType.ALL)
    private GestionReclamo gestion;

    @OneToOne(mappedBy = "reclamo", cascade = CascadeType.ALL)
    private GestionClinica gestionClinica;

    @OneToMany(mappedBy = "reclamo", cascade = CascadeType.ALL)
    private List<MedidasAdoptadas> medidas;
}
