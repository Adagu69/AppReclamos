package com.AppReclamos.AppReclamosCms.Modelos;

import jakarta.persistence.*;

@Entity
@Table(name = "GestionReclamo")
public class GestionReclamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idGestion;

    @Column(columnDefinition = "TEXT")
    private String servicio;

    private String competencia;
    private String clasificacion1;
    private String clasificacion2;
    private String clasificacion3;

    private String estadoReclamo;
    private String etapaReclamo;
    private String codigoPrimigenio;
    private String tipoAdministraTraslado;
    private String codigoAdministraTraslado;

    @OneToOne
    @JoinColumn(name = "id_reclamo")
    private Reclamos reclamo;
}
