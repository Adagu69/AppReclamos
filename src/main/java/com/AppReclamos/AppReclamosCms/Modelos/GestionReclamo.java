package com.AppReclamos.AppReclamosCms.Modelos;

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_reclamo", nullable = false, unique = true)
    private Reclamos reclamo;
}
