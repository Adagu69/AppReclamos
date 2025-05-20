package com.AppReclamos.AppReclamosCms.Controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/dashboard")
    public String mostrarDashboardUsuario(Model model) {
        return "USER/UserDashboard"; // Vista en templates/USER/UserDashboard.html
    }
}
