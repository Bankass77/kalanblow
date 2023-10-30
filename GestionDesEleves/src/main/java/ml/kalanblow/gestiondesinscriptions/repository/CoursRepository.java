package ml.kalanblow.gestiondesinscriptions.repository;

import ml.kalanblow.gestiondesinscriptions.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CoursRepository extends JpaRepository<Cours, Long> {

    /**
     * Recherche un cours d'enseignement distinct en fonction de l'enseignant et de l'année scolaire spécifiés.
     *
     * @param enseignant    L'enseignant pour lequel rechercher le cours d'enseignement.
     * @param anneeScolaire L'année scolaire pour laquelle rechercher le cours d'enseignement.
     * @return Une instance facultative (Optional) du cours d'enseignement trouvé, ou une instance vide si aucun cours correspondant n'est trouvé.
     */
    Optional<Cours> findDistinctByEnseignantAndAnneeScolaire(Enseignant enseignant, Periode anneeScolaire);

    /**
     * Recherche un cours d'enseignement distinct en fonction de l'horaire de classe, de la salle de classe et de la matière spécifiés.
     *
     * @param horaireClasse L'horaire de classe pour lequel rechercher le cours d'enseignement.
     * @param salleDeClasse La salle de classe pour laquelle rechercher le cours d'enseignement.
     * @param matiere       La matière pour laquelle rechercher le cours d'enseignement.
     * @return Une instance facultative (Optional) du cours d'enseignement trouvé, ou une instance vide si aucun cours correspondant n'est trouvé.
     */
    Optional<Cours> findDistinctByHorairesAndAndSalleAndMatiere(Horaire horaireClasse, Salle salleDeClasse, Matiere matiere);

    /**
     * Recherche un cours d'enseignement distinct en fonction de l'enseignant, de la salle de classe et de l'horaire de classe spécifiés.
     *
     * @param enseignant L'enseignant pour lequel rechercher le cours d'enseignement.
     * @param salleDeClasse La salle de classe pour laquelle rechercher le cours d'enseignement.
     * @param horaireClasse L'horaire de classe pour lequel rechercher le cours d'enseignement.
     * @return Une instance facultative (Optional) du cours d'enseignement trouvé, ou une instance vide si aucun cours correspondant n'est trouvé.
     */
    Optional<Cours> findDistinctByEnseignantOrSalleAndAndHoraires(Enseignant enseignant, Salle salleDeClasse, Horaire horaireClasse);


    /**
     * Vérifie si un cours d'enseignement existe pour une salle de classe et des horaires de classe donnés.
     *
     * @param salleDeClasse   La salle de classe pour laquelle vérifier l'existence d'un cours.
     * @param horaireClasse   Les horaires de classe pour lesquels vérifier l'existence d'un cours.
     * @return true si un cours d'enseignement existe pour la salle de classe et les horaires de classe donnés, sinon false.
     */
    Optional<Cours> existsCoursDEnseignementBySalleAndHoraires(Salle salleDeClasse, Horaire horaireClasse);

    Optional<Cours> existsCoursDEnseignementByEnseignantAndHoraires(Enseignant enseignant, Horaire horaireClasse);

    Optional<Cours> findDistinctByNomDuCours(String nomDuCours);
}
