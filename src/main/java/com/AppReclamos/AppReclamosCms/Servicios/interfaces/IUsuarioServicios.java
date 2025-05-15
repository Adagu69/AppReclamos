package com.AppReclamos.AppReclamosCms.Servicios.interfaces;

import com.AppReclamos.AppReclamosCms.Modelos.Usuarios;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface IUsuarioServicios {

    UserDetails loadUserByUsername(String email);
    Usuarios crearUsuario(Usuarios user);
    Optional<Usuarios> buscarPorEmail(String email);

    List<Usuarios> listarTodos();
    Usuarios guardar(Usuarios usuario);
    Usuarios obtenerPorId(Integer id);
    void eliminar(Integer id);

    List<Usuarios> listarActivos();
}
