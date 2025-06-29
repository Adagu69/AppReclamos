package com.AppReclamos.AppReclamosCms.Controladores;

import com.AppReclamos.AppReclamosCms.Modelos.MedidaDTO;
import com.AppReclamos.AppReclamosCms.Modelos.ReclamoDTO;
import com.AppReclamos.AppReclamosCms.Servicios.interfaces.IMedidaAdoptadaServicio;
import com.AppReclamos.AppReclamosCms.Servicios.interfaces.IReclamosServicios;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    @GetMapping("/reclamo/{reclamoId}")
    public String verMedidasPorReclamo(@PathVariable("reclamoId") Integer reclamoId, Model model) {
        // CORRECCIÓN: Usar el nuevo nombre del método 'buscarPorId'
        ReclamoDTO reclamo = reclamoService.buscarPorId(reclamoId);

        if (reclamo == null) {
            return "redirect:/admin/reclamos";
        }
        List<MedidaDTO> listaMedidas = medidaService.findByReclamoId(reclamoId);
        model.addAttribute("reclamo", reclamo);
        model.addAttribute("listaMedidas", listaMedidas);
        model.addAttribute("medidaDTO", new MedidaDTO());
        return "ADMIN/medidas-adoptadas";
    }

    @PostMapping("/guardar")
    public String guardarMedida(@Valid @ModelAttribute("medidaDTO") MedidaDTO medidaDTO,
                                BindingResult result, RedirectAttributes attributes, Model model) {

        if (result.hasErrors()) {
            // CORRECCIÓN: Usar el nuevo nombre del método 'buscarPorId'
            ReclamoDTO reclamo = reclamoService.buscarPorId(medidaDTO.getReclamoId());

            List<MedidaDTO> listaMedidas = medidaService.findByReclamoId(medidaDTO.getReclamoId());
            model.addAttribute("reclamo", reclamo);
            model.addAttribute("listaMedidas", listaMedidas);
            model.addAttribute("showModal", true);
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
