package com.AppReclamos.AppReclamosCms.Controladores;

import com.AppReclamos.AppReclamosCms.Modelos.*;
import com.AppReclamos.AppReclamosCms.Modelos.Enums.*;
import com.AppReclamos.AppReclamosCms.Servicios.interfaces.IReclamosServicios;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/reclamos")
@RequiredArgsConstructor
public class ReclamosController {

    @Autowired
    private IReclamosServicios reclamosSvc;

    /* ╔════════════════════════════════════════╗
       ║ 1. LISTA + FILTRO (reclamos.html)      ║
       ╚════════════════════════════════════════╝ */
    @GetMapping
    public String listar(
            @RequestParam(defaultValue = "TODOS") String estado,
            @RequestParam(defaultValue = "TODO")  String buscarPor,
            @RequestParam(defaultValue = "")      String query,
            @RequestParam(required = false)       Integer anio,
            @RequestParam(required = false)       Integer mes,
            Model model) {

        List<ReclamoTablaDTO> reclamos = reclamosSvc.buscarFiltrado(estado, buscarPor, query, anio, mes);

        model.addAttribute("reclamos",   reclamos);   // para la tabla
        model.addAttribute("estado",     estado);     // mantiene filtros
        model.addAttribute("buscarPor",  buscarPor);
        model.addAttribute("query",      query);
        model.addAttribute("anio",       anio);
        model.addAttribute("mes",        mes);

        // lista estática de estados (para el <select>)
        model.addAttribute("estadosEnum", EstadoReclamo.values());

        return "ADMIN/reclamos";          // ⇢ reclamos.html
    }

    /* ╔════════════════════════════════════════╗
           ║ 2. NUEVO RECLAMO (formulario)          ║
           ╚════════════════════════════════════════╝ */
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        ReclamoDTO dto = new ReclamoDTO();

        // inicializar lista mínima
        dto.getPersonas().add(new PersonaReclamoDTO());    // 0 = presentante
        dto.getPersonas().add(new PersonaReclamoDTO());    // 1 = usuario/tercero (si lo usas)
        dto.getDetalles().add(new DetalleReclamoDTO());    // 0 = detalle principal

        // **¡¡ importante inicializar gestión !!**
        dto.setGestion(new GestionReclamoDTO());

        model.addAttribute("reclamo", dto);
        model.addAttribute("title",   "Nuevo Reclamo");
        model.addAttribute("estadosEnum", EstadoReclamo.values());
        return "ADMIN/reclamos-form";
    }

    /* ╔════════════════════════════════════════╗
           ║ 3. EDITAR RECLAMO (formulario)         ║
           ╚════════════════════════════════════════╝ */
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id,
                         Model model,
                         RedirectAttributes redir) {
        ReclamoDTO dto = reclamosSvc.buscarDTOporId(id);
        if (dto == null) {
            redir.addFlashAttribute("error", "El reclamo no existe.");
            return "redirect:/admin/reclamos";
        }

        // Asegurar 2 personas en la lista
        if (dto.getPersonas() == null) {
            dto.setPersonas(new ArrayList<>());
        }
        while (dto.getPersonas().size() < 2) {
            dto.getPersonas().add(new PersonaReclamoDTO());
        }

        // Asegurar al menos un detalle
        if (dto.getDetalles() == null) {
            dto.setDetalles(new ArrayList<>());
        }
        if (dto.getDetalles().isEmpty()) {
            dto.getDetalles().add(new DetalleReclamoDTO());
        }

        // **¡¡ importante inicializar gestión si es nulo !!**
        if (dto.getGestion() == null) {
            dto.setGestion(new GestionReclamoDTO());
        }

        model.addAttribute("title", "Editar Reclamo");
        model.addAttribute("reclamo", dto);
        model.addAttribute("estadosEnum", EstadoReclamo.values());
        return "ADMIN/reclamos-form";
    }

    /* ╔════════════════════════════════════════╗
         ║ 4. GUARDAR / ACTUALIZAR                ║
         ╚════════════════════════════════════════╝ */
    @PostMapping("/guardar")
    public String save(@Valid @ModelAttribute("reclamo") ReclamoDTO reclamo,
                       BindingResult br,
                       RedirectAttributes redir) {
        if (br.hasErrors()) {
            return "ADMIN/reclamos-form";
        }
        reclamosSvc.guardarDesdeDTO(reclamo);
        redir.addFlashAttribute("success", "Reclamo guardado correctamente.");
        return "redirect:/admin/reclamos";
    }

    /* ╔════════════════════════════════════════╗
     ║ 5. ENDPOINT JSON (AJAX opcional)       ║
     ╚════════════════════════════════════════╝ */
    @GetMapping("/api/{id}")
    @ResponseBody
    public ReclamoDTO detalleAjax(@PathVariable Integer id) {
        return reclamosSvc.buscarDTOporId(id);
    }

}
