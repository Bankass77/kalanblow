package ml.kalanblow.gestiondesinscriptions.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ml.kalanblow.gestiondesinscriptions.constraint.PasswordsMatch;
import ml.kalanblow.gestiondesinscriptions.response.CreateUserFormData;


/**
 * Validator to check if the password and passwordRepeated fields in CreateUserFormData match.
 */
public class PasswordsMatchValidator implements ConstraintValidator<PasswordsMatch, CreateUserFormData> {


    /**
     * Initializes the validator.
     *
     * @param constraintAnnotation The annotation instance.
     */
    @Override
    public void initialize(PasswordsMatch constraintAnnotation) {
    }


    /**
     * Validates if the password and passwordRepeated fields match.
     *
     * @param value   The CreateUserFormData instance to validate.
     * @param context The validation context.
     * @return True if the password and passwordRepeated fields match, false otherwise.
     */
    @Override
    public boolean isValid(CreateUserFormData value, ConstraintValidatorContext context) {
        String password = value.getPassword();
        String passwordRepeated = value.getPasswordRepeated();

        // Check for null values
        if (!password.equals(passwordRepeated)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{PasswordsNotMatching}")
                    .addPropertyNode("passwordRepeated")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
