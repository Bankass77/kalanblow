package ml.kalanblow.gestiondesinscriptions.constraint;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Documented
@NotNull(message = "{customNotNull.message}")
@Size(min = 1, max = 50, message = "{customSize.message}")
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface CustomValidation {

    @AliasFor("message")
    String customMessage() default "";

    @AliasFor("customMessage")
    @Value("#{T(java.lang.String).format('{propertyName}', target.name)}")
    String message() default "";

    @Value("#{T(java.lang.String).format('{propertyName}', target.name)}")
    String customNotNullMessage() default "";

    @Value("#{T(java.lang.String).format('{propertyName}', target.name)}")
    String customSizeMessage() default "";
}
