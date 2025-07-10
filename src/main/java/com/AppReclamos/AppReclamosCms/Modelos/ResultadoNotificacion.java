package com.AppReclamos.AppReclamosCms.Modelos;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

    @Column(name = "fecha_resultado", length = 8, nullable = false)
    private String fechaResultado; // Formato AAAAMMDD

    @Column(name = "fecha_notificacion", length = 8, nullable = false)
    private String fechaNotificacion; // Formato AAAAMMDD

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_reclamo")
    private Reclamos reclamo;

    @PrePersist
    private void prePersist() {
        // Si la fecha de notificación no se ha establecido, se pone la actual en el formato correcto.
        if (this.fechaNotificacion == null || this.fechaNotificacion.isBlank()) {
            this.fechaNotificacion = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        }
    }
}
