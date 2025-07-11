package com.AppReclamos.AppReclamosCms.Validations;

import com.AppReclamos.AppReclamosCms.Modelos.Enums.TipoDeclarante;
import com.AppReclamos.AppReclamosCms.Modelos.ReclamoDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CodigoDeclaranteValidator implements ConstraintValidator<ValidCodigoDeclarante, ReclamoDTO> {
    @Override
    public boolean isValid(ReclamoDTO dto, ConstraintValidatorContext context) {
        if (dto.getTipoDeclarante() == null || dto.getCodigoDeclarante() == null || dto.getCodigoDeclarante().isBlank()) {
            return true; // Se deja pasar para que @NotNull y @NotBlank hagan su trabajo.
        }

        TipoDeclarante tipo = dto.getTipoDeclarante();
        String codigo = dto.getCodigoDeclarante();
        boolean isValid = true;
        String message = "";

        switch (tipo) {
            case IPRESS:
            case UGIPRESS:
                if (!codigo.matches("^\\d{8}$")) {
                    isValid = false;
                    message = "Para IPRESS/UGIPRESS, el código debe tener 8 dígitos numéricos.";
                }
                break;
            case IAFAS:
                if (!codigo.matches("^\\d{5}$")) {
                    isValid = false;
                    message = "Para IAFAS, el código debe tener 5 dígitos numéricos.";
                }
                break;
        }

        // Si la validación falla, se añade el mensaje de error al campo específico.
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode("codigoDeclarante") // Asocia el error al campo 'codigoDeclarante'
                    .addConstraintViolation();
        }

        return isValid;
    }
}
