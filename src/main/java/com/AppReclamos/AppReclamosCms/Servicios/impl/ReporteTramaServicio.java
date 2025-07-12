package com.AppReclamos.AppReclamosCms.Servicios.impl;

import com.AppReclamos.AppReclamosCms.Modelos.ReclamoTablaDTO;
import com.AppReclamos.AppReclamosCms.Servicios.interfaces.IReclamosServicios;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReporteTramaServicio {

    private final IReclamosServicios reclamosServicios;
    private static final Logger log = LoggerFactory.getLogger(ReporteTramaServicio.class);

    /**
     * Genera un archivo TXT con la trama de reclamos según los filtros aplicados
     */
    public ByteArrayResource generarTramaTxt(String estado, String buscarPor, String query, Integer anio, Integer mes) throws IOException {
        log.info("Generando trama TXT con filtros - Estado: {}, BuscarPor: {}, Query: {}, Año: {}, Mes: {}", 
                estado, buscarPor, query, anio, mes);

        // Obtener los datos filtrados
        List<ReclamoTablaDTO> reclamos = reclamosServicios.buscarFiltrado(estado, buscarPor, query, anio, mes);

        // Generar el contenido del archivo
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {

            for (ReclamoTablaDTO reclamo : reclamos) {
                String lineaTrama = construirLineaTrama(reclamo);
                writer.write(lineaTrama);
                writer.write(System.lineSeparator()); // Nueva línea para cada reclamo
            }

            writer.flush();
        }

        byte[] contenido = outputStream.toByteArray();
        log.info("Trama TXT generada exitosamente. {} reclamos procesados, {} bytes generados",
                reclamos.size(), contenido.length);

        return new ByteArrayResource(contenido);
    }

    /**
     * Construye una línea de trama en el formato especificado
     */
    private String construirLineaTrama(ReclamoTablaDTO reclamo) {
        StringBuilder trama = new StringBuilder();

        // Construcción de la trama según el formato especificado
        trama.append(obtenerValor(reclamo.getPeriodoDeclaracion())).append("|");                    // 202501
        trama.append(obtenerValorNumerico(reclamo.getTipoDeclarante())).append("|");                // 1
        trama.append(obtenerValor(reclamo.getCodigoDeclarante())).append("|");                      // 00009641
        trama.append(obtenerValor(reclamo.getCodigoUgipress())).append("|");                        // 00009641
        trama.append(obtenerValorNumerico(reclamo.getTipoInstitucion())).append("|");               // 1
        trama.append(obtenerValor(reclamo.getCodigoInstitucion())).append("|");                     // 00009641
        trama.append(obtenerValorNumerico(reclamo.getMedioRecepcion())).append("|");                // 2
        trama.append(obtenerValor(reclamo.getCodigoReclamo())).append("|");                         // 00009641-452

        // Datos del presentante
        trama.append(obtenerValorNumerico(reclamo.getTipoDocumento_presentante())).append("|");     // 1
        trama.append(obtenerValor(reclamo.getNumeroDocumento_presentante())).append("|");           // 92151490
        trama.append(obtenerValor(reclamo.getRazonSocial_presentante())).append("|");               // (vacío)
        trama.append(obtenerValor(reclamo.getNombres_presentante())).append("|");                   // EMILIA
        trama.append(obtenerValor(reclamo.getApellidoPaterno_presentante())).append("|");           // TORRES
        trama.append(obtenerValor(reclamo.getApellidoMaterno_presentante())).append("|");           // VALQUE

        // Datos del afectado
        trama.append(obtenerValorNumerico(reclamo.getTipoDocumento_afectado())).append("|");        // 1
        trama.append(obtenerValor(reclamo.getNumeroDocumento_afectado())).append("|");              // 45235695
        trama.append(obtenerValor(reclamo.getRazonSocial_afectado())).append("|");                  // (vacío)
        trama.append(obtenerValor(reclamo.getNombres_afectado())).append("|");                      // JUAN CARLOS
        trama.append(obtenerValor(reclamo.getApellidoPaterno_afectado())).append("|");              // TORRES
        trama.append(obtenerValor(reclamo.getApellidoMaterno_afectado())).append("|");              // JARA

        // Datos adicionales del presentante
        trama.append(obtenerValorNumerico(reclamo.getResultadoPorCorreo_presentante())).append("|"); // 1
        trama.append(obtenerValor(reclamo.getCorreoElectronico_presentante())).append("|");         // email
        trama.append(obtenerValor(reclamo.getDomicilio_presentante())).append("|");                 // domicilio
        trama.append(obtenerValor(reclamo.getTelefono_presentante())).append("|");                  // teléfono

        // Detalle del reclamo
        trama.append(obtenerValorNumerico(reclamo.getMedioPresentacion())).append("|");             // 2
        trama.append(obtenerValor(reclamo.getFechaRecepcion())).append("|");                        // 20241213
        trama.append(obtenerValor(reclamo.getDescripcion())).append("|");                           // descripción

        // Gestión del reclamo
        trama.append(obtenerValor(reclamo.getServicio())).append("|");                              // 03
        trama.append(obtenerValorNumerico(reclamo.getCompetencia())).append("|");                   // 1
        trama.append(obtenerValor(reclamo.getClasificacion1())).append("|");                        // 2001
        trama.append(obtenerValor(reclamo.getClasificacion2())).append("|");                        // 1118
        trama.append(obtenerValor(reclamo.getClasificacion3())).append("|");                        // (vacío)
        trama.append(obtenerValorNumerico(reclamo.getEtapaReclamo())).append("|");                  // 6
        trama.append(obtenerValorNumerico(reclamo.getEstado())).append("|");                        // (vacío)
        trama.append(obtenerValor(reclamo.getCodigoPrimigenio())).append("|");                      // 4
        trama.append(obtenerValorNumerico(reclamo.getTipoAdministraTraslado())).append("|");        // (vacío)
        trama.append(obtenerValor(reclamo.getCodigoAdministraTraslado())).append("|");              // (vacío)

        // Resultado y notificación
        trama.append(obtenerValorNumerico(reclamo.getResultado())).append("|");                     // 3
        trama.append(obtenerValorNumerico(reclamo.getMotivoConclusion())).append("|");              // (vacío)
        trama.append(obtenerValor(reclamo.getFechaResultado())).append("|");                        // 20250110
        trama.append(obtenerValorNumerico(reclamo.getComunicacionResultado())).append("|");         // 2
        trama.append(obtenerValor(reclamo.getFechaNotificacion()));                                 // 20250128

        return trama.toString();
    }

    /**
     * Método auxiliar para manejar valores String nulos
     */
    private String obtenerValor(String value) {
        return value != null ? value : "";
    }

    /**
     * Método auxiliar para manejar valores numéricos nulos
     */
    private String obtenerValorNumerico(Object value) {
        return value != null ? value.toString() : "";
    }

    /**
     * Genera el nombre del archivo con timestamp
     */
    public String generarNombreArchivo() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        return "trama_reclamos_" + timestamp + ".txt";
    }
}
