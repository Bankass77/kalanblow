package ml.kalanblow.gestiondesinscriptions.service;

import ml.kalanblow.gestiondesinscriptions.model.Email;
import ml.kalanblow.gestiondesinscriptions.model.Enseignant;
import ml.kalanblow.gestiondesinscriptions.model.Etablissement;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EnseignantService {

    // Créer un nouvel enseignant
    Enseignant createEnseignant(Enseignant enseignant);

    // Mettre à jour un enseignant existant
    Enseignant updateEnseignant(Long enseignantId, Enseignant enseignant);

    // Supprimer un enseignant par ID
    void deleteEnseignant(Long enseignantId);

    // Trouver par matricule
    Optional<Enseignant> findByLeMatricule(String leMatricule);

    // Trouver par établissement
    List<Enseignant> findByEtablissement(Etablissement etablissement);


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
    List<Enseignant>  getEnseignantByUserCreatedDateIsBetween(LocalDate debut, LocalDate fin);


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

    Set<DayOfWeek> getDisponibilitesParEnseignant(Enseignant enseignant);
}
