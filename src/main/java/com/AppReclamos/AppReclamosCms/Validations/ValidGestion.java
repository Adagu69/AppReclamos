package com.AppReclamos.AppReclamosCms.Validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = GestionValidator.class)
@Target({ ElementType.TYPE }) // Se aplica a nivel de clase (al DTO)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidGestion {
    String message() default "Los datos de la sección 'Gestión Interna' contienen errores de validación.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
