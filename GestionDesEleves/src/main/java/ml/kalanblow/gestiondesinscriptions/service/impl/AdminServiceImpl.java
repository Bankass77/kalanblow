package ml.kalanblow.gestiondesinscriptions.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import ml.kalanblow.gestiondesinscriptions.enums.UserRole;

import ml.kalanblow.gestiondesinscriptions.exception.EntityNotFoundException;
import ml.kalanblow.gestiondesinscriptions.exception.KaladewnManagementException;
import ml.kalanblow.gestiondesinscriptions.model.Administrateur;
import ml.kalanblow.gestiondesinscriptions.model.Eleve;
import ml.kalanblow.gestiondesinscriptions.repository.AdministrateurRepository;
import ml.kalanblow.gestiondesinscriptions.service.AdminService;
import jakarta.transaction.Transactional;

@Service
@Slf4j
@Transactional
public class AdminServiceImpl implements AdminService {

    private final AdministrateurRepository administrateurRepository;

    @Autowired
    public AdminServiceImpl(final AdministrateurRepository administrateurRepository) {
        this.administrateurRepository = administrateurRepository;
    }

    /**
     * Ajoute un administrateur avec le rôle ADMIN si non défini.
     *
     * @param administrateur de l'application
     * @return un admin qui peut se connecter au système
     */
    @Override
    public Administrateur ajouterAdministrateur(final Administrateur administrateur) {

        if (administrateur.getUser().getRoles().isEmpty()) {
            administrateur.getUser().setRoles(Set.of(UserRole.ADMIN));
        } else {
            administrateur.getUser().getRoles().add(UserRole.ADMIN);
        }
        return administrateurRepository.save(administrateur);
    }

    /**
     * Authentifie un administrateur par email et mot de passe.
     *
     * @param email    de l'admin
     * @param password de l'admin
     * @return un admin authentifié, sinon lève une exception si l'authentification échoue.
     */
    @Override
    public Administrateur authentifierAdministrateur(final String email, final String password) {
        return administrateurRepository.findByUserUserEmailEmail(email)
                .filter(admin -> admin.getUser().getPassword().equals(password))
                .orElseThrow(() -> new KaladewnManagementException(email));
    }

    /**
     * Supprime un administrateur par identifiant.
     *
     * @param adminId identifiant de l'admin
     */
    @Override
    public void supprimerAdministrateur(final long adminId) {
        if (!administrateurRepository.existsById(adminId)) {
            throw new EntityNotFoundException(adminId, Administrateur.class);
        } else {
            administrateurRepository.deleteById(adminId);
        }
    }

    /**
     * Retourne tous les administrateurs.
     *
     * @return un ensemble d'administrateurs
     */
    @Override
    public Set<Administrateur> getAllAdministrateurs() {
        List<Administrateur> administrateurs = administrateurRepository.findAll();
        return new HashSet<>(administrateurs);

    }

    /**
     * Met à jour un administrateur existant par ID.
     *
     * @param id             identifiant de l'administrateur
     * @param administrateur détails de l'administrateur à mettre à jour
     * @return administrateur mis à jour
     */
    @Override
    public Administrateur updateAdministrateur(final long id, final Administrateur administrateur) {
        Administrateur existingAdmin = administrateurRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Eleve.class));

        existingAdmin.setUser(administrateur.getUser());
        return administrateurRepository.save(existingAdmin);

    }

    /**
     * @param id de l'admin
     * @return admin
     */
    @Override
    public Optional<Administrateur> getAdminById(final long id) {
        return administrateurRepository.findById(id);
    }
}
