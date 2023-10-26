package ml.kalanblow.gestiondesinscriptions.service;

import ml.kalanblow.gestiondesinscriptions.model.*;
import ml.kalanblow.gestiondesinscriptions.request.CreateCoursDEnseignementParameters;
import ml.kalanblow.gestiondesinscriptions.request.EditCoursDEnseignementParameters;

import java.util.Optional;

public interface CoursDEnseignementService {
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
    Optional<Cours> findDistinctByHoraireClassesAndAndSalleDeClasseAndMatiere(Horaire horaireClasse, Salle salleDeClasse, Matiere matiere);

    /**
     * Recherche un cours d'enseignement distinct en fonction de l'enseignant, de la salle de classe et de l'horaire de classe spécifiés.
     *
     * @param enseignant    L'enseignant pour lequel rechercher le cours d'enseignement.
     * @param salleDeClasse La salle de classe pour laquelle rechercher le cours d'enseignement.
     * @param horaireClasse L'horaire de classe pour lequel rechercher le cours d'enseignement.
     * @return Une instance facultative (Optional) du cours d'enseignement trouvé, ou une instance vide si aucun cours correspondant n'est trouvé.
     */
    Optional<Cours> findDistinctByEnseignantOrSalleDeClasseAndAndHoraireClasses(Enseignant enseignant, Salle salleDeClasse, Horaire horaireClasse);

    /**
     * Crée un nouveau cours d'enseignement et le stocke dans le système.
     *
     * @param nouveauCoursDEnseignement Le cours d'enseignement à créer.
     * @return Un objet Optional contenant le cours d'enseignement créé s'il a été ajouté
     * avec succès, ou Optional.empty() si le cours n'a pas pu être ajouté.
     */
    Optional<Cours> creerCoursDEnseignement(CreateCoursDEnseignementParameters nouveauCoursDEnseignement);

    /**
     * Édite un cours d'enseignement existant avec les paramètres spécifiés.
     *
     * @param editCoursDEnseignementParameters Les paramètres de l'édition du cours d'enseignement.
     * @param id L'identifiant du cours.
     * @return Un objet Optional contenant le cours d'enseignement édité si l'édition a été effectuée
     * avec succès, ou Optional.empty() si le cours n'a pas pu être édité.
     */
    Optional<Cours> editerCoursDEnseignement(Long id, EditCoursDEnseignementParameters editCoursDEnseignementParameters);
}
