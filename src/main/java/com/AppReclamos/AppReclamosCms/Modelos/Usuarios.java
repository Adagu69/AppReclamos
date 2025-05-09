package com.AppReclamos.AppReclamosCms.Modelos;


import jakarta.persistence.*;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@Entity
@Table(name="Usuarios")
public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUsuario;

    @Column(name="Nombre",nullable = false, length = 100)
    private String nombre;

    @NotNull
    @Column(name="Correo",nullable = false, length = 255)
    private String email;

    @Column(nullable = false, length = 255)
    private String contraseña;

    @ManyToOne
    @JoinColumn(name = "idRol", nullable = false)
    private Roles rol;
}
