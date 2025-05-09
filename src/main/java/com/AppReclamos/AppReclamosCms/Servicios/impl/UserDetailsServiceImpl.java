package com.AppReclamos.AppReclamosCms.Servicios.impl;

import com.AppReclamos.AppReclamosCms.Modelos.Usuarios;
import com.AppReclamos.AppReclamosCms.Repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuarios user = usuarioRepositorio.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getContraseña(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRol().getNombre().toUpperCase()))
        );
    }
}
