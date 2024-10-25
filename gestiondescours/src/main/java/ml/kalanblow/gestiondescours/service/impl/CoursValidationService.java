package ml.kalanblow.gestiondescours.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ml.kalanblow.gestiondescours.exception.EntityType;
import ml.kalanblow.gestiondescours.exception.ExceptionType;
import ml.kalanblow.gestiondescours.exception.KaladewnManagementException;
import ml.kalanblow.gestiondescours.model.Matiere;
import ml.kalanblow.gestiondescours.model.Salle;

@Service
@Data
@Slf4j
public class CoursValidationService {

    public Salle validateSalle(Optional<Salle> salleOptional, Long salleId) {
        return salleOptional.orElseThrow(() ->
                KaladewnManagementException.throwExceptionWithId(
                        EntityType.SALLE, ExceptionType.ENTITY_NOT_FOUND, salleId.toString(), "Salle non trouvée"
                )
        );
    }

    public Matiere validateMatiere(Optional<Matiere> matiereOptional, Long matiereId) {
        return matiereOptional.orElseThrow(() ->
                KaladewnManagementException.throwExceptionWithId(
                        EntityType.MATIERE, ExceptionType.ENTITY_NOT_FOUND, matiereId.toString(), "Matière non trouvée"
                )
        );
    }
}
