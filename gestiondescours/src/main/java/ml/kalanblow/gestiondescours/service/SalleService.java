package ml.kalanblow.gestiondescours.service;

import java.util.List;
import java.util.Optional;

import ml.kalanblow.gestiondescours.model.Cours;
import ml.kalanblow.gestiondescours.model.Salle;

public interface SalleService {
    List<Salle> findAllSalleByCours(Cours cours);

    Optional<Salle> findSalleBy(long id);

    Salle createSalle (Salle salle);

    Salle updateSalle(long id, Salle salle);
}
