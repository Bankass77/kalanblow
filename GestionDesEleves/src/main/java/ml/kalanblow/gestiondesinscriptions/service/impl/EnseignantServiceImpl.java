package ml.kalanblow.gestiondesinscriptions.service.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import ml.kalanblow.gestiondesinscriptions.model.Cours;
import ml.kalanblow.gestiondesinscriptions.model.Email;
import ml.kalanblow.gestiondesinscriptions.model.Enseignant;
import ml.kalanblow.gestiondesinscriptions.repository.EnseignantRepository;
import ml.kalanblow.gestiondesinscriptions.request.CreateEnseignantParameters;
import ml.kalanblow.gestiondesinscriptions.request.EditEnseignantParameters;
import ml.kalanblow.gestiondesinscriptions.service.EnseignantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Service
@Transactional
@Slf4j
public class EnseignantServiceImpl implements EnseignantService {

    private final EnseignantRepository enseignantRepository;



    private  BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public EnseignantServiceImpl(EnseignantRepository enseignantRepository) {

        this.enseignantRepository = enseignantRepository;

    }

    /**
     * Recherche un enseignant par son identifiant unique.
     *
     * @param aLong L'identifiant unique de l'enseignant à rechercher.
     * @return Une instance facultative (Optional) de l'enseignant trouvé, ou une instance vide si aucun enseignant correspondant n'est trouvé.
     */
    @Override
    public Optional<Enseignant> findById(Long aLong) {
        log.info("Un  enseignant a été trouvé: {}", aLong);
        return enseignantRepository.findById(aLong);
    }

    /**
     * Compte le nombre d'enseignants distincts associés à un cours d'enseignement spécifique.
     *
     * @param coursDEnseignement Le cours d'enseignement pour lequel compter les enseignants.
     * @return Une instance facultative (Optional) du nombre d'enseignants distincts associés au cours d'enseignement, ou une instance vide si aucun enseignant n'est associé au cours.
     */
    @Override
    public Optional<Long> countDistinctByCoursDEnseignements(Cours coursDEnseignement) {

        log.info("Un cours a été trouvé pour cet enseignant: {}", coursDEnseignement);
        return enseignantRepository.countDistinctByCoursDEnseignements(coursDEnseignement);
    }

    /**
     * Recherche des enseignants par adresse e-mail en utilisant une correspondance partielle.
     *
     * @param email L'adresse e-mail à rechercher partiellement.
     * @return Une instance facultative (Optional) des enseignants trouvés, ou une instance vide si aucun enseignant correspondant n'est trouvé.
     */
    @Override
    public Optional<Enseignant> searchAllByEmailIsLike(Email email) {
        log.info("Un enseignat a été trouvé par cet email: {}", email);
        return enseignantRepository.findEnseignantByEmail(email);
    }

    /**
     * Obtient un enseignant en fonction du cours d'enseignement spécifié.
     *
     * @param coursDEnseignement Le cours d'enseignement pour lequel rechercher l'enseignant.
     * @return Une instance facultative (Optional) de l'enseignant trouvé, ou une instance vide si aucun enseignant n'est associé au cours d'enseignement.
     */
    @Override
    public Optional<Enseignant> getEnseignantByCoursDEnseignements(Cours coursDEnseignement) {
        log.info("Un Enseignant a été trouvé pour ce cours: {}", coursDEnseignement);
        return enseignantRepository.getEnseignantByCoursDEnseignements(coursDEnseignement);
    }

    /**
     * Obtient un enseignant en fonction de la plage de dates de création.
     *
     * @param debut La date de début de la plage de dates de création.
     * @param fin   La date de fin de la plage de dates de création.
     * @return Une instance facultative (Optional) de l'enseignant trouvé, ou une instance vide si aucun enseignant correspondant n'est trouvé.
     */
    @Override
    public Optional<Enseignant> getEnseignantByCreatedDateIsBetween(LocalDate debut, LocalDate fin) {
        return enseignantRepository.getEnseignantByCreatedDateIsBetween(debut, fin);
    }

    /**
     * Obtient un enseignant en fonction du cours d'enseignement et des jours disponibles spécifiés.
     *
     * @param coursDEnseignement Le cours d'enseignement pour lequel rechercher l'enseignant.
     * @param enseignant         L'enseignant dont les jours disponibles doivent correspondre.
     * @return Une instance facultative (Optional) de l'enseignant trouvé, ou une instance vide si aucun enseignant correspondant n'est trouvé.
     */
    @Override
    public Optional<Enseignant> getEnseignantByCoursDEnseignementsAndJoursDisponibles(Cours coursDEnseignement, Enseignant enseignant) {

        log.info("Cours {} a été attribué au professeur {}", coursDEnseignement.getNomDuCours(), enseignant.getUserName());

        return enseignantRepository.getEnseignantByCoursDEnseignementsAndJoursDisponibles(coursDEnseignement, enseignant);
    }

    /**
     * Recherche un enseignant en fonction du cours d'enseignement, de l'enseignant, de l'heure de début de disponibilité,
     * de l'heure de fin de disponibilité et du jour disponible spécifiés.
     *
     * @param enseignant              L'enseignant pour lequel rechercher.
     * @param heureDebutDisponibilite L'heure de début de disponibilité à rechercher.
     * @param heureFinDisponibilite   L'heure de fin de disponibilité à rechercher.
     * @return Une instance facultative (Optional) de l'enseignant trouvé, ou une instance vide si aucun enseignant correspondant n'est trouvé.
     */
    @Override
    public Optional<Enseignant> getEnseignantByCoursDEnseignementsAndHeureDebutDisponibiliteAndAndHeureFinDisponibilite(Enseignant enseignant, LocalTime heureDebutDisponibilite, LocalTime heureFinDisponibilite) {
        log.info("La disponibilité pour {} est du {} au {}", enseignant.getUserName(), heureDebutDisponibilite, heureFinDisponibilite);

        return enseignantRepository.getEnseignantByCoursDEnseignementsAndHeureDebutDisponibiliteAndAndHeureFinDisponibilite(enseignant, heureDebutDisponibilite, heureFinDisponibilite);
    }

    /**
     * Crée un nouvel enseignant en utilisant les paramètres spécifiés.
     *
     * @param enseignantParameters Les paramètres pour créer l'enseignant.
     * @return Une option contenant l'enseignant créé, ou une option vide en cas d'erreur.
     */
    @Override
    public Optional<Enseignant> creerEnseignant(CreateEnseignantParameters enseignantParameters) {

        Enseignant.EnseignantBuider enseignantBuider = new Enseignant.EnseignantBuider();
        enseignantBuider.dateDeNaissance(enseignantParameters.getDateDeNaissance());
        enseignantBuider.joursDisponibilite(enseignantParameters.getJoursDisponibles());
        enseignantBuider.etablissementScolaire(enseignantParameters.getEtablissement());
        enseignantBuider.coursDEnseignements(enseignantParameters.getCoursDEnseignements());
        enseignantBuider.horaireClasses(enseignantParameters.getHoraireClasses());
        enseignantBuider.leMatricule(enseignantParameters.getLeMatricule());
        enseignantBuider.heureDebutDisponibilite(enseignantParameters.getHeureDebutDisponibilite());
        enseignantBuider.heureFinDisponibilite(enseignantParameters.getHeureFinDisponibilite());
        enseignantBuider.build();
        Enseignant enseignant = Enseignant.createEnseignatFromBuilder(enseignantBuider);
        log.info("Creating Enseignant {} ({})", enseignant.getUserName().getFullName(), enseignant.getEmail().asString());
        return Optional.of(enseignantRepository.save(enseignant));
    }

    /**
     * Modifie un enseignant existant en utilisant les paramètres spécifiés.
     *
     * @param id                       L'identifiant de l'enseignant à modifier.
     * @param editEnseignantParameters Les paramètres pour effectuer la modification.
     * @return Une option contenant l'enseignant modifié, ou une option vide en cas d'erreur.
     */
    @Override
    public Optional<Enseignant> editerEnseignant(Long id, EditEnseignantParameters editEnseignantParameters) {

        Enseignant enseignant = enseignantRepository.getReferenceById(id);
        if (editEnseignantParameters.getVersion() != enseignant.getVersion()) {

            throw  new ObjectOptimisticLockingFailureException(Enseignant.class, enseignant.getId());

        }
        Enseignant.EnseignantBuider enseignantBuider = new Enseignant.EnseignantBuider();
        enseignantBuider.heureFinDisponibilite(editEnseignantParameters.getHeureFinDisponibilite());
        enseignantBuider.coursDEnseignements(editEnseignantParameters.getCoursDEnseignements());
        enseignantBuider.joursDisponibilite(editEnseignantParameters.getJoursDisponibles());
        enseignantBuider.leMatricule(editEnseignantParameters.getLeMatricule());
        enseignantBuider.horaireClasses(editEnseignantParameters.getHoraireClasses());
        enseignantBuider.heureDebutDisponibilite(editEnseignantParameters.getHeureDebutDisponibilite());
        enseignantBuider.etablissementScolaire(editEnseignantParameters.getEtablissement());
        enseignantBuider.dateDeNaissance(editEnseignantParameters.getDateDeNaissance());
        enseignantBuider.build();
        enseignant = Enseignant.createEnseignatFromBuilder(enseignantBuider);
        log.info("Mise à jour des informations de l'Enseignant {} ({})", enseignant.getUserName().getFullName(), enseignant.getEmail().asString());
        editEnseignantParameters.updateEnseignant(enseignant);
        return Optional.of(enseignant);
    }

    @Override
    public Set<Enseignant> getAllEnseignants() {
        return  new HashSet<>(enseignantRepository.findAll());
    }

    @Override
    public void deleteById(Long id) {
        enseignantRepository.deleteById(id);
    }
}
