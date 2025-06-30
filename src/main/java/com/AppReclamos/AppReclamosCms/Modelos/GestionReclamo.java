package com.AppReclamos.AppReclamosCms.Modelos;

import com.AppReclamos.AppReclamosCms.Modelos.Enums.GestionCompetencia;
import com.AppReclamos.AppReclamosCms.Modelos.Enums.GestionEtapa;
import com.AppReclamos.AppReclamosCms.Modelos.Enums.GestionServicios;
import com.AppReclamos.AppReclamosCms.Modelos.Enums.TipoDeclarante;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = "reclamo")
@Entity
@Table(name = "GestionReclamo")
public class GestionReclamo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_gestion")
    @EqualsAndHashCode.Include
    private Integer idGestion;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false,length=20)
    private GestionServicios servicio;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false,length=12)
    private GestionCompetencia competencia;

    private String clasificacion1;
    private String clasificacion2;
    private String clasificacion3;

    private String estadoReclamo;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false,length=20)
    private GestionEtapa etapaReclamo;

    @Column(length=15)
    private String codigoPrimigenio;

    @Enumerated(EnumType.STRING)
    @Column(nullable=true,length=20)
    private TipoDeclarante tipoAdministraTraslado;

    @Column(length = 8, nullable = true)
    private String codigoAdministraTraslado;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_reclamo", nullable = false, unique = true)
    private Reclamos reclamo;
}
