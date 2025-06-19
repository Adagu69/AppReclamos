package com.AppReclamos.AppReclamosCms.Controladores;

import com.AppReclamos.AppReclamosCms.Modelos.ReclamoDTO;
import com.AppReclamos.AppReclamosCms.Modelos.ReclamoTablaDTO;
import com.AppReclamos.AppReclamosCms.Servicios.interfaces.IReclamosServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/reclamos")
public class ReclamosController {

    @Autowired
    private IReclamosServicios reclamosServicios;

    // Muestra la lista de reclamos y el formulario de creación
    @GetMapping
    public String listarReclamos(@RequestParam(value = "estado", required = false, defaultValue = "TODOS") String estado,
                                 @RequestParam(value = "buscarPor", required = false, defaultValue = "TODO") String buscarPor,
                                 @RequestParam(value = "query", required = false, defaultValue = "") String query,
                                 @RequestParam(value = "anio", required = false) Integer anio,
                                 @RequestParam(value = "mes", required = false) Integer mes,
                                 Model model) {

        List<ReclamoTablaDTO> reclamos = reclamosServicios.buscarFiltrado(estado, buscarPor, query, anio, mes);
        model.addAttribute("reclamos", reclamos);


        // Para repoblar selects de filtro si hace falta
        model.addAttribute("estado", estado);
        model.addAttribute("buscarPor", buscarPor);
        model.addAttribute("query", query);
        model.addAttribute("anio", anio);
        model.addAttribute("mes", mes);

        List<ReclamoDTO> lista = reclamosServicios.listarTodosDTO();
        model.addAttribute("reclamos", lista);
        model.addAttribute("reclamo", new ReclamoDTO());
        model.addAttribute("reclamos", reclamosServicios.listarTodosParaTabla());
        return "ADMIN/reclamos";
    }

    // Carga el formulario para editar un reclamo (modal)
    @GetMapping("/editar/{id}")
    public String editarReclamo(@PathVariable Integer id, Model model) {
        ReclamoDTO reclamo = reclamosServicios.buscarDTOporId(id);
        model.addAttribute("reclamos", reclamosServicios.listarTodosDTO());
        model.addAttribute("reclamo", reclamo);
        return "ADMIN/reclamos";
    }

    // Guarda/actualiza reclamo (desde el modal/formulario)
    @PostMapping("/guardar")
    public String guardarReclamo(@ModelAttribute("reclamo") ReclamoDTO reclamo, RedirectAttributes redirect) {
        reclamosServicios.guardarDesdeDTO(reclamo);
        redirect.addFlashAttribute("success", "Reclamo guardado correctamente.");
        return "redirect:/admin/reclamos";
    }

    // Si quieres editar, puedes devolver el reclamo como JSON para llenar el modal por AJAX
    @GetMapping("/buscar/{id}")
    @ResponseBody
    public ReclamoDTO buscarReclamo(@PathVariable Integer id) {
        return reclamosServicios.buscarDTOporId(id);
    }
}
