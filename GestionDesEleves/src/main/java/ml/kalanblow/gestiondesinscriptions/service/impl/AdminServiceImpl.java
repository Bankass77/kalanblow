package ml.kalanblow.gestiondesinscriptions.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import ml.kalanblow.gestiondesinscriptions.exception.KaladewnManagementException;
import ml.kalanblow.gestiondesinscriptions.model.Administrateur;
import ml.kalanblow.gestiondesinscriptions.repository.AdministrateurRepository;

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
