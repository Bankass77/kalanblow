package ml.kalanblow.gestiondescours.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import ml.kalanblow.gestiondescours.model.Cours;
import ml.kalanblow.gestiondescours.model.Salle;

public interface CoursService {

    Optional<Cours> findCoursByIntitule(String intitule);

    Optional<Cours> findCoursBySalle(Salle sale);

    List<Cours> findAllByDateDebutAndDateFin(LocalDateTime dateDebut, LocalDateTime dateFin);

    Optional<Cours> findCoursByEnseignantResponsable(Cours cours);

    List<Cours> findAllCoursBySalle(Salle salle);

    Cours createCours (Cours cours);

    Cours updateCours(Long id, Cours cours);

    Optional<Cours> findCoursByCoursId(Long id);
}
