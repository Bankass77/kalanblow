package ml.kalanblow.gestiondesinscriptions.service.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import ml.kalanblow.gestiondesinscriptions.exception.EntityType;
import ml.kalanblow.gestiondesinscriptions.exception.ExceptionType;
import ml.kalanblow.gestiondesinscriptions.exception.KaladewnManagementException;
import ml.kalanblow.gestiondesinscriptions.model.Parent;
import ml.kalanblow.gestiondesinscriptions.repository.ParentRepository;
import ml.kalanblow.gestiondesinscriptions.service.ParentService;

@Service
@Transactional
public class ParentServiceImpl implements ParentService {

    private final ParentRepository parentRepository;
    private final ModelMapper modelMapper;
    private final KaladewnManagementException kaladewnManagementException;

    @Autowired
    public ParentServiceImpl(final ParentRepository parentRepository, ModelMapper modelMapper,KaladewnManagementException kaladewnManagementException) {
        this.parentRepository = parentRepository;
        this.modelMapper = modelMapper;
        this.kaladewnManagementException= kaladewnManagementException;
    }

    /**
     * @return une liste de parent
     */
    @Override
    public List<Parent> getAllParents() {

        return parentRepository.findAll();
    }

    /**
     * @param id du parent
     * @return un Parent
     */
    @Override
    public Optional<Parent> getParentById(final Long id) {

        try {
            Optional<Parent> parent = parentRepository.findByParentId(id);
            return parent;
        } catch (Exception e) {
            throw kaladewnManagementException.throwExceptionWithTemplate(EntityType.ENSEIGNANT, ExceptionType.DUPLICATE_ENTITY,
                    "getParentById: ", e.getMessage());
        }
    }

    /**
     * @param profession du parent
     * @return une liste de parent
     */
    @Override
    public List<Parent> getParentsByProfession(final String profession) {

        try {
            return parentRepository.findByProfession(profession);
        } catch (Exception e) {
            throw kaladewnManagementException.throwExceptionWithTemplate(EntityType.ENSEIGNANT, ExceptionType.DUPLICATE_ENTITY,
                    "getParentsByProfession: ", e.getMessage());
        }
    }

    /**
     * @param prenom du parent de l'élève
     * @param nomDeFamille nom de famille du parent
     * @return un parent d'élève
     */
    @Override
    public Optional<Parent> findByUserUserNamePrenomAndUserUserNameNomDeFamille(final String prenom, final String nomDeFamille) {
        try {
            return parentRepository.findByUserUserNamePrenomAndUserUserNameNomDeFamille(prenom,
                      nomDeFamille);
        }catch ( Exception e){
            throw kaladewnManagementException.throwExceptionWithTemplate(EntityType.ENSEIGNANT, ExceptionType.DUPLICATE_ENTITY,
                    "findByUserUserNamePrenomAndUserUserNameNomDeFamille: ", e.getMessage());
        }

    }

    /**
     * @param email du parent
     * @return un Parent
     */
    @Override
    public Optional<Parent> findByUserEmail(final String email) {
        try {
            Optional<Parent> parent = parentRepository.findByUserEmail(email);
            return parent;
        } catch (Exception e) {
            throw kaladewnManagementException.throwExceptionWithTemplate(EntityType.ENSEIGNANT, ExceptionType.DUPLICATE_ENTITY,
                    "findByUserEmail: ", e.getMessage());
        }
    }

    /**
     * @param phonenumber du parent
     * @return un Parent
     */
    @Override
    public Optional<Parent> findByPhoneNumber(final String phonenumber) {

        try {
            return parentRepository.findByUserPhoneNumber(phonenumber);
        } catch (Exception e) {
            throw kaladewnManagementException.throwExceptionWithTemplate(EntityType.ENSEIGNANT, ExceptionType.DUPLICATE_ENTITY,
                    "findByPhoneNumber: ", e.getMessage());
        }
    }

    /**
     * @param parent de l'élève
     * @return une liste de parent
     */
    @Override
    public List<Parent> findParentByEnfants(final Parent parent) {

        try {
            return parentRepository.findParentByEnfants(parent);
        } catch (Exception e) {
            throw kaladewnManagementException.throwExceptionWithTemplate(EntityType.ENSEIGNANT, ExceptionType.DUPLICATE_ENTITY,
                    "findParentByEnfants: ", e.getMessage());
        }
    }

    /**
     * @param id du parent
     */
    @Override
    public void deleteParent(final Long id) {
        try {
            parentRepository.deleteById(id);
        } catch (Exception e) {
            throw kaladewnManagementException.throwExceptionWithTemplate(EntityType.ENSEIGNANT, ExceptionType.DUPLICATE_ENTITY,
                    "deleteParent: ", e.getMessage());
        }
    }

    /**
     * @param parent de l'élève
     * @return un Parent
     */
    @Override
    public Optional<Parent> updateParents(long id, final Parent parent) {
        try {
            Optional<Parent> parentToUpdate = parentRepository.findByParentId(id);
            modelMapper.map(parent, parentToUpdate);

        } catch (Exception e) {
            throw kaladewnManagementException.throwExceptionWithTemplate(EntityType.ENSEIGNANT, ExceptionType.DUPLICATE_ENTITY,
                    "updateParents: ", e.getMessage());
        }
        return Optional.empty();
    }
}
