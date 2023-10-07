package ml.kalanblow.gestiondesinscriptions.validation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ml.kalanblow.gestiondesinscriptions.constraint.NotExistingUser;
import ml.kalanblow.gestiondesinscriptions.model.Email;
import ml.kalanblow.gestiondesinscriptions.response.AbstractUserFormData;

import ml.kalanblow.gestiondesinscriptions.service.EleveService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;


public class NotExistingUserValidator implements ConstraintValidator<NotExistingUser, AbstractUserFormData> {

    private final EleveService userService;

    @Autowired
    public NotExistingUserValidator(EleveService userService) {
        super();
        this.userService = userService;
    }

    @Override
    public void initialize(NotExistingUser constraint) {

    }

    @Override
    public boolean isValid(AbstractUserFormData value, ConstraintValidatorContext context) {

        if (!StringUtils.isEmpty(value.getEmail()) && userService.verifierExistenceEmail(new Email(value.getEmail()))) {

            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{UserAlreadyExisting}").addPropertyNode("email")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}

