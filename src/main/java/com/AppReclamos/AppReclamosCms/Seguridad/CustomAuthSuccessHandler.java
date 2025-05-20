package com.AppReclamos.AppReclamosCms.Seguridad;

import com.AppReclamos.AppReclamosCms.Modelos.Usuarios;
import com.AppReclamos.AppReclamosCms.Repositorios.UsuarioRepositorio;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Optional;

@Component
public class CustomAuthSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        String email = authentication.getName();
        Optional<Usuarios> optUser = usuarioRepositorio.findByEmail(email.toLowerCase());

        if (optUser.isPresent()) {
            String rol = optUser.get().getRol().getNombre().toUpperCase();
            if (rol.contains("ADMIN")) {
                response.sendRedirect("/admin/usuarios");
            } else {
                response.sendRedirect("/user/dashboard");
            }
        } else {
            response.sendRedirect("/login?error");
        }
    }
}
