package com.AppReclamos.AppReclamosCms.Validations;

import com.AppReclamos.AppReclamosCms.Modelos.Enums.EstadoReclamo;
import com.AppReclamos.AppReclamosCms.Modelos.Enums.ResultadoReclamo;
import com.AppReclamos.AppReclamosCms.Modelos.ReclamoDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ResultadoValidator implements ConstraintValidator<ValidResultado, ReclamoDTO> {
    @Override
    public boolean isValid(ReclamoDTO dto, ConstraintValidatorContext context) {
        if (dto.getResultados() == null || dto.getResultados().isEmpty() || dto.getEstado() == null) {
            return true; // No hay nada que validar o el estado falta.
        }

        boolean isValid = true;
        context.disableDefaultConstraintViolation();

        var resultadoDto = dto.getResultados().get(0);
        EstadoReclamo estado = dto.getEstado();
        ResultadoReclamo resultado = resultadoDto.getResultado();

        // Criterio 38: Relación entre Estado y Resultado del Reclamo
        if (estado == EstadoReclamo.EN_TRAMITE || estado == EstadoReclamo.TRASLADADO) {
            if (resultado != ResultadoReclamo.PENDIENTE) {
                isValid = false;
                addError(context, "resultados[0].resultado", "Para un reclamo 'En Trámite' o 'Trasladado', el resultado debe ser 'Pendiente'.");
            }
        } else { // Para RESUELTO, ARCHIVADO, ACUMULADO, CONCLUIDO
            if (resultado == ResultadoReclamo.PENDIENTE) {
                isValid = false;
                addError(context, "resultados[0].resultado", "El resultado 'Pendiente' no es válido para este estado del reclamo.");
            }
        }

        // Criterio 39: Motivo de Conclusión Anticipada
        if (resultado == ResultadoReclamo.CONCLUIDO_ANTICIPADAMENTE) {
            if (resultadoDto.getMotivoConclusion() == null) {
                isValid = false;
                addError(context, "resultados[0].motivoConclusion", "El Motivo es obligatorio si el resultado es 'Concluido Anticipadamente'.");
            }
        }

        // Criterios 40, 41, 42: Campos requeridos si el resultado NO es Pendiente
        if (resultado != ResultadoReclamo.PENDIENTE) {
            if (isBlank(resultadoDto.getFechaResultado())) {
                isValid = false;
                addError(context, "resultados[0].fechaResultado", "La Fecha de Resultado es obligatoria.");
            }
            if (resultadoDto.getComunicacionResultado() == null) {
                isValid = false;
                addError(context, "resultados[0].comunicacionResultado", "La Comunicación del Resultado es obligatoria.");
            }
            if (isBlank(resultadoDto.getFechaNotificacion())) {
                isValid = false;
                addError(context, "resultados[0].fechaNotificacion", "La Fecha de Notificación es obligatoria.");
            }
        }

        return isValid;
    }

    private void addError(ConstraintValidatorContext context, String field, String message) {
        context.buildConstraintViolationWithTemplate(message).addPropertyNode(field).addConstraintViolation();
    }

    private boolean isBlank(String str) {
        return str == null || str.isBlank();
    }
}
