package ml.kalanblow.gestiondesinscriptions.service;

import ml.kalanblow.gestiondesinscriptions.model.Email;
import ml.kalanblow.gestiondesinscriptions.model.Enseignant;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.Set;

public interface EnseignantService {


/**
 * Recherche un enseignant par son identifiant unique.
 *
 * @param aLong L'identifiant unique de l'enseignant à rechercher.
 * @return Une instance facultative (Optional) de l'enseignant trouvé, ou une instance vide si aucun enseignant correspondant n'est trouvé.
 */
    Optional<Enseignant> findById(Long aLong);


    /**
     * Recherche des enseignants par adresse e-mail en utilisant une correspondance partielle.
     *
     * @param email L'adresse e-mail à rechercher partiellement.
     * @return Une instance facultative (Optional) des enseignants trouvés, ou une instance vide si aucun enseignant correspondant n'est trouvé.
     */
    Optional<Enseignant> searchAllByEmailIsLike(Email email);


    /**
     * Obtient un enseignant en fonction de la plage de dates de création.
     *
     * @param debut La date de début de la plage de dates de création.
     * @param fin   La date de fin de la plage de dates de création.
     * @return Une instance facultative (Optional) de l'enseignant trouvé, ou une instance vide si aucun enseignant correspondant n'est trouvé.
     */
    Optional<Enseignant> getEnseignantByCreatedDateIsBetween(LocalDate debut, LocalDate fin);


    /**
     * Recherche un enseignant en fonction du cours d'enseignement, de l'enseignant, de l'heure de début de disponibilité,
     * de l'heure de fin de disponibilité et du jour disponible spécifiés.
     *
     * @param enseignant              L'enseignant pour lequel rechercher.
     * @param heureDebutDisponibilite L'heure de début de disponibilité à rechercher.
     * @param heureFinDisponibilite   L'heure de fin de disponibilité à rechercher.
     * @return Une instance facultative (Optional) de l'enseignant trouvé, ou une instance vide si aucun enseignant correspondant n'est trouvé.
     */
    Optional<Enseignant> getEnseignantByCoursDEnseignementsAndHeureDebutDisponibiliteAndAndHeureFinDisponibilite(Enseignant enseignant, LocalTime heureDebutDisponibilite, LocalTime heureFinDisponibilite);


    Set<Enseignant> getAllEnseignants();

    void deleteById(Long id);
}
