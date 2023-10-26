package ml.kalanblow.gestiondesinscriptions.service;

import ml.kalanblow.gestiondesinscriptions.model.Periode;
import ml.kalanblow.gestiondesinscriptions.request.CreatePeriodeParameters;
import ml.kalanblow.gestiondesinscriptions.request.EditPeriodeParameters;

import java.util.Optional;

public interface PeriodeService {

    /**
     * Supprime une année scolaire en fonction de l'année ou de l'identifiant spécifié.
     *
     * @param anneeScolaire L'année scolaire à supprimer.
     * @param id L'identifiant de l'année scolaire à supprimer.
     * @return Une instance facultative (Optional) de l'année scolaire supprimée, ou une instance vide si aucune année scolaire correspondante n'est trouvée et supprimée.
     */
    Optional<Periode> deleteAnneeScolaireByAnneeOrId(Periode anneeScolaire, int id);
    /**
     * Crée une nouvelle période en utilisant les paramètres spécifiés.
     *
     * @param createPeriodeParameters Les paramètres pour créer une période.
     * @return Une option contenant la nouvelle période si la création réussit, sinon Option vide.
     */
    Optional<Periode> createPeriode(CreatePeriodeParameters createPeriodeParameters);

    /**
     * Modifie une période existante identifiée par l'ID spécifié en utilisant les paramètres d'édition spécifiés.
     *
     * @param id L'identifiant de la période à éditer.
     * @param editPeriodeParameters Les paramètres pour effectuer la modification.
     * @return Une option contenant la période modifiée si l'édition réussit, sinon Option vide.
     */
    Optional<Periode> doEditPeriode(Long id, EditPeriodeParameters editPeriodeParameters);

    Optional<Periode>findAnneeScolairesByAnnee(int annee);



}
