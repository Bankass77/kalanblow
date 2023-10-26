package ml.kalanblow.gestiondesinscriptions.service.impl;

import jakarta.transaction.Transactional;
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


@Service
@Transactional
public class ParentServiceImpl implements ParentService {

    @Autowired
    private ParentRepository parentRepository;

    @Override
    public List<Parent> getAllParents() {
        return parentRepository.findAll();
    }

    @Override
    public Parent getParentById(Long id) {
        return parentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Parent> getParentsByProfession(String profession) {
        return parentRepository.findByProfession(profession);
    }

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
        parent.setEnfantsMere(createParentParameters.getEnfantsMere());
        parent.setEnfantsPere(createParentParameters.getEnfantsPere());
        return Optional.of(parentRepository.save(parent));
    }

    @Override
    public Optional<Parent> editParent(Long id, EditParentParameters editParentParameters) {

        Optional<Parent> parent = parentRepository.findById(id);

        if (editParentParameters.getVersion() != parent.get().getVersion()) {


            throw new ObjectOptimisticLockingFailureException(Parent.class, parent.get().getId());
        }

        if (parent.isPresent()) {

            parent.get().setProfession(editParentParameters.getProfession());
            parent.get().setGender(editParentParameters.getGender());
            parent.get().setCreatedDate(editParentParameters.getCreatedDate());
            parent.get().setEmail(editParentParameters.getEmail());
            parent.get().setLastModifiedDate(editParentParameters.getModifyDate());
            parent.get().setMaritalStatus(editParentParameters.getMaritalStatus());
            parent.get().setPhoneNumber(editParentParameters.getPhoneNumber());
            parent.get().setPassword(editParentParameters.getPassword());
            parent.get().setUserName(editParentParameters.getUserName());

            parent.get().setEnfantsMere(editParentParameters.getEnfantsMere());
            parent.get().setEnfantsPere(editParentParameters.getEnfantsPere());
            editParentParameters.update(parent.get());


        }

        return parent;
    }

    @Override
    public void deleteParent(Long id) {
        parentRepository.deleteById(id);
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
