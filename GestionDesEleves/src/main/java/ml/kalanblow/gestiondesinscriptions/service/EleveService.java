package ml.kalanblow.gestiondesinscriptions.service;


import ml.kalanblow.gestiondesinscriptions.model.AbsenceEleve;
import ml.kalanblow.gestiondesinscriptions.model.Eleve;
import ml.kalanblow.gestiondesinscriptions.model.Email;
import ml.kalanblow.gestiondesinscriptions.model.UserName;
import ml.kalanblow.gestiondesinscriptions.request.CreateEleveParameters;
import ml.kalanblow.gestiondesinscriptions.request.EditEleveParameters;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EleveService {


    /**
     * @param email
     * @return
     */
    boolean verifierExistenceEmail(Email email);

    /**
     * @param email
     * @return
     */
    Optional<Eleve> chercherParEmail(String email);


    /**
     * @param pageable
     * @return
     */
    Page<Eleve> obtenirListeElevePage(Pageable pageable);

    // tag::editEleve[]
    Eleve mettreAjourUtilisateur(Long userId, EditEleveParameters parameters);
    // end::editEleve[]

    /**
     * @param userId
     * @return
     */
    Optional<Eleve> obtenirEleveParSonId(Long userId);

    Optional<Eleve> chercherParSonNumeroIne(String ineNumber);

    /**
     * @param userId
     */
    void supprimerEleveParSonId(Long userId);

    long countEleves();

    void deleteAllEleves();

    /**
     * Récupère un élève en fonction de son numéro INE.
     *
     * @param ineNumber Le numéro INE de l'élève.
     * @return Une instance d'Élève enveloppée dans un Optional, ou Optional.empty() si aucun élève correspondant n'est trouvé.
     */
    Optional<Eleve> getEleveByIneNumber(String ineNumber);

    Eleve CreationUtilisateur(CreateEleveParameters createEleveParameters);

    List<Eleve> recupererEleveParPrenomEtNom(String  prenom, String nomDeFamille);

    /**
     * @param userId
     */
    void supprimerUtilisateurParId(Long userId);

    Optional<Eleve> recupererEleveParTelephone( String telephone);


    /**
     * Recherche un élève distinct par absence et nom d'utilisateur.
     *
     * @param absenceEleve L'absence de l'élève à rechercher.
     * @param userName Le nom d'utilisateur à rechercher.
     * @return Un objet Optional contenant l'élève distinct correspondant à l'absence et au nom d'utilisateur (s'il existe).
     */
    Optional<Eleve> findDistinctByAbsencesAndUserName(AbsenceEleve absenceEleve, UserName userName);

    /**
     * Recherche un élève dont la date de naissance est similaire à la date spécifiée et dont le numéro INE contient la chaîne spécifiée (insensible à la casse).
     *
     * @param dateDeNaissance La date de naissance à rechercher.
     * @param numeroIne La chaîne de numéro INE à rechercher.
     * @return Un objet Optional contenant l'élève correspondant aux critères de recherche (s'il existe).
     */
    Optional<Eleve> searchAllByDateDeNaissanceIsLikeAndIneNumberContainsIgnoreCase(LocalDate dateDeNaissance, String numeroIne);

    /**
     * Recherche un élève par date de création se situant entre les dates spécifiées.
     *
     * @param debut La date de début de la période de création.
     * @param fin La date de fin de la période de création.
     * @return Un objet Optional contenant l'élève correspondant à la période de création (s'il existe).
     */
    Optional<Optional> findEleveByCreatedDateBetween( LocalDate debut, LocalDate fin);


    /**
     * Compte le nombre d'élèves par absence et date de création.
     *
     * @param absenceEleve L'absence de l'élève à prendre en compte.
     * @param dateDeCreation La date de création à prendre en compte.
     * @return Un objet Optional contenant le nombre d'élèves correspondant aux critères de recherche (s'il existe).
     */
    Optional<Eleve> countEleveByAbsencesAndCreatedDate( AbsenceEleve absenceEleve, LocalDate dateDeCreation);

    /**
     * Recherche un élève distinct par absence.
     *
     * @param absenceEleve L'absence de l'élève à rechercher.
     * @return Un objet Optional contenant l'élève distinct correspondant à l'absence (s'il existe).
     */
    Optional<Eleve> findDistinctByAbsences( AbsenceEleve absenceEleve);

}
