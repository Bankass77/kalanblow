package ml.kalanblow.gestiondesinscriptions.service;


import ml.kalanblow.gestiondesinscriptions.model.*;
import ml.kalanblow.gestiondesinscriptions.request.CreateEleveParameters;
import ml.kalanblow.gestiondesinscriptions.request.EditEleveParameters;
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
     * Met à jour les informations d'un élève.
     *
     * @param userId     L'identifiant de l'élève à mettre à jour.
     * @param parameters Les paramètres de mise à jour.
     * @return L'élève mis à jour.
     */
    Eleve mettreAjourUtilisateur(Long userId, EditEleveParameters parameters);

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
     * Crée un nouvel élève en utilisant les paramètres spécifiés.
     *
     * @param createEleveParameters Les paramètres pour créer l'élève.
     * @return L'élève créé.
     */
    Eleve ajouterUnEleve(CreateEleveParameters createEleveParameters);

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
     * Recherche un élève distinct par absence et nom d'utilisateur.
     *
     * @param absenceEleve L'absence de l'élève à rechercher.
     * @param userName     Le nom d'utilisateur à rechercher.
     * @return Un objet Optional contenant l'élève distinct correspondant à l'absence et au nom d'utilisateur (s'il existe).
     */
    Optional<Eleve> findDistinctByAbsencesAndUserName(Absence absenceEleve, UserName userName);

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


    /**
     * Compte le nombre d'élèves par absence et date de création.
     *
     * @param absenceEleve   L'absence de l'élève à prendre en compte.
     * @param dateDeCreation La date de création à prendre en compte.
     * @return Un objet Optional contenant le nombre d'élèves correspondant aux critères de recherche (s'il existe).
     */
    Optional<Eleve> countEleveByAbsencesAndCreatedDate(Absence absenceEleve, LocalDate dateDeCreation);

    /**
     * Recherche un élève distinct par absence.
     *
     * @param absenceEleve L'absence de l'élève à rechercher.
     * @return Un objet Optional contenant l'élève distinct correspondant à l'absence (s'il existe).
     */
    Optional<Eleve> findDistinctByAbsences(Absence absenceEleve);

    /**
     * Recherche une liste distincte d'élèves en fonction de la salle de classe et de l'établissement scolaire fournis.
     *
     * @param salleDeClasse La salle de classe pour laquelle rechercher les élèves.
     * @return Une liste facultative (Optional) d'élèves. Elle peut être vide si aucun élève ne correspond aux critères de recherche.
     */
    Optional<List<Eleve>> recupererElevesParClasse (Salle salleDeClasse);

    Page<Eleve> recupererLaListeDesElevesParClasseEtDate(Salle salleDeClasse, LocalDate dateActuelle, Pageable pageable);
}
