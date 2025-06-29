package com.AppReclamos.AppReclamosCms.Controladores;

import com.AppReclamos.AppReclamosCms.Modelos.*;
import com.AppReclamos.AppReclamosCms.Modelos.Enums.*;
import com.AppReclamos.AppReclamosCms.Servicios.interfaces.IReclamosServicios;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/reclamos")
@RequiredArgsConstructor
public class ReclamosController {

    private final IReclamosServicios reclamosSvc;

    private static final Logger log = LoggerFactory.getLogger(ReclamosController.class);

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
        model.addAttribute("reclamos", reclamos);
        model.addAttribute("estado", estado);
        model.addAttribute("buscarPor", buscarPor);
        model.addAttribute("query", query);
        model.addAttribute("anio", anio);
        model.addAttribute("mes", mes);
        model.addAttribute("estadosEnum", EstadoReclamo.values());
        return "ADMIN/reclamos";
    }


    /* ╔════════════════════════════════════════╗
           ║ 2. NUEVO RECLAMO (formulario)          ║
           ╚════════════════════════════════════════╝ */
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        ReclamoDTO dto = new ReclamoDTO();
        dto.getPersonas().add(new PersonaReclamoDTO());
        dto.getPersonas().add(new PersonaReclamoDTO());
        dto.getDetalles().add(new DetalleReclamoDTO());
        dto.setGestion(new GestionReclamoDTO());
        dto.getResultados().add(new ResultadoNotificacionDTO());

        model.addAttribute("reclamo", dto);
        model.addAttribute("title", "Nuevo Reclamo");
        // CORRECCIÓN: Pasar todos los enums necesarios para el formulario
        model.addAttribute("estadosEnum", EstadoReclamo.values());
        model.addAttribute("etapasEnum", GestionEtapa.values());
        return "ADMIN/reclamos-form";
    }

    /* ╔════════════════════════════════════════╗
           ║ 3. EDITAR RECLAMO (formulario)         ║
           ╚════════════════════════════════════════╝ */
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model, RedirectAttributes redir) {
        // Correcto: usa el método unificado 'buscarPorId'
        ReclamoDTO dto = reclamosSvc.buscarPorId(id);
        if (dto == null) {
            redir.addFlashAttribute("error", "El reclamo no existe.");
            return "redirect:/admin/reclamos";
        }

        // Lógica para asegurar listas mínimas (está bien)
        if (dto.getPersonas() == null) dto.setPersonas(new ArrayList<>());
        while (dto.getPersonas().size() < 2) dto.getPersonas().add(new PersonaReclamoDTO());

        if (dto.getDetalles() == null || dto.getDetalles().isEmpty()) {
            dto.setDetalles(new ArrayList<>());
            dto.getDetalles().add(new DetalleReclamoDTO());
        }

        if (dto.getGestion() == null) dto.setGestion(new GestionReclamoDTO());

        // resultado
        if (dto.getResultados() == null) dto.setResultados(new ArrayList<>());
        if (dto.getResultados().isEmpty())
            dto.getResultados().add(new ResultadoNotificacionDTO());

        model.addAttribute("title", "Editar Reclamo");
        model.addAttribute("reclamo", dto);
        // CORRECCIÓN: Pasar todos los enums necesarios para el formulario
        model.addAttribute("estadosEnum", EstadoReclamo.values());
        model.addAttribute("etapasEnum", GestionEtapa.values());
        return "ADMIN/reclamos-form";
    }

    /* ╔════════════════════════════════════════╗
         ║ 4. GUARDAR / ACTUALIZAR                ║
         ╚════════════════════════════════════════╝ */
    @PostMapping("/guardar")
    public String save(@Valid @ModelAttribute("reclamo") ReclamoDTO reclamo,
                       BindingResult br,
                       Model model,
                       RedirectAttributes redir) {

        // Log de depuración
        if (reclamo.getGestion() != null) {
            log.info("---[CONTROLADOR] Valor de 'etapaReclamo' recibido del formulario: {}", reclamo.getGestion().getEtapaReclamo());
        } else {
            log.warn("---[CONTROLADOR] El objeto 'gestion' en el DTO es NULO.");
        }

        if (br.hasErrors()) {
            // CORRECCIÓN: Devolvemos los enums para que la vista no se rompa al recargar
            model.addAttribute("estadosEnum", EstadoReclamo.values());
            model.addAttribute("etapasEnum", GestionEtapa.values());
            return "ADMIN/reclamos-form";
        }

        ReclamoDTO reclamoGuardado = reclamosSvc.guardarDesdeDTO(reclamo);
        redir.addFlashAttribute("success", "Paso 1: Datos guardados. Ahora, añade las medidas adoptadas.");
        return "redirect:/admin/medidas/reclamo/" + reclamoGuardado.getIdReclamo();
    }

    /* ╔════════════════════════════════════════╗
     ║ 5. ENDPOINT JSON (AJAX opcional)       ║
     ╚════════════════════════════════════════╝ */
    @GetMapping("/api/{id}")
    @ResponseBody
    public ReclamoDTO detalleAjax(@PathVariable Integer id) {
        // Correcto: usa el método unificado 'buscarPorId'
        return reclamosSvc.buscarPorId(id);
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarReclamo(@PathVariable Integer id, RedirectAttributes attributes) {
        try {
            // Correcto: usa el método unificado 'eliminarPorId'
            reclamosSvc.eliminarPorId(id);
            attributes.addFlashAttribute("msg_success", "Reclamo eliminado correctamente");
        } catch (Exception e) {
            attributes.addFlashAttribute("msg_error", "Error al eliminar el reclamo");
        }
        return "redirect:/admin/reclamos";
    }


}
