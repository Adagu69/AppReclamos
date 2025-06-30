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
@Table(name = "GestionClinica")
public class GestionClinica implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_gestion_clinica")
    @EqualsAndHashCode.Include
    private Integer idGestionClinica;

    private String compania;
    private String area;
    private String origen;
    private String parentesco;
    private String personalInvolucrado;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_reclamo", nullable = false, unique = true)
    private Reclamos reclamo;
}