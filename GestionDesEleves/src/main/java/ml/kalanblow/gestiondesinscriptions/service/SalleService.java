package ml.kalanblow.gestiondesinscriptions.service;

import ml.kalanblow.gestiondesinscriptions.enums.TypeDeClasse;
import ml.kalanblow.gestiondesinscriptions.model.Cours;
import ml.kalanblow.gestiondesinscriptions.model.Salle;
import ml.kalanblow.gestiondesinscriptions.request.CreateSalleParameters;
import ml.kalanblow.gestiondesinscriptions.request.EditSalleParameters;

import java.util.Optional;
import java.util.Set;

public interface SalleService {
    /**
     * Recherche une salle de classe distincte en fonction du cours d'enseignement et du nom de la salle ou du type de classe spécifiés.
     *
     * @param coursDEnseignement Le cours d'enseignement pour lequel rechercher la salle de classe.
     * @param salleDeClasse La salle de classe ou le type de classe pour lequel rechercher.
     * @return Une instance facultative (Optional) de la salle de classe trouvée, ou une instance vide si aucune salle de classe correspondante n'est trouvée.
     */
    Optional<Salle> findDistinctByCoursPlanifiesAndAndNomDeLaSalleOrTypeDeClasse(Cours coursDEnseignement, Salle salleDeClasse, TypeDeClasse typeDeClasse);


    /**
     * Compte le nombre de salles de classe en fonction du cours d'enseignement et du nom de la salle spécifiés.
     *
     * @param coursDEnseignement Le cours d'enseignement pour lequel compter les salles de classe.
     * @param salleDeClasse Le nom de la salle de classe à rechercher.
     * @return Une instance facultative (Optional) contenant le nombre de salles de classe correspondantes, ou une instance vide si aucune salle de classe ne correspond.
     */
    Optional<Salle> countSalleDeClasseByCoursPlanifiesAndNomDeLaSalle(Cours coursDEnseignement, Salle salleDeClasse);


    /**
     * Obtient une salle de classe en fonction du cours d'enseignement spécifié.
     *
     * @param coursDEnseignement Le cours d'enseignement pour lequel rechercher la salle de classe.
     * @return Une instance facultative (Optional) de la salle de classe trouvée, ou une instance vide si aucune salle de classe correspondante n'est trouvée.
     */
    Optional<Salle>  getSalleDeClasseByCoursPlanifies(Cours coursDEnseignement);

    /**
     * Crée une nouvelle salle de classe en utilisant les paramètres spécifiés.
     *
     * @param createSalleParameters Les paramètres pour créer une salle de classe.
     * @return Une option contenant la nouvelle salle de classe si la création réussit, sinon Option vide.
     */
    Optional<Salle> createSalleDeClasse(CreateSalleParameters createSalleParameters);

    /**
     * Modifie une salle de classe existante identifiée par l'ID spécifié en utilisant les paramètres d'édition spécifiés.
     *
     * @param id L'identifiant de la salle de classe à éditer.
     * @param editSalleParameters Les paramètres pour effectuer la modification.
     * @return Une option contenant la salle de classe modifiée si l'édition réussit, sinon Option vide.
     */
    Optional<Salle> doEditSalleDeClasse(Long id, EditSalleParameters editSalleParameters);

    Set<Salle> getAllSalles();

    public void deleteSalleDeClasse(Long id);

    Optional<Salle> getTimeslotById(Long id);
}
