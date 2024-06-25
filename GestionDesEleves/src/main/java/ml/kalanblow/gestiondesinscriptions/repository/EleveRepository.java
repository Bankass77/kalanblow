package ml.kalanblow.gestiondesinscriptions.repository;


import ml.kalanblow.gestiondesinscriptions.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface EleveRepository extends UserBaseRepository<Eleve>, JpaSpecificationExecutor<Eleve> {

    /**
     * Recherche un élève par numéro de téléphone.
     *
     * @param phoneNumber Le numéro de téléphone à rechercher.
     * @return Un objet Optional contenant l'élève correspondant au numéro de téléphone (s'il existe).
     */
    Optional<Eleve> findByPhoneNumber(PhoneNumber phoneNumber);

    /**
     * Supprime un élève par son identifiant.
     *
     * @param id L'identifiant de l'élève à supprimer.
     * @return Un objet Optional contenant l'élève supprimé (s'il existe).
     */
    Optional<Eleve> deleteEleveById(Long id);

    /**
     * Recherche un élève par adresse e-mail.
     *
     * @param email L'adresse e-mail à rechercher.
     * @return Un objet Optional contenant l'élève correspondant à l'adresse e-mail (s'il existe).
     */
    Optional<Eleve>  findElevesByEmail(Email email);

    /**
     * Recherche un élève distinct par absence et nom d'utilisateur.
     *
     * @param absenceEleve L'absence de l'élève à rechercher.
     * @param userName Le nom d'utilisateur à rechercher.
     * @return Un objet Optional contenant l'élève distinct correspondant à l'absence et au nom d'utilisateur (s'il existe).
     */
    Optional<Eleve> findDistinctByAbsencesAndUserName(Absence absenceEleve, UserName userName);

    /**
     * Recherche un élève dont la date de naissance est similaire à la date spécifiée et dont le numéro INE contient la chaîne spécifiée (insensible à la casse).
     *
     * @param dateDeNaissance La date de naissance à rechercher.
     * @param ineNumber La chaîne de numéro INE à rechercher.
     * @return Un objet Optional contenant l'élève correspondant aux critères de recherche (s'il existe).
     */
    Optional<Eleve> findByDateDeNaissanceAndIneNumberContainingIgnoreCase(LocalDate dateDeNaissance, String ineNumber);

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
    Optional<Eleve> countEleveByAbsencesAndCreatedDate(Absence absenceEleve, LocalDate dateDeCreation);

    /**
     * Recherche un élève distinct par absence.
     *
     * @param absenceEleve L'absence de l'élève à rechercher.
     * @return Un objet Optional contenant l'élève distinct correspondant à l'absence (s'il existe).
     */
    Optional<Eleve> findDistinctByAbsences( Absence absenceEleve);

    /**
     * Récupère une liste facultative (Optional) de tous les élèves.
     *
     * @return Une liste facultative (Optional) d'élèves. Elle peut être vide si aucun élève n'est trouvé.
     */
    Page<Eleve> findAll(Pageable pageable);

    /**
     * Recherche une liste distincte d'élèves en fonction de la salle de classe et de l'établissement scolaire fournis.
     *
     * @param salleDeClasse La salle de classe pour laquelle rechercher les élèves.
     * @return Une liste facultative (Optional) d'élèves. Elle peut être vide si aucun élève ne correspond aux critères de recherche.
     */
    Optional<List<Eleve>> findDistinctBySalle_Etablissement (Salle salleDeClasse);


    /**
     * Récupère un élève en fonction de son numéro INE.
     *
     * @param ineNumber Le numéro INE de l'élève.
     * @return Une instance d'Élève enveloppée dans un Optional, ou Optional.empty() si aucun élève correspondant n'est trouvé.
     */
    Optional<Eleve> findByIneNumber(String ineNumber);


    /**
     * Récupère une liste d'élèves par leur prénom et nom de famille.
     *
     * @param prenom       Le prénom de l'élève.
     * @param nomDeFamille Le nom de famille de l'élève.
     * @return Une liste d'élèves correspondant aux critères de recherche.
     */
    Optional<User> findByUserName_PrenomAndUserName_NomDeFamille(String prenom, String nomDeFamille);

    @Query("SELECT e FROM Eleve e WHERE e.salle = :salle AND e.dateInscription <= :dateActuelle")
    Page<Eleve> findAllBySalleAndDate(@Param("salle") Salle salle, @Param("dateActuelle") LocalDate dateActuelle, Pageable pageable);

}
