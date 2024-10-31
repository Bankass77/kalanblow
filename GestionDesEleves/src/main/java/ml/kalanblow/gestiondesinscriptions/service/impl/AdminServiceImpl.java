package ml.kalanblow.gestiondesinscriptions.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import ml.kalanblow.gestiondesinscriptions.enums.UserRole;
import ml.kalanblow.gestiondesinscriptions.exception.EntityType;
import ml.kalanblow.gestiondesinscriptions.exception.ExceptionType;
import ml.kalanblow.gestiondesinscriptions.exception.KaladewnManagementException;
import ml.kalanblow.gestiondesinscriptions.model.Administrateur;
import ml.kalanblow.gestiondesinscriptions.repository.AdministrateurRepository;
import ml.kalanblow.gestiondesinscriptions.service.AdminService;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

    private final AdministrateurRepository administrateurRepository;


    private final KaladewnManagementException kaladewnManagementException;

    @Autowired
    public AdminServiceImpl(final AdministrateurRepository administrateurRepository,KaladewnManagementException kaladewnManagementException) {
        this.administrateurRepository = administrateurRepository;
        this.kaladewnManagementException= kaladewnManagementException;
    }

    /**
     * @param administrateur de l'application
     * @return un admin qui peut se logger dans le système
     */
    @Override
    public Administrateur ajouterAdministrateur(final Administrateur administrateur) {
        try {
            if (administrateur.getUser().getRoles().isEmpty()) {
                Set<UserRole> roles = new HashSet<>();
                roles.add(UserRole.ADMIN);
                administrateur.getUser().setRoles(roles);
            } else {
                administrateur.getUser().getRoles().add(UserRole.ADMIN);
            }
            return administrateurRepository.save(administrateur);
        } catch (Exception e) {

            throw kaladewnManagementException.throwException("L'admin n'a pas pu être crée", e.getMessage());
        }
    }

    /**
     * @param email de l'admin
     * @param passoword de l'admin
     * @return un admin qui est loggé , sinon return une exception si l'authentification échoue.
     */
    @Override
    public Administrateur authentifierAdministrateur(final String email, final String passoword) {
        try {
            return administrateurRepository.findByUserUserEmailEmail(email).filter(
                    admin -> admin.getUser().getPassword().equals(passoword)).get();
        } catch (Exception e) {
            throw kaladewnManagementException.throwException("Identifiant incorrect", "Email ou le mot de pass est incorrect");
        }
    }

    /**
     * @param admin
     */
    @Override
    public void supprimerAdministrateur(final long admin) {
        try {
            administrateurRepository.deleteById(admin);
        } catch (Exception e) {

            throw kaladewnManagementException.throwException("L'administrateur n'a pu être supprimé: ", e.getMessage());
        }

    }

    /**
     * @return un ensemble d'administrateur
     */
    @Override
    public Set<Administrateur> getAllAdministrateurs() {
        try {
            List<Administrateur> administrateurs = administrateurRepository.findAll();
            if (!administrateurs.isEmpty()) {
                return new HashSet<>(administrateurs);
            }

        } catch (Exception e) {
            throw kaladewnManagementException.throwExceptionWithTemplate(EntityType.USER, ExceptionType.ENTITY_NOT_FOUND,
                    "getAllAdministrateturs", e.getMessage());
        }

        return Set.of();
    }

    /**
     * @param id
     * @param administrateur
     * @return
     */
    @Override
    public Administrateur updateAdministrateur(final long id, final Administrateur administrateur) {
        Administrateur existingAdmin = administrateurRepository.findById(id).orElseThrow(() -> kaladewnManagementException.throwExceptionWithId(EntityType.ADMINISTRATEUR,
                ExceptionType.ENTITY_NOT_FOUND, "L'Admin avec cet " + id + " n'a pas été trouvé"));
        existingAdmin.setUser(administrateur.getUser());
        return administrateurRepository.save(existingAdmin);
    }
}
