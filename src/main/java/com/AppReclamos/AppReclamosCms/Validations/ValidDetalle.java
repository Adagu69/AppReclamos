package com.AppReclamos.AppReclamosCms.Validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = DetalleReclamoValidator.class)
@Target({ ElementType.TYPE }) // Se aplicará a nivel de clase (al DTO)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidDetalle {
    String message() default "La fecha de recepción es obligatoria cuando el medio es Documento Escrito.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
