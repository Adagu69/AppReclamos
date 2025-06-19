package com.AppReclamos.AppReclamosCms.Modelos;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "ResultadoNotificacion")
public class ResultadoNotificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String resultado;
    private String motivoConclusionAnticipada;
    private String comunicacionResultado;
    private LocalDate fechaResultado;
    private LocalDate fechaNotificacion;

    @ManyToOne
    @JoinColumn(name = "id_reclamo")
    private Reclamos reclamo;
}
