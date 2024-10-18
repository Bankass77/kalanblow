package ml.kalanblow.gestiondescours.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ml.kalanblow.gestiondescours.exception.EntityType;
import ml.kalanblow.gestiondescours.exception.ExceptionType;
import ml.kalanblow.gestiondescours.exception.KaladewnManagementException;
import ml.kalanblow.gestiondescours.model.Matiere;
import ml.kalanblow.gestiondescours.model.Salle;
import ml.kalanblow.gestiondescours.service.MatiereService;
import ml.kalanblow.gestiondescours.service.SalleService;

@Service
@Data
@Slf4j
public class CoursValidationService {
    private final SalleService salleService;
    private final MatiereService matiereService;

    @Autowired
    public CoursValidationService(final SalleService salleService, final MatiereService matiereService) {
        this.salleService = salleService;
        this.matiereService = matiereService;
    }

    public Salle validateSalle(Long salleId) {
        return verifySalleExists(salleId);
    }

    public Matiere validateMatiere(Long matiereId) {
        return verifyMatiereExists(matiereId);
    }

    private Salle verifySalleExists(Long salleId) {
        Optional<Salle> salleOptional = salleService.findSalleBy(salleId);
        if (salleOptional.isEmpty()) {
            log.error("La salle avec l'ID {} n'existe pas.", salleId);
            throw KaladewnManagementException.throwExceptionWithId(
                    EntityType.SALLE,
                    ExceptionType.ENTITY_NOT_FOUND,
                    salleId.toString(),
                    "La salle n'existe pas."
            );
        }
        return salleOptional.get();
    }

    private Matiere verifyMatiereExists(Long matiereId) {
        Optional<Matiere> matiereOptional = matiereService.findMatiereById(matiereId);
        if (matiereOptional.isEmpty()) {
            log.error("La matière avec l'ID {} n'existe pas.", matiereId);
            throw KaladewnManagementException.throwExceptionWithId(
                    EntityType.MATIERE,
                    ExceptionType.ENTITY_NOT_FOUND,
                    matiereId.toString(),
                    "La matière n'existe pas."
            );
        }
        return matiereOptional.get();
    }

}
