package ml.kalanblow.gestiondesinscriptions.service.impl;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import ml.kalanblow.gestiondesinscriptions.enums.UserRole;
import ml.kalanblow.gestiondesinscriptions.exception.EntityType;
import ml.kalanblow.gestiondesinscriptions.exception.ExceptionType;
import ml.kalanblow.gestiondesinscriptions.exception.KaladewnManagementException;
import ml.kalanblow.gestiondesinscriptions.model.Parent;
import ml.kalanblow.gestiondesinscriptions.repository.ParentRepository;
import ml.kalanblow.gestiondesinscriptions.request.CreateParentParameters;
import ml.kalanblow.gestiondesinscriptions.request.EditParentParameters;
import ml.kalanblow.gestiondesinscriptions.service.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
@Transactional
public class ParentServiceImpl implements ParentService {


    private final ParentRepository parentRepository;

    @Autowired
    public ParentServiceImpl(ParentRepository parentRepository) {
        this.parentRepository = parentRepository;
    }

    @Override
    public List<Parent> getAllParents() {
        return parentRepository.findAll();
    }

    @Override
    public Parent getParentById(Long id) {
        return parentRepository.findParentById(id);
    }

    @Override
    public List<Parent> getParentsByProfession(String profession) {
        return parentRepository.findByProfession(profession);
    }

    /**
     * Saves a new parent using the provided parameters.
     *
     * @param createParentParameters The parameters for creating the new parent.
     * @return An {@code Optional} containing the saved {@code Parent} if the save operation was successful,
     *         or an empty {@code Optional} if the save operation failed.
     * @throws KaladewnManagementException If there is an exception during the save operation (specify the exception type).
     */
    @Override
    public Optional<Parent> saveParent(CreateParentParameters createParentParameters) {

        Parent parent = new Parent();
        parent.setProfession(createParentParameters.getProfession());
        parent.setRoles(Collections.singleton(UserRole.USER));
        parent.setUserName(createParentParameters.getUserName());
        parent.setAddress(createParentParameters.getAddress());
        parent.setGender(createParentParameters.getGender());
        parent.setPassword(createParentParameters.getPassword());
        parent.setPhoneNumber(createParentParameters.getPhoneNumber());
        parent.setLastModifiedDate(createParentParameters.getModifyDate());
        parent.setCreatedDate(createParentParameters.getCreatedDate());
        parent.setMaritalStatus(createParentParameters.getMaritalStatus());
        parent.setEmail(createParentParameters.getEmail());
        ajouterPhotoSiPresent(createParentParameters, parent);
        parent.addEnfantMere(createParentParameters.getEnfantsMere().iterator().next());
        parent.addEnfantPere(createParentParameters.getEnfantsPere().iterator().next());

        // Validation des données
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Parent>> constraintViolations = validator.validate(parent);

        if (!constraintViolations.isEmpty()) {
            constraintViolations.forEach(c -> KaladewnManagementException.throwException(c.getMessage()));
        }
        return Optional.of(parentRepository.save(parent));
    }

    /**
     * Edits a parent with the specified identifier using the provided parameters.
     *
     * @param id The identifier of the parent to be edited.
     * @param editParentParameters The parameters for editing the parent.
     * @return An {@code Optional} containing the edited {@code Parent} if the edit was successful,
     *         or an empty {@code Optional} if the parent with the given ID was not found.
     * @throws ObjectOptimisticLockingFailureException If there is an exception during the edit operation (specify the exception type).
     */
    @Override
    public Optional<Parent> editParent(Long id, EditParentParameters editParentParameters) {

        Optional<Parent> parent = Optional.ofNullable(parentRepository.findParentById(id));

        if (parent.get().getVersion() != editParentParameters.getVersion()) {

            throw new ObjectOptimisticLockingFailureException(Parent.class, parent.get().getId());
        }

        parent.ifPresent(editParentParameters::update);

        return parent;
    }

    @Override
    public void deleteParent(Long id) {
        parentRepository.deleteParentById(id);
    }

    @Override
    public void remove(Parent parent) {

        parentRepository.delete(parent);
    }


    /**
     * Cette méthode permet d'ajouter une photo au profil d'un élève s'il est fourni dans les paramètres.
     *
     * @param parameters Les paramètres de création de l'élève.
     * @param eleve      L'élève auquel la photo doit être associée.
     * @throws KaladewnManagementException Si une erreur survient lors de la récupération ou de la sauvegarde de la photo.
     */
    private static void ajouterPhotoSiPresent(CreateParentParameters parameters, Parent eleve) {
        // Récupère le fichier de profil de l'élève depuis les paramètres.
        MultipartFile profileEleve = parameters.getAvatar();

        if (profileEleve != null) {
            try {
                // Convertit le contenu du fichier en tableau de bytes et l'associe à l'élève.
                eleve.setAvatar(profileEleve.getBytes());
            } catch (IOException e) {
                // En cas d'erreur lors de la récupération des bytes du fichier, lance une exception personnalisée.
                throw new KaladewnManagementException().throwException(EntityType.PARENT, ExceptionType.ENTITY_NOT_FOUND, e.getMessage());
            }
        }
    }
}
