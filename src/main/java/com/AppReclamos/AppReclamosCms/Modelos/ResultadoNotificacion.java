package com.AppReclamos.AppReclamosCms.Modelos;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@ToString(exclude = "reclamo")
@Entity
@Table(name = "ResultadoNotificacion")
public class ResultadoNotificacion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50, nullable = false)
    private String resultado;

    @Column(length = 255)
    private String motivoConclusionAnticipada;

    @Column(length = 255)
    private String comunicacionResultado;

    private LocalDate fechaResultado;
    private LocalDate fechaNotificacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_reclamo")
    private Reclamos reclamo;

    @PrePersist
    private void prePersist() {
        if (fechaNotificacion == null) {
            fechaNotificacion = LocalDate.now();
        }
    }
}
