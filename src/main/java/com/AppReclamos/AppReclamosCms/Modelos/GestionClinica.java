package com.AppReclamos.AppReclamosCms.Modelos;


import jakarta.persistence.*;

@Entity
@Table(name = "GestionClinica")
public class GestionClinica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idGestionClinica;

    private String compania;
    private String area;
    private String origen;
    private String parentesco;
    private String personalInvolucrado;

    @OneToOne
    @JoinColumn(name = "id_reclamo")
    private Reclamos reclamo;
}
