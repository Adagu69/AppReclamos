package com.AppReclamos.AppReclamosCms.Modelos;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

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
    @ToString.Exclude // Evita loop en toString
    @JsonIgnore       // (Si usas JSON alguna vez)
    private List<Usuarios> usuarios;
}
