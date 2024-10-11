package ml.kalanblow.gestiondesinscriptions.service;


import ml.kalanblow.gestiondesinscriptions.model.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EleveService {


    /**
     * Vérifie l'existence d'un élève avec l'adresse e-mail spécifiée.
     *
     * @param email L'adresse e-mail à vérifier.
     * @return Vrai si un élève avec cette adresse e-mail existe, sinon faux.
     */
    boolean verifierExistenceEmail(Email email);

    /**
     * Recherche un élève par son adresse e-mail.
     *
     * @param email L'adresse e-mail de l'élève à rechercher.
     * @return Une option contenant l'élève trouvé, ou une option vide si aucun élève correspondant n'est trouvé.
     */
    Optional<Eleve> chercherParEmail(String email);

    /**
     * Obtient une liste paginée d'élèves.
     *
     * @param pageable Les informations de pagination.
     * @return Une page d'élèves.
     */
    Page<Eleve> obtenirListeElevePage(Pageable pageable) ;


    /**
     * Obtient un élève par son identifiant.
     *
     * @param userId L'identifiant de l'élève.
     * @return Une option contenant l'élève trouvé, ou une option vide si aucun élève correspondant n'est trouvé.
     */
    Optional<Eleve> obtenirEleveParSonId(Long userId);

    /**
     * Cherche un élève par son numéro INE.
     *
     * @param ineNumber Le numéro INE de l'élève à rechercher.
     * @return Une option contenant l'élève trouvé, ou une option vide si aucun élève correspondant n'est trouvé.
     */
    Optional<Eleve> chercherParSonNumeroIne(String ineNumber);

    /**
     * Supprime un élève par son identifiant.
     *
     * @param userId L'identifiant de l'élève à supprimer.
     */
    void supprimerEleveParSonId(Long userId);


    /**
     * Compte le nombre total d'élèves.
     *
     * @return Le nombre total d'élèves.
     */
    long countEleves();

    /**
     * Supprime tous les élèves.
     */
    void deleteAllEleves();


    /**
     * Récupère une liste d'élèves par leur prénom et nom de famille.
     *
     * @param prenom       Le prénom de l'élève.
     * @param nomDeFamille Le nom de famille de l'élève.
     * @return Une liste d'élèves correspondant aux critères de recherche.
     */
    Optional<Eleve> recupererEleveParPrenomEtNom(String prenom, String nomDeFamille);

    /**
     * Supprime un utilisateur par son identifiant.
     *
     * @param userId L'identifiant de l'utilisateur à supprimer.
     */
    void supprimerUtilisateurParId(Long userId);

    /**
     * Recherche un élève par son numéro de téléphone.
     *
     * @param telephone Le numéro de téléphone de l'élève à rechercher.
     * @return Une option contenant l'élève trouvé, ou une option vide si aucun élève correspondant n'est trouvé.
     */
    Optional<Eleve> recupererEleveParTelephone(String telephone);


    /**
     * Recherche un élève dont la date de naissance est similaire à la date spécifiée et dont le numéro INE contient la chaîne spécifiée (insensible à la casse).
     *
     * @param dateDeNaissance La date de naissance à rechercher.
     * @param numeroIne       La chaîne de numéro INE à rechercher.
     * @return Un objet Optional contenant l'élève correspondant aux critères de recherche (s'il existe).
     */
    Optional<Eleve> searchAllByDateDeNaissanceIsLikeAndIneNumberContainsIgnoreCase(LocalDate dateDeNaissance, String numeroIne);

    /**
     * Recherche un élève par date de création se situant entre les dates spécifiées.
     *
     * @param debut La date de début de la période de création.
     * @param fin   La date de fin de la période de création.
     * @return Un objet Optional contenant l'élève correspondant à la période de création (s'il existe).
     */
    Optional<Optional> findEleveByCreatedDateBetween(LocalDate debut, LocalDate fin);



}
