package ml.kalanblow.gestiondesinscriptions.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import ml.kalanblow.gestiondesinscriptions.enums.UserRole;
import ml.kalanblow.gestiondesinscriptions.exception.KaladewnManagementException;
import ml.kalanblow.gestiondesinscriptions.model.Administrateur;
import ml.kalanblow.gestiondesinscriptions.repository.AdministrateurRepository;
import ml.kalanblow.gestiondesinscriptions.service.AdminService;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

    private final AdministrateurRepository administrateurRepository;

    @Autowired
    public AdminServiceImpl(final AdministrateurRepository administrateurRepository) {
        this.administrateurRepository = administrateurRepository;
    }

    /**
     * @param administrateur de l'application
     * @return un admin qui peut se logger dans le système
     */
    @Override
    public Administrateur ajouterAdministrateur(final Administrateur administrateur) {
        try {
            if(administrateur.getUser().getRoles().isEmpty()){
                Set<UserRole> roles = new HashSet<>();
                roles.add(UserRole.ADMIN);
                administrateur.getUser().setRoles(roles);
            }else {
                administrateur.getUser().getRoles().add(UserRole.ADMIN);
            }
            return administrateurRepository.save(administrateur);
        } catch (Exception e) {

            throw KaladewnManagementException.throwException("L'admin n'a pas pu être crée", e.getMessage());
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
            throw KaladewnManagementException.throwException("Identifiant incorrect", "Email ou le mot de pass est incorrect");
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

            throw KaladewnManagementException.throwException("L'administrateur n'a pu être supprimé: ", e.getMessage());
        }

    }
}
