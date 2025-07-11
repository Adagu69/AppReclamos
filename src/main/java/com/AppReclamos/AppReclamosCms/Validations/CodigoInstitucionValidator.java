package com.AppReclamos.AppReclamosCms.Validations;

import com.AppReclamos.AppReclamosCms.Modelos.Enums.TipoInstitucion;
import com.AppReclamos.AppReclamosCms.Modelos.ReclamoDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CodigoInstitucionValidator implements ConstraintValidator<ValidCodigoInstitucion, ReclamoDTO> {
    @Override
    public boolean isValid(ReclamoDTO dto, ConstraintValidatorContext context) {
        if (dto.getTipoInstitucion() == null || dto.getCodigoInstitucion() == null || dto.getCodigoInstitucion().isBlank()) {
            return true; // Dejar que @NotNull / @NotBlank se encarguen.
        }

        TipoInstitucion tipo = dto.getTipoInstitucion();
        String codigo = dto.getCodigoInstitucion();
        boolean isValid = true;
        String message = "";

        switch (tipo) {
            case IPRESS:
            case UGIPRESS:
                if (!codigo.matches("^\\d{8}$")) {
                    isValid = false;
                    message = "Para IPRESS/UGIPRESS, el Código de Institución debe tener 8 dígitos.";
                }
                break;
            case IAFAS:
                if (!codigo.matches("^\\d{5}$")) {
                    isValid = false;
                    message = "Para IAFAS, el Código de Institución debe tener 5 dígitos.";
                }
                break;
        }

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode("codigoInstitucion")
                    .addConstraintViolation();
        }

        return isValid;
    }
}
