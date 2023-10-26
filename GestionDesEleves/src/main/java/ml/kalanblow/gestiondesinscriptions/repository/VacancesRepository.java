package ml.kalanblow.gestiondesinscriptions.repository;

import ml.kalanblow.gestiondesinscriptions.model.Vacances;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;


@Repository
public interface VacancesRepository extends JpaRepository<Vacances, Long> {

    /**
     * Recherche une période de vacances distincte par sa date de début et de fin.
     *
     * @param dateDebut La date de début de la période de vacances à rechercher.
     * @param dateFin La date de fin de la période de vacances à rechercher.
     * @return Une instance optionnelle contenant la période de vacances correspondant aux dates spécifiées, si elle existe.
     */
    Optional<Vacances> findDistinctByDateDebutAndDateFin(LocalDate dateDebut, LocalDate dateFin);

}
