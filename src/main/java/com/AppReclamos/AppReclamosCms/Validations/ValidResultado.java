package com.AppReclamos.AppReclamosCms.Validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = ResultadoValidator.class)
@Target({ ElementType.TYPE }) // Se aplicará a nivel de clase (al ReclamoDTO)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidResultado {
    String message() default "Los datos de la sección 'Resultado y Notificación' contienen errores de validación.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
