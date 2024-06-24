package ml.kalanblow.gestiondesinscriptions.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ml.kalanblow.gestiondesinscriptions.model.Horaire;
import ml.kalanblow.gestiondesinscriptions.constraint.PlageHoraire;

public class PlageHoraireValidator implements ConstraintValidator<PlageHoraire, Horaire> {

    @Override
    public void initialize(PlageHoraire constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Horaire value, ConstraintValidatorContext context) {

        if ((value.getHeureDebut() != null) && (value.getHeureFin() != null) && value.getHeureFin().isBefore(value.getHeureDebut())) {
            return false;
        }
        return true;
    }
}
