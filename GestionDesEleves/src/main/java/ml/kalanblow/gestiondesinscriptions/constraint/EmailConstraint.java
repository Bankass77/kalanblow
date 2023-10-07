package ml.kalanblow.gestiondesinscriptions.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.*;

@Documented
@Email(message = "Please provide a valid email address")
@Constraint(validatedBy = {})
@Retention(RetentionPolicy.RUNTIME)
@NotEmpty
@Pattern(regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$", message = "Invalid Email:le fomat du mail est incorrect")
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
public @interface EmailConstraint {

    String message() default "Invalid Email: le format du mail est incorrect";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}