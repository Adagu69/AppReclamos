package com.AppReclamos.AppReclamosCms.Modelos;


import com.AppReclamos.AppReclamosCms.Modelos.Enums.MedioRecepcion;
import com.AppReclamos.AppReclamosCms.Modelos.Enums.TipoDeclarante;
import com.AppReclamos.AppReclamosCms.Modelos.Enums.TipoInstitucion;
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

    @Column(name = "periodo_declaracion", nullable = false, length = 6)
    private String periodoDeclaracion;

    private TipoDeclarante tipoDeclarante;
    private String codigoDeclarante;
    private TipoInstitucion tipoInstitucion;
    private String codigoInstitucion;
    private String codigoUgipress;
    private String codigoReclamo;
    private String estadoReclamo;

    // ¡CAMBIO CLAVE! Reemplazamos el Set por dos relaciones One-to-One
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_presentante") // Crea una nueva columna FK en la tabla reclamos
    private PersonaReclamo presentante;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_afectado") // Crea otra nueva columna FK
    private PersonaReclamo afectado;

    private MedioRecepcion medioRecepcion;


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



}
