package com.AppReclamos.AppReclamosCms.Servicios.interfaces;

import com.AppReclamos.AppReclamosCms.Modelos.Usuarios;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface IUsuarioServicios {

    UserDetails loadUserByUsername(String email);
    Usuarios crearUsuario(Usuarios user);
    Optional<Usuarios> buscarPorEmail(String email);
}
