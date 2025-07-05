package com.AppReclamos.AppReclamosCms.Controladores;

import com.AppReclamos.AppReclamosCms.Modelos.*;
import com.AppReclamos.AppReclamosCms.Modelos.Enums.*;
import com.AppReclamos.AppReclamosCms.Servicios.interfaces.IReclamosServicios;
import com.AppReclamos.AppReclamosCms.Utils.ExcelGenerator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/admin/reclamos")
@RequiredArgsConstructor
public class ReclamosController {

    private final IReclamosServicios reclamosSvc;

    private void addEnumsToModel(Model model) {
        model.addAttribute("estadosEnum", EstadoReclamo.values());
        model.addAttribute("etapasEnum", GestionEtapa.values());
        model.addAttribute("tiposDeclaranteEnum", TipoDeclarante.values());
        // ... (otros enums si los hubiera)
    }

    // --- (Los métodos GET como listar, nuevo, editar se mantienen igual) ---
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
        addEnumsToModel(model);
        return "ADMIN/reclamos";
    }

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
        addEnumsToModel(model);
        return "ADMIN/reclamos-form";
    }


    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model, RedirectAttributes redir) {
        ReclamoDTO dto = reclamosSvc.buscarPorId(id);
        if (dto == null) {
            redir.addFlashAttribute("error", "El reclamo no existe.");
            return "redirect:/admin/reclamos";
        }
        // ... (Lógica para asegurar listas mínimas) ...
        model.addAttribute("reclamo", dto);
        model.addAttribute("title", "Editar Reclamo");
        addEnumsToModel(model);
        return "ADMIN/reclamos-form";
    }

    // --- MÉTODO SAVE (VERSIÓN FINAL Y CORRECTA) ---
    @PostMapping("/guardar")
    public String save(@Valid @ModelAttribute("reclamo") ReclamoDTO reclamo,
                       BindingResult br,
                       Model model,
                       RedirectAttributes redir) {

        // --- MANEJO DE ERRORES DE VALIDACIÓN (ROBUSTO) ---
        if (br.hasErrors()) {
            // 1. Añadimos de nuevo al modelo todos los datos que la vista necesita
            //    para renderizarse (como los enums para los dropdowns).
            addEnumsToModel(model);

            // 2. Añadimos un título para que la cabecera de la página se vea bien.
            model.addAttribute("title", reclamo.getIdReclamo() != null ? "Editar Reclamo" : "Nuevo Reclamo");

            // 3. ¡IMPORTANTE! Extraemos los errores GLOBALES (los que no son de un campo específico)
            //    y los añadimos al modelo para poder mostrarlos.
            List<String> globalErrors = br.getGlobalErrors().stream()
                    .map(err -> err.getDefaultMessage())
                    .collect(Collectors.toList());
            model.addAttribute("globalErrors", globalErrors);

            // 4. Devolvemos la vista del formulario. Thymeleaf usará el BindingResult (br)
            //    para mostrar los errores de campo, y nuestra variable "globalErrors" para los demás.
            return "ADMIN/reclamos-form";
        }

        // Si la validación es exitosa, procedemos a guardar y redirigir.
        ReclamoDTO reclamoGuardado = reclamosSvc.guardarDesdeDTO(reclamo);
        redir.addFlashAttribute("success", "Paso 1: Datos guardados. Ahora, añade las medidas.");
        return "redirect:/admin/medidas/reclamo/" + reclamoGuardado.getIdReclamo();
    }

    // --- (Otros métodos como eliminar, api, etc.) ---
    @GetMapping("/eliminar/{id}")
    public String eliminarReclamo(@PathVariable Integer id, RedirectAttributes attributes) {
        try {
            reclamosSvc.eliminarPorId(id);
            attributes.addFlashAttribute("msg_success", "Reclamo eliminado correctamente");
        } catch (Exception e) {
            attributes.addFlashAttribute("msg_error", "Error al eliminar el reclamo");
        }
        return "redirect:/admin/reclamos";
    }

    @GetMapping("/exportar")
    public ResponseEntity<InputStreamResource> exportarReclamosAExcel(
            @RequestParam(defaultValue = "TODOS") String estado,
            @RequestParam(defaultValue = "TODO")  String buscarPor,
            @RequestParam(defaultValue = "")      String query,
            @RequestParam(required = false)       Integer anio,
            @RequestParam(required = false)       Integer mes) throws IOException {

        // 1. Obtener los datos filtrados (reutilizamos la lógica existente)
        List<ReclamoTablaDTO> reclamos = reclamosSvc.buscarFiltrado(estado, buscarPor, query, anio, mes);

        // 2. Generar el archivo Excel en memoria
        ByteArrayInputStream in = ExcelGenerator.reclamosToExcel(reclamos);

        // 3. Preparar la respuesta HTTP para la descarga
        HttpHeaders headers = new HttpHeaders();
        String fecha = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        headers.add("Content-Disposition", "attachment; filename=reclamos_" + fecha + ".xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new InputStreamResource(in));
    }


    @PostMapping("/importar")
    public String importarReclamosDesdeExcel(@RequestParam("archivoExcel") MultipartFile file, RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Por favor, seleccione un archivo para subir.");
            return "redirect:/admin/reclamos";
        }

        try {
            reclamosSvc.procesarTrama(file);
            redirectAttributes.addFlashAttribute("success", "Trama de reclamos procesada exitosamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al procesar el archivo: " + e.getMessage());
            e.printStackTrace(); // Es bueno ver el error completo en la consola del servidor
        }

        return "redirect:/admin/reclamos";
    }
}
