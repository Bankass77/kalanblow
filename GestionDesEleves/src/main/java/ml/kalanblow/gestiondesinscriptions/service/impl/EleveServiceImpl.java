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
import ml.kalanblow.gestiondesinscriptions.request.CreateParentParameters;
import ml.kalanblow.gestiondesinscriptions.request.EditEleveParameters;
import ml.kalanblow.gestiondesinscriptions.request.EditParentParameters;
import ml.kalanblow.gestiondesinscriptions.service.EleveService;
import ml.kalanblow.gestiondesinscriptions.service.EtablissementService;
import ml.kalanblow.gestiondesinscriptions.service.ParentService;
import ml.kalanblow.gestiondesinscriptions.util.CalculateUserAge;
import ml.kalanblow.gestiondesinscriptions.util.KaladewnUtility;
import ml.kalanblow.gestiondesinscriptions.validation.ValidationGroupOne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
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

    private final BCryptPasswordEncoder passwordEncoder;


    @Autowired
    public EleveServiceImpl(EleveRepository eleveRepository, ParentService parentService, EtablissementService etablissementService,BCryptPasswordEncoder passwordEncoder) {

        this.eleveRepository = eleveRepository;
        this.parentService = parentService;
        this.etablissementService = etablissementService;
        this.passwordEncoder=passwordEncoder;

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

        Optional<Eleve> eleve = eleveRepository.findElevesByEmail(new Email(email));
        if (email != null) {

            return eleve;

        }
        throw  new KaladewnManagementException.EntityNotFoundException(email);
    }

    /**
     * Obtient une page de la liste des élèves.
     *
     * @param pageable Les informations de pagination.
     * @return Une page contenant des élèves.
     */
    @Override
    public Page<Eleve> obtenirListeElevePage(Pageable pageable)  {
        log.debug("obtenirListeElevePage {}", pageable.isPaged());
        return eleveRepository.findAll(pageable);
    }


    /**
     * @param userId     L'identifiant de l'élève à mettre à jour.
     * @param parameters Les paramètres de mise à jour.
     * @return Une instance d'Élève représentant les nouvelles informations de l'élève.
     */
    @Override
    public Eleve mettreAjourUtilisateur(Long userId, EditEleveParameters parameters) {

        Optional<Eleve> eleveOptional = eleveRepository.findById(userId);
        Eleve eleve = eleveOptional.orElseThrow(() ->
                new KaladewnManagementException()
                        .throwException(EntityType.USER, ExceptionType.ENTITY_EXCEPTION, "Utilisateur non trouvé avec l'identifiant : " + userId)
        );

        if (parameters.getVersion() != eleve.getVersion()) {
            log.warn("Incohérence de version pour l'utilisateur avec l'ID {}: Attendue {}, Actuelle {}", userId, parameters.getEmail(), eleve.getVersion());
            throw new ObjectOptimisticLockingFailureException(Eleve.class, eleve.getId());

        }
        // Update parent information using EditParentParameters
        EditParentParameters editPereParameters = createEditParentParametersFromCreateParentParameters(parameters.getPere());
        EditParentParameters editMereParameters = createEditParentParametersFromCreateParentParameters(parameters.getMere());

        updateParentInformation(eleve.getPere(), editPereParameters);
        updateParentInformation(eleve.getMere(), editMereParameters);

        log.debug("Mettre à jour un Eleve {} ({})", eleve.getEmail(), eleve.getUserName());
        // Mettre à jour les informations de l'élève
        parameters.updateStudent(eleve);
        return eleve;
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

        return eleveRepository.findById(userId);
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
     * Récupère le nombre d'objets Eleve depuis eleveRepository.
     *
     * @return Le nombre d'objets Eleve en tant que valeur longue. Renvoie 0 s'il n'y a aucun objet Eleve.
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
     * Ajoute un nouvel élève en utilisant les paramètres spécifiés.
     *
     * @param createEleveParameters Les paramètres nécessaires pour créer un nouvel élève.
     * @return L'objet Eleve créé.
     */
    @Override
    public Eleve ajouterUnEleve(CreateEleveParameters createEleveParameters) {
        log.debug("Création d'un Eleve {} ({})", createEleveParameters.getUserName().getFullName(), createEleveParameters.getEmail());

        // création à jour les informations du père
        CreateParentParameters createPereParameters = new CreateParentParameters();
        createPereParameters.setUserName(createEleveParameters.getPere().getUserName());
        createPereParameters.setEmail(createEleveParameters.getPere().getEmail());
        createPereParameters.setAddress(createEleveParameters.getPere().getAddress());
        createPereParameters.setPhoneNumber(createEleveParameters.getPere().getPhoneNumber());
        createPereParameters.setMaritalStatus(createEleveParameters.getPere().getMaritalStatus());
        createPereParameters.setProfession(createEleveParameters.getPere().getProfession());
        createPereParameters.setGender(createEleveParameters.getPere().getGender());
        createPereParameters.setModifyDate(createEleveParameters.getPere().getLastModifiedDate());
        createPereParameters.setCreatedDate(createEleveParameters.getPere().getCreatedDate());
        createPereParameters.setEnfantsPere(createEleveParameters.getPere().getEnfantsPere());


        //  création à jour les informations de la mère
        CreateParentParameters createMereParameters = new CreateParentParameters();
        createMereParameters.setUserName(createEleveParameters.getMere().getUserName());
        createMereParameters.setEmail(createEleveParameters.getMere().getEmail());
        createMereParameters.setMaritalStatus(createEleveParameters.getMere().getMaritalStatus());
        createMereParameters.setAddress(createEleveParameters.getMere().getAddress());
        createMereParameters.setPhoneNumber(createEleveParameters.getMere().getPhoneNumber());
        createPereParameters.setProfession(createEleveParameters.getMere().getProfession());
        createPereParameters.setMaritalStatus(createEleveParameters.getMere().getMaritalStatus());
        createPereParameters.setAddress(createEleveParameters.getMere().getAddress());
        createPereParameters.setCreatedDate(createEleveParameters.getMere().getCreatedDate());
        createPereParameters.setModifyDate(createEleveParameters.getMere().getLastModifiedDate());
        createPereParameters.setEnfantsMere(createEleveParameters.getPere().getEnfantsMere());


        return ajouterUnNouveauEleve(createEleveParameters, createPereParameters, createMereParameters);

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
        Eleve eleve= (Eleve) optionalUser.orElse(new Eleve());

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


    /**
     * Supprime un utilisateur (Eleve) en utilisant son identifiant.
     *
     * @param userId L'identifiant de l'utilisateur à supprimer.
     */
    @Override
    public void supprimerUtilisateurParId(Long userId) {
        eleveRepository.deleteById(userId);
    }

    /**
     * Récupère un élève en utilisant le numéro de téléphone spécifié.
     *
     * @param telephone Le numéro de téléphone de l'élève à récupérer.
     * @return Un objet Optional contenant l'élève correspondant au numéro de téléphone, ou Optional vide si aucun élève n'est trouvé.
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

        return eleveRepository.searchAllByDateDeNaissanceIsLikeAndIneNumberContainsIgnoreCase(dateDeNaissance, numeroIne);
    }

    /**
     * Recherche un élève par date de création se situant entre les dates spécifiées.
     *
     * @param debut La date de début de la période de création.
     * @param fin   La date de fin de la période de création.
     * @return Un objet Optional contenant l'élève correspondant à la période de création (s'il existe).
     */
    @Override
    public Optional<Eleve> findEleveByCreatedDateBetween(LocalDate debut, LocalDate fin) {
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


    /**
     * Ajoute un nouvel élève en utilisant les paramètres spécifiés.
     *
     * @param createEleveParameters Les paramètres nécessaires pour créer un nouvel élève.
     * @return L'objet Eleve créé.
     */
    private Eleve ajouterUnNouveauEleve(CreateEleveParameters createEleveParameters, CreateParentParameters pereParams, CreateParentParameters mereParams) {
        Eleve eleve = new Eleve();

        // Crée les objets Parent à partir des paramètres
        Parent pere = null;
        try {
            pere = parentService.saveParent(pereParams).orElseThrow(() -> new KaladewnManagementException.EntityNotFoundException("Erreur lors de la sauvegarde du père"));
        } catch (IOException e) {
            throw new KaladewnManagementException().throwException(EntityType.PARENT, ExceptionType.ENTITY_EXCEPTION);
        }
        Parent mere = null;
        try {
            mere = parentService.saveParent(mereParams).orElseThrow(() -> new KaladewnManagementException.EntityNotFoundException("Erreur lors de la sauvegarde de la mère"));
        } catch (IOException e) {
            throw new KaladewnManagementException().throwException(EntityType.PARENT, ExceptionType.ENTITY_EXCEPTION);
        }

        eleve.setEtablissement(etablissementService.trouverEtablissementScolaireParSonIdentifiant(createEleveParameters.getEtablissement().getEtablisementScolaireId()));
        eleve.setIneNumber(KaladewnUtility.generatingandomAlphaNumericStringWithFixedLength());
        eleve.setUserName(createEleveParameters.getUserName());
        eleve.setCreatedDate(createEleveParameters.getCreatedDate());
        eleve.setGender(createEleveParameters.getGender());
        eleve.setEmail(createEleveParameters.getEmail());
        eleve.setPhoneNumber(createEleveParameters.getPhoneNumber());
        eleve.setLastModifiedDate(createEleveParameters.getModifyDate());
        eleve.setAddress(createEleveParameters.getAddress());
        eleve.setPassword(passwordEncoder.encode(createEleveParameters.getPassword()));
        ajouterPhotoSiPresent(createEleveParameters, eleve);
        eleve.setMaritalStatus(createEleveParameters.getMaritalStatus());
        eleve.setAge(CalculateUserAge.calculateAge(createEleveParameters.getDateDeNaissance()));
        eleve.setDateDeNaissance(createEleveParameters.getDateDeNaissance());
        eleve.setRoles(Collections.singleton(UserRole.STUDENT));
        eleve.setPere(createEleveParameters.getPere());
        eleve.setMere(createEleveParameters.getMere());
        // Ajoute les parents à l'objet Eleve
        eleve.setPere(pere);
        eleve.setMere(mere);
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


    /**
     * Updates the information of an existing parent with the provided parameters.
     *
     * @param parent The {@code Parent} object to be updated.
     * @param parameters The parameters for updating the parent information.
     * @throws KaladewnManagementException If there is an exception during the update operation (specify the exception type).
     */
    private void updateParentInformation(Parent parent, EditParentParameters parameters) {

        Optional<Parent> optionalParent = Optional.ofNullable(parentService.getParentById(parent.getId()));
        Parent existingParent = optionalParent.orElseThrow(() -> new KaladewnManagementException().throwException(EntityType.PARENT, ExceptionType.ENTITY_EXCEPTION, String.valueOf(parent.getId())));

        if (parameters.getVersion() != existingParent.getVersion()) {
            log.warn("Incohérence de version pour l'utilisateur avec l'ID {}: Attendue {}, Actuelle {}", parent.getId(), parent.getEmail(), parent.getVersion());
            throw new ObjectOptimisticLockingFailureException(Parent.class, existingParent.getId());
        }

        // Mettez à jour les informations spécifiques du parent
        parameters.update(existingParent);

        // Appel à la méthode du service Parent pour mettre à jour le parent dans la base de données
        parentService.editParent(existingParent.getId(), parameters);
    }

    private EditParentParameters createEditParentParametersFromCreateParentParameters(Parent parent) {
        EditParentParameters editParentParameters = new EditParentParameters();
        editParentParameters.setEmail(parent.getEmail());
        editParentParameters.setCreatedDate(parent.getCreatedDate());
        editParentParameters.setModifyDate(parent.getLastModifiedDate());
        editParentParameters.setEnfantsMere(parent.getEnfantsMere());
        editParentParameters.setGender(parent.getGender());
        editParentParameters.setProfession(parent.getProfession());
        editParentParameters.setPhoneNumber(parent.getPhoneNumber());
        editParentParameters.setUserName(parent.getUserName());
        editParentParameters.setMaritalStatus(parent.getMaritalStatus());
        editParentParameters.setAddress(parent.getAddress());
        editParentParameters.setEnfantsMere(parent.getEnfantsMere());
        editParentParameters.setEnfantsPere(parent.getEnfantsPere());

        return editParentParameters;
    }

}

