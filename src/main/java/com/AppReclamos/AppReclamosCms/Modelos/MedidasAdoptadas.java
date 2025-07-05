package com.AppReclamos.AppReclamosCms.Modelos;

import com.AppReclamos.AppReclamosCms.Modelos.Enums.NaturalezaMedida;
import com.AppReclamos.AppReclamosCms.Modelos.Enums.ProcesoMedida;
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

    @Column(length = 2, nullable = false)
    private String codigoMedida;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private NaturalezaMedida naturaleza;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private ProcesoMedida proceso;

    @Column(nullable = false)
    private LocalDate fechaInicioImplementacion;

    @Column(nullable = false)
    private LocalDate fechaCulminacionPrevista;

    @Column(
            name             = "descripcion_medida",
            columnDefinition = "TEXT",
            nullable         = false
    )
    private String descripcionMedida;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_reclamo", nullable = false)
    private Reclamos reclamo;
}
