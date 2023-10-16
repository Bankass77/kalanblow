package ml.kalanblow.gestiondesinscriptions.repository;

import ml.kalanblow.gestiondesinscriptions.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CoursDEnseignementRepository extends JpaRepository<CoursDEnseignement, Long> {

    /**
     * Recherche un cours d'enseignement distinct en fonction de l'enseignant et de l'année scolaire spécifiés.
     *
     * @param enseignant    L'enseignant pour lequel rechercher le cours d'enseignement.
     * @param anneeScolaire L'année scolaire pour laquelle rechercher le cours d'enseignement.
     * @return Une instance facultative (Optional) du cours d'enseignement trouvé, ou une instance vide si aucun cours correspondant n'est trouvé.
     */
    Optional<CoursDEnseignement> findDistinctByEnseignantAndAnneeScolaire(Enseignant enseignant, AnneeScolaire anneeScolaire);

    /**
     * Recherche un cours d'enseignement distinct en fonction de l'horaire de classe, de la salle de classe et de la matière spécifiés.
     *
     * @param horaireClasse L'horaire de classe pour lequel rechercher le cours d'enseignement.
     * @param salleDeClasse La salle de classe pour laquelle rechercher le cours d'enseignement.
     * @param matiere       La matière pour laquelle rechercher le cours d'enseignement.
     * @return Une instance facultative (Optional) du cours d'enseignement trouvé, ou une instance vide si aucun cours correspondant n'est trouvé.
     */
    Optional<CoursDEnseignement> findDistinctByHoraireClassesAndAndSalleDeClasseAndMatiere(HoraireClasse horaireClasse, SalleDeClasse salleDeClasse, Matiere matiere);

    /**
     * Recherche un cours d'enseignement distinct en fonction de l'enseignant, de la salle de classe et de l'horaire de classe spécifiés.
     *
     * @param enseignant L'enseignant pour lequel rechercher le cours d'enseignement.
     * @param salleDeClasse La salle de classe pour laquelle rechercher le cours d'enseignement.
     * @param horaireClasse L'horaire de classe pour lequel rechercher le cours d'enseignement.
     * @return Une instance facultative (Optional) du cours d'enseignement trouvé, ou une instance vide si aucun cours correspondant n'est trouvé.
     */
    Optional<CoursDEnseignement> findDistinctByEnseignantOrSalleDeClasseAndAndHoraireClasses(Enseignant enseignant, SalleDeClasse salleDeClasse, HoraireClasse horaireClasse);
}
