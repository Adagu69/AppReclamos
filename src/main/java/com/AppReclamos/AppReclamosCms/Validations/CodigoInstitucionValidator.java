package com.AppReclamos.AppReclamosCms.Validations;

import com.AppReclamos.AppReclamosCms.Modelos.ReclamoDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CodigoInstitucionValidator implements ConstraintValidator<ValidCodigoInstitucion, ReclamoDTO> {
    @Override
    public boolean isValid(ReclamoDTO dto, ConstraintValidatorContext ctx) {
        if (dto.getTipoInstitucion() == null || dto.getCodigoInstitucion() == null) {
            return false;
        }
        String c = dto.getCodigoInstitucion().trim();
        switch (dto.getTipoInstitucion()) {
            case IPRESS:
            case UGIPRESS:
                return c.matches("\\d{8}");
            case IAFAS:
                return c.matches("\\d{5}");
            default:
                return false;
        }
    }
}
