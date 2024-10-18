package ml.kalanblow.gestiondescours.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ml.kalanblow.gestiondescours.model.Cours;
import ml.kalanblow.gestiondescours.model.Salle;

@Repository
public interface CoursRepository extends JpaRepository<Cours, Long> {
    Optional<Cours> findCoursByIntitule(String intitule);

    Optional<Cours> findCoursBySalle(Salle sale);

    List<Cours> findAllByDateDebutAndDateFin(LocalDateTime dateDebut, LocalDateTime dateFin);

    Optional<Cours> findCoursByEnseignantResponsable(Cours cours);

    List<Cours> findAllCoursBySalle(Salle salle);
}
