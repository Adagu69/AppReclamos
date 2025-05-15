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
        System.out.println("Buscando usuario con email: " + email);

        return usuarioRepositorio.findByEmail(email)
                .map(user -> {
                    System.out.println("Usuario encontrado: " + user.getEmail());
                    return new org.springframework.security.core.userdetails.User(
                            user.getEmail(),
                            user.getContraseña(),
                            Collections.singletonList(new SimpleGrantedAuthority(user.getRol().getNombre())) // <- no le añadas "ROLE_"
                    );
                })
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));
    }
}
