package com.AppReclamos.AppReclamosCms.Validations;

import com.AppReclamos.AppReclamosCms.Modelos.Enums.TipoDocumento;
import com.AppReclamos.AppReclamosCms.Modelos.PersonaReclamoDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PersonaValidator implements ConstraintValidator<ValidPersona, PersonaReclamoDTO> {


    @Override
    public boolean isValid(PersonaReclamoDTO dto, ConstraintValidatorContext context) {
        if (dto.getTipoDocumento() == null || dto.getNumeroDocumento() == null || dto.getNumeroDocumento().isBlank()) {
            return true; // Dejar que las anotaciones @NotNull y @NotBlank se encarguen primero.
        }

        boolean isValid = true;
        TipoDocumento tipoDoc = dto.getTipoDocumento();
        String numDoc = dto.getNumeroDocumento();

        // Deshabilitar el mensaje de error por defecto
        context.disableDefaultConstraintViolation();

        // --- Criterios para Número de Documento (C1, C2, C3) ---
        switch (tipoDoc) {
            case DNI:
                if (!numDoc.matches("^\\d{8}$")) {
                    isValid = false;
                    context.buildConstraintViolationWithTemplate("DNI debe tener 8 dígitos numéricos.")
                            .addPropertyNode("numeroDocumento").addConstraintViolation();
                }
                break;
            case CE:
                if (!numDoc.matches("^[a-zA-Z0-9]{9}$")) {
                    isValid = false;
                    context.buildConstraintViolationWithTemplate("Carnet de Extranjería debe tener 9 caracteres alfanuméricos.")
                            .addPropertyNode("numeroDocumento").addConstraintViolation();
                }
                break;
            case RUC:
                if (!numDoc.matches("^\\d{11}$")) {
                    isValid = false;
                    context.buildConstraintViolationWithTemplate("RUC debe tener 11 dígitos numéricos.")
                            .addPropertyNode("numeroDocumento").addConstraintViolation();
                }
                break;
        }

        // --- Criterios para Nombres vs Razón Social (C5, C6) ---
        if (tipoDoc == TipoDocumento.RUC) {
            // Si es RUC, Razón Social es obligatoria
            if (dto.getRazonSocial() == null || dto.getRazonSocial().isBlank()) {
                isValid = false;
                context.buildConstraintViolationWithTemplate("Razón Social es obligatoria para RUC.")
                        .addPropertyNode("razonSocial").addConstraintViolation();
            }
        } else {
            // Si NO es RUC, Nombres y Apellidos son obligatorios
            if (dto.getNombres() == null || dto.getNombres().isBlank()) {
                isValid = false;
                context.buildConstraintViolationWithTemplate("Nombres son obligatorios.")
                        .addPropertyNode("nombres").addConstraintViolation();
            }
            if (dto.getApellidoPaterno() == null || dto.getApellidoPaterno().isBlank()) {
                isValid = false;
                context.buildConstraintViolationWithTemplate("Apellido Paterno es obligatorio.")
                        .addPropertyNode("apellidoPaterno").addConstraintViolation();
            }
            if (dto.getApellidoMaterno() == null || dto.getApellidoMaterno().isBlank()) {
                isValid = false;
                context.buildConstraintViolationWithTemplate("Apellido Materno es obligatorio.")
                        .addPropertyNode("apellidoMaterno").addConstraintViolation();
            }
        }

        return isValid;
    }
}

