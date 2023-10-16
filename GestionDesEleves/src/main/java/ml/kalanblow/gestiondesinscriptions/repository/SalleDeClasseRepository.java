package ml.kalanblow.gestiondesinscriptions.repository;

import ml.kalanblow.gestiondesinscriptions.enums.TypeDeClasse;
import ml.kalanblow.gestiondesinscriptions.model.CoursDEnseignement;
import ml.kalanblow.gestiondesinscriptions.model.SalleDeClasse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface SalleDeClasseRepository extends JpaRepository<SalleDeClasse,Long> {



    /**
     * Recherche une salle de classe distincte en fonction du cours d'enseignement et du nom de la salle ou du type de classe spécifiés.
     *
     * @param coursDEnseignement Le cours d'enseignement pour lequel rechercher la salle de classe.
     * @param salleDeClasse La salle de classe ou le type de classe pour lequel rechercher.
     * @return Une instance facultative (Optional) de la salle de classe trouvée, ou une instance vide si aucune salle de classe correspondante n'est trouvée.
     */
    Optional<SalleDeClasse> findDistinctByCoursPlanifiesAndAndNomDeLaSalleOrTypeDeClasse(CoursDEnseignement coursDEnseignement, SalleDeClasse salleDeClasse, TypeDeClasse typeDeClasse);


    /**
     * Compte le nombre de salles de classe en fonction du cours d'enseignement et du nom de la salle spécifiés.
     *
     * @param coursDEnseignement Le cours d'enseignement pour lequel compter les salles de classe.
     * @param salleDeClasse Le nom de la salle de classe à rechercher.
     * @return Une instance facultative (Optional) contenant le nombre de salles de classe correspondantes, ou une instance vide si aucune salle de classe ne correspond.
     */
    Optional<SalleDeClasse> countSalleDeClasseByCoursPlanifiesAndNomDeLaSalle(CoursDEnseignement coursDEnseignement, SalleDeClasse salleDeClasse);


    /**
     * Obtient une salle de classe en fonction du cours d'enseignement spécifié.
     *
     * @param coursDEnseignement Le cours d'enseignement pour lequel rechercher la salle de classe.
     * @return Une instance facultative (Optional) de la salle de classe trouvée, ou une instance vide si aucune salle de classe correspondante n'est trouvée.
     */
    Optional<SalleDeClasse>  getSalleDeClasseByCoursPlanifies( CoursDEnseignement coursDEnseignement);
}
