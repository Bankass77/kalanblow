package ml.kalanblow.gestiondesinscriptions.service.impl;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import ml.kalanblow.gestiondesinscriptions.exception.EntityType;
import ml.kalanblow.gestiondesinscriptions.exception.ExceptionType;
import ml.kalanblow.gestiondesinscriptions.exception.KaladewnManagementException;
import ml.kalanblow.gestiondesinscriptions.model.Eleve;
import ml.kalanblow.gestiondesinscriptions.model.Email;
import ml.kalanblow.gestiondesinscriptions.model.PhoneNumber;
import ml.kalanblow.gestiondesinscriptions.repository.EleveRepository;
import ml.kalanblow.gestiondesinscriptions.request.CreateEleveParameters;
import ml.kalanblow.gestiondesinscriptions.request.EditEleveParameters;
import ml.kalanblow.gestiondesinscriptions.service.EleveService;
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
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static ml.kalanblow.gestiondesinscriptions.service.impl.EleveSpecification.recupererEleveParSonNomePrenom;
import static org.springframework.data.jpa.domain.Specification.where;

/**
 * Service pour la gestion des élèves.
 */
@Service
@Slf4j
@Transactional
public class EleveServiceImpl implements EleveService {

    private final EleveRepository eleveRepository;

    @Autowired
    public EleveServiceImpl(EleveRepository eleveRepository) {

        this.eleveRepository = eleveRepository;


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
        Specification<Eleve> eleveSpecificationEmail = where(null);
        if (email != null) {

            eleveSpecificationEmail = EleveSpecification.recupererEleveParEmail(email.asString());
            return true;
        }
        return false;
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
    public Page<Eleve> obtenirListeElevePage(Pageable pageable) {


        Specification<Eleve> spec = EleveSpecification.all();
        log.debug("obtenirListeElevePage {}", spec);

        return eleveRepository.findAll(spec, pageable);
    }


    private Eleve ajouterUnNouveauEleve(CreateEleveParameters createEleveParameters) {
        Eleve eleve = new Eleve();
        eleve.setEtablissementScolaire(createEleveParameters.getEtablissementScolaire());
        eleve.setIneNumber(KaladewnUtility.generatingandomAlphaNumericStringWithFixedLength());
        eleve.setMotherFirstName(createEleveParameters.getMotherFirstName());
        eleve.setMotherLastName(createEleveParameters.getMotherLastName());
        eleve.setFatherFirstName(createEleveParameters.getFatherFirstName());
        eleve.setFatherLastName(createEleveParameters.getFatherLastName());
        eleve.setUserName(createEleveParameters.getUserName());
        eleve.setCreatedDate(createEleveParameters.getCreatedDate());
        eleve.setGender(createEleveParameters.getGender());
        eleve.setEmail(createEleveParameters.getEmail());
        eleve.setPhoneNumber(createEleveParameters.getPhoneNumber());
        eleve.setLastModifiedDate(createEleveParameters.getModifyDate());
        eleve.setAddress(createEleveParameters.getAddress());
        eleve.setPassword(createEleveParameters.getPassword());
        eleve.setRoles(createEleveParameters.getRoles());
        ajouterPhotoSiPresent(createEleveParameters, eleve);
        eleve.setMaritalStatus(createEleveParameters.getMaritalStatus());
        eleve.setAge(createEleveParameters.getAge());
        eleve.setDateDeNaissance(createEleveParameters.getDateDeNaissance());
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
     * @param userId
     * @param parameters
     * @return
     */
    @Override
    public Eleve mettreAjourUtilisateur(Long userId, EditEleveParameters parameters) {

        Optional<Eleve> eleve = eleveRepository.findById(userId);

        if (parameters.getEmail() != eleve.get().getEmail()) {
            throw new ObjectOptimisticLockingFailureException(Eleve.class, eleve.get().getId());

        }
        parameters = new EditEleveParameters(eleve.get().getVersion(), eleve.get().getUserName(), eleve.get().getGender(),
                eleve.get().getMaritalStatus(), eleve.get().getEmail(), eleve.get().getPassword(), eleve.get().getPhoneNumber(), eleve.get().getAddress(),
                eleve.get().getCreatedDate(), eleve.get().getLastModifiedDate(), eleve.get().getDateDeNaissance(), eleve.get().getAge(),
                eleve.get().getIneNumber(), eleve.get().getMotherFirstName(), eleve.get().getMotherLastName(), eleve.get().getPhoneNumber(),
                eleve.get().getMotherFirstName(), eleve.get().getFatherLastName(), eleve.get().getPhoneNumber(), eleve.get().getRoles(), eleve.get().getEtablissementScolaire());
        parameters.updateStudent(eleve.get());

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

        Specification<Eleve> spec = where(null);
        if (userId != 0) {

            spec = spec.and(EleveSpecification.hasId(userId));
        }
        return eleveRepository.findOne(spec);
    }

    /**
     * @param ineNumber
     * @return
     */
    @Override
    public Optional<Eleve> chercherParSonNumeroIne(String ineNumber) {
        Specification<Eleve> eleveSpecification = where(null);
        if (ineNumber != null) {
            eleveSpecification = eleveSpecification.and((EleveSpecification.findIneNumber(ineNumber)));
        }
        return Optional.ofNullable((Eleve) eleveRepository.findAll(eleveSpecification));
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
     * Récupère un élève en fonction de son numéro INE.
     *
     * @param ineNumber Le numéro INE de l'élève.
     * @return Une instance d'Élève enveloppée dans un Optional, ou Optional.empty() si aucun élève correspondant n'est trouvé.
     */
    @Override
    public Optional<Eleve> getEleveByIneNumber(String ineNumber) {

        Specification<Eleve> eleveSpecification = where(null);
        if (ineNumber != null) {
            eleveSpecification = eleveSpecification.and((EleveSpecification.findIneNumber(ineNumber)));
        }
        return Optional.ofNullable((Eleve) eleveRepository.findAll(eleveSpecification));
    }

    /**
     * @param createEleveParameters
     * @return
     */
    @Override
    public Eleve CreationUtilisateur(CreateEleveParameters createEleveParameters) {
        return ajouterUnNouveauEleve(createEleveParameters);

    }

    /**
     * @param prenom
     * @param nomDeFamille
     * @return
     */
    @Override
    public List<Eleve> recupererEleveParPrenomEtNom(String prenom, String nomDeFamille) {
        return eleveRepository.findAll(where(recupererEleveParSonNomePrenom(prenom).and(recupererEleveParSonNomePrenom(nomDeFamille))));
    }

    /**
     * Édite les informations d'un élève existant.
     *
     * @param id         L'ID de l'élève à éditer.
     * @param parameters Les paramètres de modification de l'élève.
     * @return Une instance d'Élève représentant les nouvelles informations de l'élève.
     */

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
}

