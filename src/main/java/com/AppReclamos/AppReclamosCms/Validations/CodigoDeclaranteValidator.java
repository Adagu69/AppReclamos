package com.AppReclamos.AppReclamosCms.Validations;

import com.AppReclamos.AppReclamosCms.Modelos.ReclamoDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CodigoDeclaranteValidator implements ConstraintValidator<ValidCodigoDeclarante, ReclamoDTO> {
    @Override
    public boolean isValid(ReclamoDTO dto, ConstraintValidatorContext ctx) {
        if (dto.getTipoDeclarante() == null || dto.getCodigoDeclarante() == null) {
            return false;
        }
        String c = dto.getCodigoDeclarante().trim();
        switch (dto.getTipoDeclarante()) {
            case IPRESS:
            case UGIPRESS:
                // 8 dígitos numéricos
                return c.matches("\\d{8}");
            case IAFAS:
                // 5 dígitos numéricos
                return c.matches("\\d{5}");
            default:
                return false;
        }
    }
}
