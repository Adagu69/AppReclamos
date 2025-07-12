package com.AppReclamos.AppReclamosCms.Controladores;

import com.AppReclamos.AppReclamosCms.Modelos.*;
import com.AppReclamos.AppReclamosCms.Modelos.Enums.*;
import com.AppReclamos.AppReclamosCms.Servicios.interfaces.IReclamosServicios;
import com.AppReclamos.AppReclamosCms.Servicios.impl.ReporteTramaServicio;
import com.AppReclamos.AppReclamosCms.Utils.ExcelGenerator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
    private final ReporteTramaServicio reporteTramaServicio;

    private static final Logger log = LoggerFactory.getLogger(ReclamosController.class);

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
        // 1. Creamos un DTO vacío. Los campos 'presentante' y 'afectado'
        //    ya deberían estar inicializados dentro del constructor de ReclamoDTO.
        ReclamoDTO dto = new ReclamoDTO();

        // 2. ¡Se eliminan las líneas que usaban getPersonas()!
        // dto.getPersonas().add(new PersonaReclamoDTO()); <--- Eliminado
        // dto.getPersonas().add(new PersonaReclamoDTO()); <--- Eliminado

        // 3. La inicialización de otras listas se mantiene si es necesaria.
        dto.getDetalles().add(new DetalleReclamoDTO());
        dto.setGestion(new GestionReclamoDTO());
        dto.getResultados().add(new ResultadoNotificacionDTO());

        // 4. Pasamos el DTO al modelo
        model.addAttribute("reclamo", dto);
        model.addAttribute("title", "Nuevo Reclamo");
        addEnumsToModel(model);
        return "ADMIN/reclamos-form";
    }


    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model, RedirectAttributes redir) {
        ReclamoDTO dto = reclamosSvc.buscarPorId(id);
        if (dto == null) {
            redir.addFlashAttribute("error", "El reclamo con ID " + id + " no existe.");
            return "redirect:/admin/reclamos";
        }

        // --- REFUERZO CLAVE ---
        // Nos aseguramos de que 'presentante' y 'afectado' nunca sean nulos
        // para evitar un NullPointerException en Thymeleaf.
        if (dto.getPresentante() == null) {
            dto.setPresentante(new PersonaReclamoDTO());
            log.warn("El reclamo con ID {} no tenía presentante. Se inicializó uno nuevo.", id);
        }
        if (dto.getAfectado() == null) {
            dto.setAfectado(new PersonaReclamoDTO());
            log.warn("El reclamo con ID {} no tenía afectado. Se inicializó uno nuevo.", id);
        }
        // ... (Tu lógica para asegurar listas mínimas para detalles, etc., se mantiene) ...

        model.addAttribute("reclamo", dto);
        model.addAttribute("title", "Editar Reclamo");
        addEnumsToModel(model);
        return "ADMIN/reclamos-form";
    }

    @PostMapping("/guardar")
    public String save(@Valid @ModelAttribute("reclamo") ReclamoDTO reclamo,
                       BindingResult br,
                       Model model,
                       RedirectAttributes redir) {

        // --- REFUERZO DE VALIDACIÓN ---
        // La validación se mantiene, ya que @Valid en el DTO principal debería
        // activar la validación en cascada para 'presentante' y 'afectado' (si están anotados con @Valid).
        if (br.hasErrors()) {
            log.error("Errores de validación encontrados: {}", br.getAllErrors());

            // La lógica para manejar errores ya es robusta y se mantiene.
            addEnumsToModel(model);

            model.addAttribute("title", reclamo.getIdReclamo() != null ? "Editar Reclamo" : "Nuevo Reclamo");

            List<String> globalErrors = br.getGlobalErrors().stream()
                    .map(err -> err.getDefaultMessage())
                    .collect(Collectors.toList());
            model.addAttribute("globalErrors", globalErrors);

            return "ADMIN/reclamos-form";
        }

        // --- REFUERZO DE LÓGICA DE GUARDADO ---
        try {
            ReclamoDTO reclamoGuardado = reclamosSvc.guardarDesdeDTO(reclamo);
            log.info("Reclamo guardado exitosamente con ID: {}", reclamoGuardado.getIdReclamo());
            redir.addFlashAttribute("success", "Reclamo guardado correctamente. Ahora, puede añadir las medidas adoptadas.");
            // Redireccionamos a una página de medidas (si existe) o a la lista principal.
            return "redirect:/admin/medidas/reclamo/" + reclamoGuardado.getIdReclamo();
        } catch (Exception e) {
            // Capturamos cualquier error inesperado del servicio.
            log.error("Error al guardar el reclamo: ", e);
            redir.addFlashAttribute("error", "Ocurrió un error inesperado al guardar el reclamo.");
            // Si falla, lo devolvemos al formulario de edición (si tiene ID) o al de nuevo.
            if (reclamo.getIdReclamo() != null) {
                return "redirect:/admin/reclamos/editar/" + reclamo.getIdReclamo();
            }
            return "redirect:/admin/reclamos/nuevo";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarReclamo(@PathVariable Integer id, RedirectAttributes attributes) {
        try {
            reclamosSvc.eliminarPorId(id);
            log.info("Reclamo con ID {} eliminado correctamente.", id);
            attributes.addFlashAttribute("success", "Reclamo eliminado correctamente.");
        } catch (Exception e) {
            // --- REFUERZO DE MANEJO DE ERRORES ---
            // Capturamos errores específicos si es posible (ej. si el reclamo no se puede borrar por FK).
            log.error("Error al intentar eliminar el reclamo con ID {}: ", id, e);
            attributes.addFlashAttribute("error", "Error al eliminar el reclamo. Es posible que tenga datos asociados.");
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

    @GetMapping("/generarTrama")
    public ResponseEntity<ByteArrayResource> generarTramaTxt(
            @RequestParam(value = "estado", defaultValue = "TODOS") String estado,
            @RequestParam(value = "buscarPor", defaultValue = "TODO") String buscarPor,
            @RequestParam(value = "query", required = false) String query,
            @RequestParam(value = "anio", required = false) Integer anio,
            @RequestParam(value = "mes", required = false) Integer mes) {

        try {
            ByteArrayResource recurso = reporteTramaServicio.generarTramaTxt(estado, buscarPor, query, anio, mes);
            String nombreArchivo = reporteTramaServicio.generarNombreArchivo();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nombreArchivo + "\"")
                    .header(HttpHeaders.CONTENT_TYPE, "text/plain; charset=UTF-8")
                    .body(recurso);

        } catch (Exception e) {
            log.error("Error al generar la trama TXT", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
