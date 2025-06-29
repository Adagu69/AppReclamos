package com.AppReclamos.AppReclamosCms.Modelos;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "MedidasAdoptadas")
public class MedidasAdoptadas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private LocalDate fechaMedida;

    @Column(length = 255, nullable = false)
    private String responsableEjecucion;

    @Column(nullable = false)
    private LocalDate fechaEjecucion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_reclamo", nullable = false)
    private Reclamos reclamo;
}
