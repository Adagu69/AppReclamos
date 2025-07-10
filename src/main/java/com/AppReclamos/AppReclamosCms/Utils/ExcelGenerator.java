package com.AppReclamos.AppReclamosCms.Utils;

import com.AppReclamos.AppReclamosCms.Modelos.ReclamoTablaDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ExcelGenerator {

    public static ByteArrayInputStream reclamosToExcel(List<ReclamoTablaDTO> reclamos) throws IOException {
        String[] HEADERS = {
                "Fecha Reclamo", "Tipo Declarante", "Codigo Declarante", "Codigo UGIPRESS", "Tipo Institucion", "Medio",
                "Código Reclamo", "Tipo Doc.", "N° Doc.", "Razón Social", "Nombre", "Apellido P.", "Apellido M.",
                "Domicilio", "Telefono", "Fecha Recepcion", "Descripcion", "Servicio", "Competencia",
                "Clasificacion 1", "Clasificacion 2", "Clasificacion 3", "Etapa Reclamo", "Resultado Reclamo",
                "Motivo Conclusion", "Fecha Resultado", "Comunicacion Resultado", "Fecha Notificacion", "Estado"
        };
        String SHEET = "Reclamos";

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet(SHEET);

            // Estilo para la cabecera
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.WHITE.getIndex());
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);
            headerCellStyle.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // Fila de cabecera
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < HEADERS.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERS[col]);
                cell.setCellStyle(headerCellStyle);
            }

            // Filas de datos
            int rowIdx = 1;
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            for (ReclamoTablaDTO reclamo : reclamos) {
                Row row = sheet.createRow(rowIdx++);

                //row.createCell(0).setCellValue(reclamo.getPeriodoDeclaracion() != null ? reclamo.getPeriodoDeclaracion().format(dateFormatter) : "");
                row.createCell(1).setCellValue(reclamo.getTipoDeclarante() != null ? reclamo.getTipoDeclarante().toString() : "");
                row.createCell(2).setCellValue(reclamo.getCodigoDeclarante());
                row.createCell(3).setCellValue(reclamo.getCodigoUgipress());
                row.createCell(4).setCellValue(reclamo.getTipoInstitucion() != null ? reclamo.getTipoInstitucion().toString() : "");
                row.createCell(5).setCellValue(reclamo.getMedioRecepcion() != null ? reclamo.getMedioRecepcion().toString() : "");
                row.createCell(6).setCellValue(reclamo.getCodigoReclamo());
//                row.createCell(7).setCellValue(reclamo.getTipoDocumentoAfectado() != null ? reclamo.getTipoDocumentoAfectado().toString() : "");
//                row.createCell(8).setCellValue(reclamo.getNumeroDocumentoAfectado());
//                row.createCell(9).setCellValue(reclamo.getRazonSocialAfectado());
//                row.createCell(10).setCellValue(reclamo.getNombreAfectado());
//                row.createCell(11).setCellValue(reclamo.getApellidoPaterno());
//                row.createCell(12).setCellValue(reclamo.getApellidoMaterno());
//                row.createCell(13).setCellValue(reclamo.getDomicilio());
//                row.createCell(14).setCellValue(reclamo.getTelefono());
//                row.createCell(15).setCellValue(reclamo.getFechaRecepcion() != null ? reclamo.getFechaRecepcion().format(dateFormatter) : "");
                row.createCell(16).setCellValue(reclamo.getDescripcion());
                row.createCell(17).setCellValue(reclamo.getServicio() != null ? reclamo.getServicio().toString() : "");
                row.createCell(18).setCellValue(reclamo.getCompetencia() != null ? reclamo.getCompetencia().toString() : "");
                row.createCell(19).setCellValue(reclamo.getClasificacion1());
                row.createCell(20).setCellValue(reclamo.getClasificacion2());
                row.createCell(21).setCellValue(reclamo.getClasificacion3());
                row.createCell(22).setCellValue(reclamo.getEtapaReclamo() != null ? reclamo.getEtapaReclamo().toString() : "");
                row.createCell(23).setCellValue(reclamo.getResultado() != null ? reclamo.getResultado().toString() : "");
                row.createCell(24).setCellValue(reclamo.getMotivoConclusion() != null ? reclamo.getMotivoConclusion().toString() : "");
//                row.createCell(25).setCellValue(reclamo.getFechaResultado() != null ? reclamo.getFechaResultado().format(dateFormatter) : "");
//                row.createCell(26).setCellValue(reclamo.getComunicacionResultado() != null ? reclamo.getComunicacionResultado().toString() : "");
//                row.createCell(27).setCellValue(reclamo.getFechaNotificacion() != null ? reclamo.getFechaNotificacion().format(dateFormatter) : "");
                row.createCell(28).setCellValue(reclamo.getEstado() != null ? reclamo.getEstado().toString() : "");
            }

            // Autoajustar columnas
            for(int i=0; i<HEADERS.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}
