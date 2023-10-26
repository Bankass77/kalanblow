package ml.kalanblow.gestiondesinscriptions.constraint;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ml.kalanblow.gestiondesinscriptions.validation.NotExistingEtablissementValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotExistingEtablissementValidator.class)
public @interface NotExistingEtablissement {

    String message () default "{EtablissementAlreadyExisting}";

    Class<?> [] groups() default {};
    Class <? extends Payload> [] payload () default {};
}
