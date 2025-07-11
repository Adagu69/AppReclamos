package com.AppReclamos.AppReclamosCms.Validations;


import com.AppReclamos.AppReclamosCms.Modelos.Enums.EstadoReclamo;
import com.AppReclamos.AppReclamosCms.Modelos.Enums.GestionCompetencia;
import com.AppReclamos.AppReclamosCms.Modelos.ReclamoDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GestionValidator implements ConstraintValidator<ValidGestion, ReclamoDTO> {
    @Override
    public boolean isValid(ReclamoDTO dto, ConstraintValidatorContext context) {
        if (dto.getGestion() == null || dto.getEstado() == null) {
            return true; // Dejar que otras validaciones se encarguen.
        }

        boolean isValid = true;
        context.disableDefaultConstraintViolation(); // Para controlar los mensajes de error.

        // --- Criterios para Clasificación 1, 2, 3 ---
        GestionCompetencia competencia = dto.getGestion().getCompetencia();
        if (competencia == GestionCompetencia.SI || competencia == GestionCompetencia.COMPARTIDA) {
            if (dto.getGestion().getClasificacion1() == null || dto.getGestion().getClasificacion1().isBlank()) {
                isValid = false;
                context.buildConstraintViolationWithTemplate("Clasificación 1 es obligatoria para esta Competencia.")
                        .addPropertyNode("gestion.clasificacion1").addConstraintViolation();
            }
            // Repetir para clasificacion2 y clasificacion3 si es necesario.
        }

        // --- Criterio para Código Primigenio ---
        if (dto.getEstado() == EstadoReclamo.ACUMULADO) {
            if (dto.getGestion().getCodigoPrimigenio() == null || dto.getGestion().getCodigoPrimigenio().isBlank()) {
                isValid = false;
                context.buildConstraintViolationWithTemplate("Código Primigenio es obligatorio cuando el estado es Acumulado.")
                        .addPropertyNode("gestion.codigoPrimigenio").addConstraintViolation();
            }
        }

        // --- Criterios para Destino del Traslado ---
        if (dto.getEstado() == EstadoReclamo.TRASLADADO) {
            if (dto.getGestion().getTipoAdministraTraslado() == null) {
                isValid = false;
                context.buildConstraintViolationWithTemplate("Tipo de Administrado Destino es obligatorio cuando el estado es Trasladado.")
                        .addPropertyNode("gestion.tipoAdministraTraslado").addConstraintViolation();
            }
            if (dto.getGestion().getCodigoAdministraTraslado() == null || dto.getGestion().getCodigoAdministraTraslado().isBlank()) {
                isValid = false;
                context.buildConstraintViolationWithTemplate("Código de Administrado Destino es obligatorio cuando el estado es Trasladado.")
                        .addPropertyNode("gestion.codigoAdministraTraslado").addConstraintViolation();
            }
        }

        return isValid;
    }
}
