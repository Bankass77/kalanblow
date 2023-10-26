package ml.kalanblow.gestiondesinscriptions.service.impl;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import ml.kalanblow.gestiondesinscriptions.enums.UserRole;
import ml.kalanblow.gestiondesinscriptions.exception.EntityType;
import ml.kalanblow.gestiondesinscriptions.exception.ExceptionType;
import ml.kalanblow.gestiondesinscriptions.exception.KaladewnManagementException;
import ml.kalanblow.gestiondesinscriptions.model.*;
import ml.kalanblow.gestiondesinscriptions.repository.EleveRepository;
import ml.kalanblow.gestiondesinscriptions.request.CreateEleveParameters;
import ml.kalanblow.gestiondesinscriptions.request.EditEleveParameters;
import ml.kalanblow.gestiondesinscriptions.service.EleveService;
import ml.kalanblow.gestiondesinscriptions.service.EtablissementService;
import ml.kalanblow.gestiondesinscriptions.service.ParentService;
import ml.kalanblow.gestiondesinscriptions.util.CalculateUserAge;
import ml.kalanblow.gestiondesinscriptions.util.KaladewnUtility;
import ml.kalanblow.gestiondesinscriptions.validation.ValidationGroupOne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Service pour la gestion des élèves.
 */
@Service
@Slf4j
@Transactional
public class EleveServiceImpl implements EleveService {

    private final EleveRepository eleveRepository;

    private final ParentService parentService;

    private final EtablissementService etablissementService;


    @Autowired
    public EleveServiceImpl(EleveRepository eleveRepository, ParentService parentService, EtablissementService etablissementService) {

        this.eleveRepository = eleveRepository;
        this.parentService = parentService;
        this.etablissementService = etablissementService;


    }

    /**
     * Vérifie l'existence d'un email dans la base de données.
     *
     * @param email L'adresse email à vérifier.
     * @return true si l'email existe, false sinon.
     */
    @Override
    public boolean verifierExistenceEmail(Email email) {

        log.debug("verifierExistenceEmail {}", email);

        return    eleveRepository.findElevesByEmail(email).isPresent();

    }

    /**
     * Cherche un élève par son adresse email.
     *
     * @param email L'adresse email de l'élève à rechercher.
     * @return Une instance d'Élève enveloppée dans un Optional, ou Optional.empty() si aucun élève correspondant n'est trouvé.
     */
    @Override
    public Optional<Eleve> chercherParEmail(String email) {
        log.debug("chercherParEmail {}", email);

        if (email != null) {

            return eleveRepository.findElevesByEmail(new Email(email));

        }

        return null;
    }

    /**
     * Obtient une page de la liste des élèves.
     *
     * @param pageable Les informations de pagination.
     * @return Une page contenant des élèves.
     */
    @Override
    public Page<Eleve> obtenirListeElevePage(Pageable pageable)  {
        log.debug("obtenirListeElevePage {}");
        return eleveRepository.findAll(pageable);
    }



    /**
     * @param userId     L'identifiant de l'élève à mettre à jour.
     * @param parameters Les paramètres de mise à jour.
     * @return Une instance d'Élève représentant les nouvelles informations de l'élève.
     */
    @Override
    public Eleve mettreAjourUtilisateur(Long userId, EditEleveParameters parameters) {

        Optional<Eleve> eleve = eleveRepository.findById(userId);

        if (parameters.getVersion() != eleve.get().getVersion()) {
            throw new ObjectOptimisticLockingFailureException(Eleve.class, eleve.get().getId());

        }
        parameters = new EditEleveParameters();
        parameters.setVersion(eleve.get().getVersion());
        parameters.setUserName(eleve.get().getUserName());
        parameters.setGender(eleve.get().getGender());
        parameters.setMaritalStatus(eleve.get().getMaritalStatus());
        parameters.setEmail(eleve.get().getEmail());
        parameters.setPassword(eleve.get().getPassword());
        parameters.setPhoneNumber(eleve.get().getPhoneNumber());
        parameters.setAddress(eleve.get().getAddress());
        parameters.setCreatedDate(eleve.get().getCreatedDate());
        parameters.setModifyDate(eleve.get().getLastModifiedDate());
        parameters.setDateDeNaissance(eleve.get().getDateDeNaissance());
        parameters.setAge(eleve.get().getAge());
        parameters.setStudentIneNumber(eleve.get().getIneNumber());
        parameters.setPere(eleve.get().getPere());
        parameters.setMere(eleve.get().getMere());
        parameters.setPhoneNumber(eleve.get().getPhoneNumber());
        parameters.setEtablissement(eleve.get().getEtablissement());
        parameters.setAbsences(eleve.get().getAbsences());
        parameters.updateStudent(eleve.get());
        log.debug("Metter à d'un Eleve {} ({})", parameters.getUserName().getFullName());
        return eleve.get();
    }

    /**
     * Obtient un élève par son ID.
     *
     * @param userId L'ID de l'élève à rechercher.
     * @return Une instance d'Élève enveloppée dans un Optional, ou Optional.empty() si aucun élève correspondant n'est trouvé.
     */
    @Override
    public Optional<Eleve> obtenirEleveParSonId(Long userId) {
        log.debug("obtenirEleveParSonId {}", userId);

       Optional<Eleve> optionalEleve= eleveRepository.findById(userId);
        return optionalEleve;
    }

    /**
     * Récupère un élève en fonction de son numéro INE.
     *
     * @param ineNumber Le numéro INE de l'élève.
     * @return Une instance d'Élève enveloppée dans un Optional, ou Optional.empty() si aucun élève correspondant n'est trouvé.
     */
    @Override
    public Optional<Eleve> chercherParSonNumeroIne(String ineNumber) {

        return  eleveRepository.findByIneNumber(ineNumber);
    }

    /**
     * Supprime un élève par son ID.
     *
     * @param userId L'ID de l'élève à supprimer.
     */
    @Override
    public void supprimerEleveParSonId(Long userId) {
        log.debug("supprimerEleveParSonId {}", userId);
        eleveRepository.deleteEleveById(userId);
    }

    /**
     * @return
     */
    @Override
    public long countEleves() {

        List<Eleve> eleveList = eleveRepository.findAll((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());
        if (!eleveList.isEmpty()) {
            return eleveList.size();
        }
        return 0;
    }


    /**
     * Supprime tous les élèves.
     */
    @Override
    public void deleteAllEleves() {
        log.debug("deleteAllEleves");


    }



    /**
     * @param createEleveParameters
     * @return
     */
    @Override
    public Eleve ajouterUnEleve(CreateEleveParameters createEleveParameters) {
        log.debug("Création d'un Eleve {} ({})", createEleveParameters.getUserName().getFullName());
        return ajouterUnNouveauEleve(createEleveParameters);

    }

    /**
     * Récupère une liste d'élèves par leur prénom et nom de famille.
     *
     * @param prenom       Le prénom de l'élève.
     * @param nomDeFamille Le nom de famille de l'élève.
     * @return Une liste d'élèves correspondant aux critères de recherche.
     */
    @Override
    public Optional<Eleve> recupererEleveParPrenomEtNom(String prenom, String nomDeFamille) {

        Optional<User> optionalUser = eleveRepository.findByUserName_PrenomAndUserName_NomDeFamille(prenom, nomDeFamille);
        Eleve eleve=  new Eleve();
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
          eleve= (Eleve) user;
          return Optional.of(eleve);
        }

        return Optional.of(eleve);
    }


    /**
     * Cette méthode permet d'ajouter une photo au profil d'un élève s'il est fourni dans les paramètres.
     *
     * @param parameters Les paramètres de création de l'élève.
     * @param eleve      L'élève auquel la photo doit être associée.
     * @throws KaladewnManagementException Si une erreur survient lors de la récupération ou de la sauvegarde de la photo.
     */
    private static void ajouterPhotoSiPresent(CreateEleveParameters parameters, Eleve eleve) {
        // Récupère le fichier de profil de l'élève depuis les paramètres.
        MultipartFile profileEleve = parameters.getAvatar();

        if (profileEleve != null) {
            try {
                // Convertit le contenu du fichier en tableau de bytes et l'associe à l'élève.
                eleve.setAvatar(profileEleve.getBytes());
            } catch (IOException e) {
                // En cas d'erreur lors de la récupération des bytes du fichier, lance une exception personnalisée.
                throw new KaladewnManagementException().throwException(EntityType.ELEVE, ExceptionType.ENTITY_NOT_FOUND, e.getMessage());
            }
        }
    }

    @Override
    public void supprimerUtilisateurParId(Long userId) {
        eleveRepository.deleteById(userId);
    }

    /**
     * @param telephone
     * @return
     */
    @Override
    public Optional<Eleve> recupererEleveParTelephone(String telephone) {
        return eleveRepository.findByPhoneNumber(new PhoneNumber(telephone));
    }

    /**
     * Recherche un élève distinct par absence et nom d'utilisateur.
     *
     * @param absenceEleve L'absence de l'élève à rechercher.
     * @param userName     Le nom d'utilisateur à rechercher.
     * @return Un objet Optional contenant l'élève distinct correspondant à l'absence et au nom d'utilisateur (s'il existe).
     */
    @Override
    public Optional<Eleve> findDistinctByAbsencesAndUserName(Absence absenceEleve, UserName userName) {


        return eleveRepository.findDistinctByAbsencesAndUserName(absenceEleve, userName);
    }

    /**
     * Recherche un élève dont la date de naissance est similaire à la date spécifiée et dont le numéro INE contient la chaîne spécifiée (insensible à la casse).
     *
     * @param dateDeNaissance La date de naissance à rechercher.
     * @param numeroIne       La chaîne de numéro INE à rechercher.
     * @return Un objet Optional contenant l'élève correspondant aux critères de recherche (s'il existe).
     */
    @Override
    public Optional<Eleve> searchAllByDateDeNaissanceIsLikeAndIneNumberContainsIgnoreCase(LocalDate dateDeNaissance, String numeroIne) {

        Optional<Eleve> eleve = eleveRepository.searchAllByDateDeNaissanceIsLikeAndIneNumberContainsIgnoreCase(dateDeNaissance, numeroIne);
        return eleve;
    }

    /**
     * Recherche un élève par date de création se situant entre les dates spécifiées.
     *
     * @param debut La date de début de la période de création.
     * @param fin   La date de fin de la période de création.
     * @return Un objet Optional contenant l'élève correspondant à la période de création (s'il existe).
     */
    @Override
    public Optional<Optional> findEleveByCreatedDateBetween(LocalDate debut, LocalDate fin) {
        return eleveRepository.findEleveByCreatedDateBetween(debut, fin);
    }

    /**
     * Compte le nombre d'élèves par absence et date de création.
     *
     * @param absenceEleve   L'absence de l'élève à prendre en compte.
     * @param dateDeCreation La date de création à prendre en compte.
     * @return Un objet Optional contenant le nombre d'élèves correspondant aux critères de recherche (s'il existe).
     */
    @Override
    public Optional<Eleve> countEleveByAbsencesAndCreatedDate(Absence absenceEleve, LocalDate dateDeCreation) {
        return eleveRepository.countEleveByAbsencesAndCreatedDate(absenceEleve, dateDeCreation);
    }

    /**
     * Recherche un élève distinct par absence.
     *
     * @param absenceEleve L'absence de l'élève à rechercher.
     * @return Un objet Optional contenant l'élève distinct correspondant à l'absence (s'il existe).
     */
    @Override
    public Optional<Eleve> findDistinctByAbsences(Absence absenceEleve) {
        return eleveRepository.findDistinctByAbsences(absenceEleve);
    }

    /**
     * Récupère la liste complète des élèves inscrits dans le système.
     *
     * @return Une instance facultative (Optional) contenant la liste complète des élèves s'ils sont disponibles, ou Optional.empty() s'il n'y a aucun élève inscrit.
     */
    @Override
    public Page<Eleve> recupererLaListeDesEleves( Pageable pageable) {
        return eleveRepository.findAll(pageable);
    }

    /**
     * Recherche une liste distincte d'élèves en fonction de la salle de classe et de l'établissement scolaire fournis.
     *
     * @param salleDeClasse La salle de classe pour laquelle rechercher les élèves.
     * @return Une liste facultative (Optional) d'élèves. Elle peut être vide si aucun élève ne correspond aux critères de recherche.
     */
    @Override
    public Optional<List<Eleve>> recupererElevesParClasse(Salle salleDeClasse) {
        return eleveRepository.findDistinctBySalle_Etablissement(salleDeClasse);
    }

    private Eleve ajouterUnNouveauEleve(CreateEleveParameters createEleveParameters) {
        Eleve eleve = new Eleve();

        eleve.setPere(createEleveParameters.getMere());
        eleve.setMere(createEleveParameters.getMere());

        eleve.setEtablissement(etablissementService.trouverEtablissementScolaireParSonIdentifiant(createEleveParameters.getEtablissement().getEtablisementScolaireId()));
        eleve.setIneNumber(KaladewnUtility.generatingandomAlphaNumericStringWithFixedLength());

        eleve.setUserName(createEleveParameters.getUserName());
        eleve.setCreatedDate(createEleveParameters.getCreatedDate());
        eleve.setGender(createEleveParameters.getGender());
        eleve.setEmail(createEleveParameters.getEmail());
        eleve.setPhoneNumber(createEleveParameters.getPhoneNumber());
        eleve.setLastModifiedDate(createEleveParameters.getModifyDate());
        eleve.setAddress(createEleveParameters.getAddress());
        eleve.setPassword(createEleveParameters.getPassword());
        ajouterPhotoSiPresent(createEleveParameters, eleve);
        eleve.setMaritalStatus(createEleveParameters.getMaritalStatus());
        eleve.setAge(CalculateUserAge.calculateAge(createEleveParameters.getDateDeNaissance()));
        eleve.setDateDeNaissance(createEleveParameters.getDateDeNaissance());

        eleve.setRoles(Collections.singleton(UserRole.STUDENT));


        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Eleve>> constraintViolations = validator.validate(eleve, ValidationGroupOne.class);
        if (!constraintViolations.isEmpty()) {

            constraintViolations.forEach(c -> KaladewnManagementException.throwException(c.getMessage()));
        }
        Eleve nouveauEleve = eleveRepository.save(eleve);
        if (nouveauEleve != null) {

            return nouveauEleve;
        } else {
            throw new IllegalStateException("L'objet Eleve n'a pas été sauvegardé.");
        }
    }

}

