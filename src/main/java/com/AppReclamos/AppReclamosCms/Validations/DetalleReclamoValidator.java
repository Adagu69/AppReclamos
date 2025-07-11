package com.AppReclamos.AppReclamosCms.Validations;

import com.AppReclamos.AppReclamosCms.Modelos.DetalleReclamoDTO;
import com.AppReclamos.AppReclamosCms.Modelos.Enums.MedioPresentacion;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DetalleReclamoValidator implements ConstraintValidator<ValidDetalle, DetalleReclamoDTO> {
    @Override
    public boolean isValid(DetalleReclamoDTO dto, ConstraintValidatorContext context) { if (dto == null || dto.getMedioPresentacion() == null) {
        return true; // No es responsabilidad de este validador.
    }

        // Criterio C1: Si el medio es "Documento Escrito", la fecha es obligatoria.
        if (dto.getMedioPresentacion() == MedioPresentacion.DOCUMENTO_ESCRITO) {
            if (dto.getFechaRecepcion() == null || dto.getFechaRecepcion().isBlank()) {
                // Si falla la validación, añade el mensaje al campo específico.
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("La fecha de recepción es requerida para este medio.")
                        .addPropertyNode("fechaRecepcion")
                        .addConstraintViolation();
                return false;
            }
        }

        // Criterio C2: No se valida aquí, ya que el campo estaría vacío si no es requerido.
        return true;
    }
}
