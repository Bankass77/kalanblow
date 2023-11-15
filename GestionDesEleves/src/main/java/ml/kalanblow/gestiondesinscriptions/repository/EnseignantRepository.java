package ml.kalanblow.gestiondesinscriptions.repository;

import ml.kalanblow.gestiondesinscriptions.model.Cours;
import ml.kalanblow.gestiondesinscriptions.model.Email;
import ml.kalanblow.gestiondesinscriptions.model.Enseignant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;


@Repository
public interface EnseignantRepository extends JpaRepository<Enseignant, Long> {

    @Override
/**
 * Recherche un enseignant par son identifiant unique.
 *
 * @param aLong L'identifiant unique de l'enseignant à rechercher.
 * @return Une instance facultative (Optional) de l'enseignant trouvé, ou une instance vide si aucun enseignant correspondant n'est trouvé.
 */
    Optional<Enseignant> findById(Long aLong);

    /**
     * Compte le nombre d'enseignants distincts associés à un cours d'enseignement spécifique.
     *
     * @param coursDEnseignement Le cours d'enseignement pour lequel compter les enseignants.
     * @return Une instance facultative (Optional) du nombre d'enseignants distincts associés au cours d'enseignement, ou une instance vide si aucun enseignant n'est associé au cours.
     */
    Optional<Long> countDistinctByCoursDEnseignements(Cours coursDEnseignement);

    /**
     * Recherche des enseignants par adresse e-mail en utilisant une correspondance partielle.
     *
     * @param email L'adresse e-mail à rechercher partiellement.
     * @return Une instance facultative (Optional) des enseignants trouvés, ou une instance vide si aucun enseignant correspondant n'est trouvé.
     */
    Optional<Enseignant> findEnseignantByEmail(Email email);

    /**
     * Obtient un enseignant en fonction du cours d'enseignement spécifié.
     *
     * @param coursDEnseignement Le cours d'enseignement pour lequel rechercher l'enseignant.
     * @return Une instance facultative (Optional) de l'enseignant trouvé, ou une instance vide si aucun enseignant n'est associé au cours d'enseignement.
     */
    Optional<Enseignant> getEnseignantByCoursDEnseignements(Cours coursDEnseignement);

    /**
     * Obtient un enseignant en fonction de la plage de dates de création.
     *
     * @param debut La date de début de la plage de dates de création.
     * @param fin   La date de fin de la plage de dates de création.
     * @return Une instance facultative (Optional) de l'enseignant trouvé, ou une instance vide si aucun enseignant correspondant n'est trouvé.
     */
    Optional<Enseignant> getEnseignantByCreatedDateIsBetween(LocalDate debut, LocalDate fin);

    /**
     * Obtient un enseignant en fonction du cours d'enseignement et des jours disponibles spécifiés.
     *
     * @param coursDEnseignement Le cours d'enseignement pour lequel rechercher l'enseignant.
     * @param enseignant         L'enseignant dont les jours disponibles doivent correspondre.
     * @return Une instance facultative (Optional) de l'enseignant trouvé, ou une instance vide si aucun enseignant correspondant n'est trouvé.
     */
    Optional<Enseignant> getEnseignantByCoursDEnseignementsAndJoursDisponibles(Cours coursDEnseignement, Enseignant enseignant);


    /**
     * Recherche un enseignant en fonction du cours d'enseignement, de l'enseignant, de l'heure de début de disponibilité,
     * de l'heure de fin de disponibilité et du jour disponible spécifiés.
     *
     * @param enseignant              L'enseignant pour lequel rechercher.
     * @param heureDebutDisponibilite L'heure de début de disponibilité à rechercher.
     * @param HeureFinDisponibilite   L'heure de fin de disponibilité à rechercher.
     * @return Une instance facultative (Optional) de l'enseignant trouvé, ou une instance vide si aucun enseignant correspondant n'est trouvé.
     */
    Optional<Enseignant> getEnseignantByCoursDEnseignementsAndHeureDebutDisponibiliteAndAndHeureFinDisponibilite(
            Enseignant enseignant, LocalTime heureDebutDisponibilite,
            LocalTime HeureFinDisponibilite);


}
