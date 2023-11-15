package ml.kalanblow.gestiondesinscriptions.service;

import ml.kalanblow.gestiondesinscriptions.model.Vacances;
import ml.kalanblow.gestiondesinscriptions.request.CreateVacancesParameters;
import ml.kalanblow.gestiondesinscriptions.request.EditVacancesParameters;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

public interface VacancesService {

    /**
     * Recherche une période de vacances distincte par sa date de début et de fin.
     *
     * @param dateDebut La date de début de la période de vacances à rechercher.
     * @param dateFin La date de fin de la période de vacances à rechercher.
     * @return Une instance optionnelle contenant la période de vacances correspondant aux dates spécifiées, si elle existe.
     */
    Optional<Vacances> findDistinctByDateDebutAndDateFin(LocalDate dateDebut, LocalDate dateFin);

    /**
     * Crée une nouvelle période de vacances en utilisant les paramètres spécifiés.
     *
     * @param createVacancesParameters Les paramètres pour créer une période de vacances.
     * @return Une option contenant la nouvelle période de vacances si la création réussit, sinon Option vide.
     */
    Optional<Vacances> createVacances(CreateVacancesParameters createVacancesParameters);

    /**
     * Modifie une période de vacances existante identifiée par l'ID spécifié en utilisant les paramètres d'édition spécifiés.
     *
     * @param id L'identifiant de la période de vacances à éditer.
     * @param editVacancesParameters Les paramètres pour effectuer la modification.
     * @return Une option contenant la période de vacances modifiée si l'édition réussit, sinon Option vide.
     */
    Optional<Vacances> doEditVacances(Long id, EditVacancesParameters editVacancesParameters);


    Set<Vacances> getAllVacances();

    Optional<Vacances> getVacancesById(Long id);

    void deleteVacances(Long id);
}
