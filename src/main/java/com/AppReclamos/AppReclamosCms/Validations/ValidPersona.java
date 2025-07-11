package com.AppReclamos.AppReclamosCms.Validations;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = PersonaValidator.class)
@Target({ ElementType.TYPE }) // Se aplicará a nivel de clase (al DTO completo)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface  ValidPersona {
    String message() default "Los datos de la persona no son válidos según el tipo de documento.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
