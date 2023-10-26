package ml.kalanblow.gestiondesinscriptions.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ml.kalanblow.gestiondesinscriptions.constraint.NotExistingEtablissement;
import ml.kalanblow.gestiondesinscriptions.model.Email;
import ml.kalanblow.gestiondesinscriptions.response.AbstractUserFormData;
import ml.kalanblow.gestiondesinscriptions.service.EtablissementService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class NotExistingEtablissementValidator implements ConstraintValidator<NotExistingEtablissement, AbstractUserFormData> {


    private final EtablissementService etablissementService;


    @Autowired
    public NotExistingEtablissementValidator(EtablissementService etablissementService) {
        this.etablissementService = etablissementService;
    }

    /**
     * Implements the validation logic.
     * The state of {@code value} must not be altered.
     * <p>
     * This method can be accessed concurrently, thread-safety must be ensured
     * by the implementation.
     *
     * @param value   object to validate
     * @param context context in which the constraint is evaluated
     * @return {@code false} if {@code value} does not pass the constraint
     */
    @Override
    public boolean isValid(AbstractUserFormData value, ConstraintValidatorContext context) {

        if (StringUtils.isEmpty(value.getEmail()) && etablissementService.findEtablissementScolaireByEmail(new Email(value.getEmail())).isPresent()) {

            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{EtablissementAlreadyExisting}").addPropertyNode("email").addConstraintViolation();
            return false;
        }
        return true;
    }


}
