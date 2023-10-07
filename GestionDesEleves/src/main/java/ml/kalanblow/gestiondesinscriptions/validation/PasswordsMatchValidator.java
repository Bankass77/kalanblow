package ml.kalanblow.gestiondesinscriptions.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ml.kalanblow.gestiondesinscriptions.constraint.PasswordsMatch;
import ml.kalanblow.gestiondesinscriptions.response.CreateUserFormData;


public class PasswordsMatchValidator implements ConstraintValidator<PasswordsMatch, CreateUserFormData> {

    @Override
    public void initialize(PasswordsMatch constraintAnnotation) {
    }

    @Override
    public boolean isValid(CreateUserFormData value, ConstraintValidatorContext context) {

        if (!value.getPassword().equals(value.getPasswordRepeated())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{PasswordsNotMatching}").addPropertyNode("passwordRepeated")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
