package com.AppReclamos.AppReclamosCms.Validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CodigoInstitucionValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCodigoInstitucion {
    String message() default "Código de institución inválido para el tipo de institución";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
