package com.AppReclamos.AppReclamosCms.Controladores;


import com.AppReclamos.AppReclamosCms.Modelos.Usuarios;
import com.AppReclamos.AppReclamosCms.Servicios.interfaces.IRolServicio;
import com.AppReclamos.AppReclamosCms.Servicios.interfaces.IUsuarioServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/admin/usuarios")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminUsuarioController {

    @Autowired
    private IUsuarioServicios usuarioServicios;

    @Autowired
    private IRolServicio rolServicio;

    @GetMapping
    public String listarUsuarios(Model model){
        model.addAttribute("usuarios", usuarioServicios.listarTodos());
        return "ADMIN/usuarios";
    }


    @GetMapping("/desactivar/{id}")
    public String desactivar(@PathVariable Integer id) {
        Usuarios u = usuarioServicios.obtenerPorId(id);
        u.setActivo(false);
        usuarioServicios.guardar(u);
        return "redirect:/admin/usuarios";
    }

    @GetMapping("/reactivar/{id}")
    public String reactivar(@PathVariable Integer id) {
        Usuarios u = usuarioServicios.obtenerPorId(id);
        u.setActivo(true);
        usuarioServicios.guardar(u);
        return "redirect:/admin/usuarios";
    }

    @GetMapping("/nuevo")
    public String nuevoUsuarioForm(Model modelo) {
        modelo.addAttribute("usuario", new Usuarios());
        modelo.addAttribute("roles", rolServicio.listarRoles());
        return "ADMIN/usuario-form";
    }

    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute("usuario") Usuarios usuario,
                                 @RequestParam("nuevaContraseña") String nuevaContraseña) {
        if (usuario.getIdUsuario() == 0) {
            usuario.setActivo(true);
            if (nuevaContraseña == null || nuevaContraseña.isBlank()) {
                // Generar una contraseña temporal
                nuevaContraseña = "123456"; // o lanza error si prefieres forzarla
            }
            usuario.setContraseña(new BCryptPasswordEncoder().encode(nuevaContraseña));
        } else {
            Usuarios existente = usuarioServicios.obtenerPorId(usuario.getIdUsuario());
            if (nuevaContraseña == null || nuevaContraseña.isBlank()) {
                usuario.setContraseña(existente.getContraseña());
            } else {
                usuario.setContraseña(new BCryptPasswordEncoder().encode(nuevaContraseña));
            }
        }

        // Validar fecha si no viene del formulario
        if (usuario.getFechaRegistro() == null) {
            usuario.setFechaRegistro(LocalDate.now());
        }

        usuarioServicios.guardar(usuario);
        return "redirect:/admin/usuarios";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model modelo) {
        modelo.addAttribute("usuario", usuarioServicios.obtenerPorId(id));
        modelo.addAttribute("roles", rolServicio.listarRoles());
        return "ADMIN/usuario-form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        Usuarios usuario = usuarioServicios.obtenerPorId(id);
        usuario.setActivo(false);
        usuarioServicios.guardar(usuario); // actualiza el estado a desactivado
        return "redirect:/admin/usuarios";
    }

    @GetMapping("/admin/dashboard")
    public String mostrarDashboardAdmin(Model model) {
        return "ADMIN/dashboard"; // Templates path
    }

    @GetMapping("/user/UserDashboard")
    public String mostrarDashboardUsuario(Model model) {
        return "USER/UserDashboard";
    }


}
