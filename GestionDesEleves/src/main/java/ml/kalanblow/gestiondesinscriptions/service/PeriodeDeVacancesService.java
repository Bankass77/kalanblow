package ml.kalanblow.gestiondesinscriptions.service;

import ml.kalanblow.gestiondesinscriptions.model.Vacances;

import java.time.LocalDate;
import java.util.Optional;

public interface PeriodeDeVacancesService {

    /**
     * Recherche une période de vacances distincte par sa date de début et de fin.
     *
     * @param dateDebut La date de début de la période de vacances à rechercher.
     * @param dateFin La date de fin de la période de vacances à rechercher.
     * @return Une instance optionnelle contenant la période de vacances correspondant aux dates spécifiées, si elle existe.
     */
    Optional<Vacances> findDistinctByDateDebutAndDateFin(LocalDate dateDebut, LocalDate dateFin);

}
