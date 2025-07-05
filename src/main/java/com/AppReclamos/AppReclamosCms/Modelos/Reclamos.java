package com.AppReclamos.AppReclamosCms.Modelos;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"personas", "gestion",
        "gestionClinica",
        "medidas",
        "resultados",
        "detalles"})
@Entity
@Table(name="Reclamos")
public class Reclamos implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reclamo")
    @EqualsAndHashCode.Include
    private int idReclamo;

    private LocalDate fechaReclamo;
    private String tipoDeclarante;
    private String codigoDeclarante;
    private String tipoInstitucion;
    private String codigoInstitucion;
    private String codigoUgipress;
    private String codigoReclamo;
    private String estadoReclamo;

    @OneToMany(
            mappedBy = "reclamo",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    // ANTES: private List<PersonaReclamo> personas = new ArrayList<>();
    private Set<PersonaReclamo> personas = new HashSet<>();


    @OneToOne(mappedBy = "reclamo",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            optional = false)
    private GestionReclamo gestion;

    @OneToOne(mappedBy = "reclamo",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            optional = false)
    private GestionClinica gestionClinica;

    @OneToMany(mappedBy = "reclamo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // ANTES: private List<MedidasAdoptadas> medidas = new ArrayList<>();
    private Set<MedidasAdoptadas> medidas = new HashSet<>();

    @OneToMany(mappedBy = "reclamo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    // ANTES: private List<ResultadoNotificacion> resultados = new ArrayList<>();
    private Set<ResultadoNotificacion> resultados = new HashSet<>();

    @OneToMany(mappedBy = "reclamo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // ANTES: private List<DetalleReclamo> detalles = new ArrayList<>();
    private Set<DetalleReclamo> detalles = new HashSet<>();

    public void addPersona(PersonaReclamo p) {
        personas.add(p);
        p.setReclamo(this);
    }

    public void removePersona(PersonaReclamo p) {
        personas.remove(p);
        p.setReclamo(null);
    }

    public void addResultado(ResultadoNotificacion r) {
        resultados.add(r);
        r.setReclamo(this);
    }

    public void removeResultado(ResultadoNotificacion r) {
        resultados.remove(r);
        r.setReclamo(null);
    }
}
