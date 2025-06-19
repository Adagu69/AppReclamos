package com.AppReclamos.AppReclamosCms.Modelos;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "DetalleReclamo")
public class DetalleReclamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String medioRecepcion;
    private LocalDate fechaRecepcion;
    @Column(columnDefinition = "TEXT")
    private String detalle;
    private LocalDateTime fechaCreacion;

    @ManyToOne
    @JoinColumn(name = "id_reclamo")
    private Reclamos reclamo;
}
