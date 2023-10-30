package ml.kalanblow.gestiondesinscriptions.service;

import ml.kalanblow.gestiondesinscriptions.enums.TypeDeClasse;
import ml.kalanblow.gestiondesinscriptions.model.Cours;
import ml.kalanblow.gestiondesinscriptions.model.Salle;

import java.util.Optional;

public interface SalleDeClasseService {
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
}
