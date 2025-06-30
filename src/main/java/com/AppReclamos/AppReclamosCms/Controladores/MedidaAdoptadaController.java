package com.AppReclamos.AppReclamosCms.Controladores;

import com.AppReclamos.AppReclamosCms.Modelos.Enums.NaturalezaMedida;
import com.AppReclamos.AppReclamosCms.Modelos.Enums.ProcesoMedida;
import com.AppReclamos.AppReclamosCms.Modelos.MedidaDTO;
import com.AppReclamos.AppReclamosCms.Modelos.ReclamoDTO;
import com.AppReclamos.AppReclamosCms.Servicios.interfaces.IMedidaAdoptadaServicio;
import com.AppReclamos.AppReclamosCms.Servicios.interfaces.IReclamosServicios;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/medidas")
public class MedidaAdoptadaController {

    @Autowired
    private IMedidaAdoptadaServicio medidaService;

    @Autowired
    private IReclamosServicios reclamoService;

    private static final Logger log = LoggerFactory.getLogger(MedidaAdoptadaController.class);

    private void addEnumsToModel(Model model) {
        model.addAttribute("naturalezasEnum", NaturalezaMedida.values());
        model.addAttribute("procesosEnum", ProcesoMedida.values());
    }

    @GetMapping("/reclamo/{reclamoId}")
    public String verMedidasPorReclamo(@PathVariable Integer reclamoId, Model model) {
        ReclamoDTO reclamo = reclamoService.buscarPorId(reclamoId);
        if (reclamo == null) {
            return "redirect:/admin/reclamos";
        }
        List<MedidaDTO> listaMedidas = medidaService.findByReclamoId(reclamoId);
        MedidaDTO medidaDTO = new MedidaDTO();
        medidaDTO.setReclamoId(reclamoId);

        model.addAttribute("reclamo", reclamo);
        model.addAttribute("listaMedidas", listaMedidas);
        model.addAttribute("medidaDTO", medidaDTO);
        addEnumsToModel(model);
        return "ADMIN/medidas-adoptadas";
    }

    // --- ¡NUEVO ENDPOINT PARA EDITAR! ---
    // Este método devuelve los datos de una medida en formato JSON.
    @GetMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<MedidaDTO> obtenerMedida(@PathVariable("id") Integer id) {
        MedidaDTO medida = medidaService.findById(id);
        if (medida != null) {
            return ResponseEntity.ok(medida); // Devuelve 200 OK con los datos
        }
        return ResponseEntity.notFound().build(); // Devuelve 404 Not Found si no existe
    }

    @PostMapping("/guardar")
    public String guardarMedida(@Valid @ModelAttribute("medidaDTO") MedidaDTO medidaDTO, BindingResult result, RedirectAttributes attributes, Model model) {
        if (medidaDTO.getFechaInicioImplementacion() != null && medidaDTO.getFechaCulminacionPrevista() != null) {
            if (medidaDTO.getFechaCulminacionPrevista().isBefore(medidaDTO.getFechaInicioImplementacion())) {
                result.rejectValue("fechaCulminacionPrevista", "error.medidaDTO", "La fecha de culminación no puede ser anterior a la de inicio.");
            }
        }
        if (result.hasErrors()) {
            log.error("--- ERRORES DE VALIDACIÓN ENCONTRADOS ---");
            for (FieldError error : result.getFieldErrors()) {
                log.error("Campo: '{}', Valor Rechazado: '{}', Mensaje: {}", error.getField(), error.getRejectedValue(), error.getDefaultMessage());
            }
            ReclamoDTO reclamo = reclamoService.buscarPorId(medidaDTO.getReclamoId());
            model.addAttribute("reclamo", reclamo);
            model.addAttribute("listaMedidas", medidaService.findByReclamoId(medidaDTO.getReclamoId()));
            model.addAttribute("showModal", true); // Para reabrir el modal con los errores
            addEnumsToModel(model);
            return "ADMIN/medidas-adoptadas";
        }
        medidaService.save(medidaDTO);
        attributes.addFlashAttribute("msg_success", "Medida guardada correctamente.");
        return "redirect:/admin/medidas/reclamo/" + medidaDTO.getReclamoId();
    }


    @PostMapping("/eliminar/{id}")
    public String eliminarMedida(@PathVariable("id") Integer id, @RequestParam("reclamoId") Integer reclamoId, RedirectAttributes attributes) {
        medidaService.delete(id);
        attributes.addFlashAttribute("msg_success", "Medida eliminada correctamente.");
        return "redirect:/admin/medidas/reclamo/" + reclamoId;
    }
}
