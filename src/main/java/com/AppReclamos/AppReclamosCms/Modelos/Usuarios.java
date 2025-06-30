package com.AppReclamos.AppReclamosCms.Modelos;


import jakarta.persistence.*;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    @Column(name="correo",nullable = false, length = 255)
    private String email;

    @Column(nullable = false )
    private String contraseña;

    @ManyToOne
    @JoinColumn(name = "id_rol", nullable = false)
    private Roles rol;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;

    @Column(name = "activos",nullable = false)
    private boolean activo = true;

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public LocalDate getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDate fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

}
