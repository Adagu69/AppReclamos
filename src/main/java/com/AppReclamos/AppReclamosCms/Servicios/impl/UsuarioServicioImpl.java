package com.AppReclamos.AppReclamosCms.Servicios.impl;

import com.AppReclamos.AppReclamosCms.Modelos.Usuarios;
import com.AppReclamos.AppReclamosCms.Repositorios.UsuarioRepositorio;
import com.AppReclamos.AppReclamosCms.Servicios.interfaces.IUsuarioServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServicioImpl implements IUsuarioServicios {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;



    @Override
    public UserDetails loadUserByUsername(String email) {
        return null;
    }

    @Override
    public Usuarios crearUsuario(Usuarios user) {
        return null;
    }

    @Override
    public Optional<Usuarios> buscarPorEmail(String email) {
        return Optional.empty();
    }

    @Override
    public List<Usuarios> listarTodos() {
        return usuarioRepositorio.findAll();
    }

    @Override
    public Usuarios guardar(Usuarios usuario) {
        return usuarioRepositorio.save(usuario);
    }

    @Override
    public Usuarios obtenerPorId(Integer id) {
        return usuarioRepositorio.findById(id).orElse(null);
    }

    @Override
    public void eliminar(Integer id) {
        Usuarios u = obtenerPorId(id);
        if (u != null) {
            u.setActivo(false);
            guardar(u);
        }

    }

    @Override
    public List<Usuarios> listarActivos() {
        return usuarioRepositorio.findByActivoTrue();
    }
}
