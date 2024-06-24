package ml.kalanblow.gestiondesinscriptions.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ml.kalanblow.gestiondesinscriptions.validation.PlageHoraireValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PlageHoraireValidator.class)
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface PlageHoraire {

    String message() default "{plageHoraire.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
