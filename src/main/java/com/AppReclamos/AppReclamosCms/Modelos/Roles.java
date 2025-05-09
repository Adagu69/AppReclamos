package com.AppReclamos.AppReclamosCms.Modelos;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name="roles")
public class Roles {

    @Id
    @Column(name = "Id_Rol")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRol;

    @Column(name = "Nombre")
    private String nombre;

    @OneToMany(mappedBy = "rol", cascade = CascadeType.ALL)
    private List<Usuarios> usuarios;
}
