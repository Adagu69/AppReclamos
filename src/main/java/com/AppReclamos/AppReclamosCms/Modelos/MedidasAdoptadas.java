package com.AppReclamos.AppReclamosCms.Modelos;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "MedidasAdoptadas")
public class MedidasAdoptadas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMedida;

    private String tipoProcedimiento;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    private String procedemado;
    private LocalDate feciimp;
    private LocalDate fecculpre;

    @ManyToOne
    @JoinColumn(name = "id_reclamo")
    private Reclamos reclamo;
}
