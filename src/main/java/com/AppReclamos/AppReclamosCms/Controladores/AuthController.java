package com.AppReclamos.AppReclamosCms.Controladores;

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

    @GetMapping("/home")
    public String home(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        model.addAttribute("username", principal.getName());
        return "home";
    }

    @GetMapping("/admin/panel")
    public String adminPanel(){
        return "admin-panel";
    }

    @GetMapping("/user/dashboard")
    public String userDashboard(){
        return "user-dashboard";
    }
}
