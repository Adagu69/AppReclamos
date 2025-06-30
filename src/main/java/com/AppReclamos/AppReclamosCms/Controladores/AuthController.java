package com.AppReclamos.AppReclamosCms.Controladores;

import com.AppReclamos.AppReclamosCms.Modelos.Usuarios;
import com.AppReclamos.AppReclamosCms.Servicios.impl.UsuarioServicioImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }
}
