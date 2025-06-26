package com.AppReclamos.AppReclamosCms.Validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CodigoDeclaranteValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCodigoDeclarante {
    String message() default "Código declarante inválido para el tipo de declarante";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
