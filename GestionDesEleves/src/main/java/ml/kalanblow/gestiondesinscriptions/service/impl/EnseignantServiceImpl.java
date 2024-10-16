package ml.kalanblow.gestiondesinscriptions.service.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import ml.kalanblow.gestiondesinscriptions.enums.UserRole;
import ml.kalanblow.gestiondesinscriptions.exception.EntityType;
import ml.kalanblow.gestiondesinscriptions.exception.ExceptionType;
import ml.kalanblow.gestiondesinscriptions.exception.KaladewnManagementException;
import ml.kalanblow.gestiondesinscriptions.model.Disponibilite;
import ml.kalanblow.gestiondesinscriptions.model.Email;
import ml.kalanblow.gestiondesinscriptions.model.Enseignant;
import ml.kalanblow.gestiondesinscriptions.model.Etablissement;
import ml.kalanblow.gestiondesinscriptions.repository.EnseignantRepository;
import ml.kalanblow.gestiondesinscriptions.service.EnseignantService;

@Service
@Transactional
public class EnseignantServiceImpl implements EnseignantService {

    @Autowired
    private final EnseignantRepository enseignantRepository;
    private final ModelMapper modelMapper;

    public EnseignantServiceImpl(final EnseignantRepository enseignantRepository, ModelMapper modelMapper) {
        this.enseignantRepository = enseignantRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * @param enseignant
     * @return un Enseignant
     */
    @Override
    public Enseignant createEnseignant(final Enseignant enseignant) {

        try {
            if (enseignant.getUser().getRoles() == null || enseignant.getUser().getRoles().isEmpty()){
                Set<UserRole> roles = new HashSet<>();
                roles.add(UserRole.TEACHER);
                enseignant.getUser().setRoles(roles);
            }else
                if( !enseignant.getUser().getRoles().contains(UserRole.TEACHER)){
                    enseignant.getUser().getRoles().add(UserRole.TEACHER);
                }
            return enseignantRepository.saveAndFlush(enseignant);
        } catch (Exception e) {

            throw KaladewnManagementException.throwExceptionWithTemplate(EntityType.ENSEIGNANT, ExceptionType.DUPLICATE_ENTITY,
                    "createEnseignant", e.getMessage());
        }
    }

    /**
     * @param enseignantId
     * @param enseignant
     * @return un Enseignant
     */
    @Override
    public Enseignant updateEnseignant(final Long enseignantId, final Enseignant enseignant) {

        Optional<Enseignant> enseignantToUpdate = enseignantRepository.findByLeMatricule(enseignant.getLeMatricule());
        try {
            modelMapper.map(enseignant, enseignantToUpdate);
        } catch (Exception e) {

            throw KaladewnManagementException.throwExceptionWithTemplate(EntityType.ENSEIGNANT, ExceptionType.DUPLICATE_ENTITY,
                    "updateEnseignant :", e.getMessage());
        }
        return enseignantToUpdate.get();
    }

    /**
     * @param enseignantId
     */
    @Override
    public void deleteEnseignant(final Long enseignantId) {
        Optional<Enseignant> enseignant = enseignantRepository.findById(enseignantId);
        try {
            enseignant.ifPresent(enseignantRepository::delete);
        } catch (Exception e) {
            throw KaladewnManagementException.throwExceptionWithTemplate(EntityType.ENSEIGNANT, ExceptionType.DUPLICATE_ENTITY,
                    "deleteEnseignant: ", e.getMessage());
        }
    }

    /**
     * @param leMatricule
     * @return Enseignant
     */
    @Override
    public Optional<Enseignant> findByLeMatricule(final String leMatricule) {

        try {
            Optional<Enseignant> enseignant = enseignantRepository.findByLeMatricule(leMatricule);
            if (enseignant.isPresent()) {
                return enseignant;
            }
        } catch (Exception e) {
            throw KaladewnManagementException.throwExceptionWithTemplate(EntityType.ENSEIGNANT, ExceptionType.DUPLICATE_ENTITY,
                    "findByLeMatricule: ", e.getMessage());
        }
        return Optional.empty();
    }

    /**
     * @param etablissement
     * @return Enseignant
     */
    @Override
    public List<Enseignant> findByEtablissement(final Etablissement etablissement) {
        try {
            List<Enseignant> enseignants = enseignantRepository.findByEtablissement(etablissement);
            return enseignants;
        } catch (Exception e) {

            throw KaladewnManagementException.throwExceptionWithTemplate(EntityType.ENSEIGNANT, ExceptionType.DUPLICATE_ENTITY,
                    "findByEtablissement: ", e.getMessage());
        }
    }

    /**
     * @param aLong L'identifiant unique de l'enseignant à rechercher.
     * @return un Enseignant
     */
    @Override
    public Optional<Enseignant> findById(final Long aLong) {

        try {
            Optional<Enseignant> enseignant = enseignantRepository.findById(aLong);
            if (enseignant.isPresent()) {
                return enseignant;
            }
        } catch (Exception e) {
            throw KaladewnManagementException.throwExceptionWithTemplate(EntityType.ENSEIGNANT, ExceptionType.DUPLICATE_ENTITY,
                    "findById: ", e.getMessage());
        }
        return Optional.empty();
    }

    /**
     * @param email L'adresse e-mail à rechercher partiellement.
     * @return un Enseignant
     */
    @Override
    public Optional<Enseignant> searchAllByEmailIsLike(final Email email) {
        try {

            Optional<Enseignant> enseignantParEmail = enseignantRepository.findByUserEmail(email.asString());
            if (enseignantParEmail.isPresent()) {
                return enseignantParEmail;
            }
        } catch (Exception e) {

            throw KaladewnManagementException.throwExceptionWithTemplate(EntityType.ENSEIGNANT, ExceptionType.DUPLICATE_ENTITY,
                    "searchAllByEmailIsLike: ", e.getMessage());
        }
        return Optional.empty();
    }

    /**
     * @param debut La date de début de la plage de dates de création.
     * @param fin   La date de fin de la plage de dates de création.
     * @return une liste d'enseignant
     */
    @Override
    public List<Enseignant> getEnseignantByUserCreatedDateIsBetween(final LocalDate debut, final LocalDate fin) {
        try {
            return enseignantRepository.getEnseignantByUserCreatedDateIsBetween(debut, fin);
        } catch (Exception e) {

            throw KaladewnManagementException.throwExceptionWithTemplate(EntityType.ENSEIGNANT, ExceptionType.DUPLICATE_ENTITY,
                    "searchAllByEmailIsLike: ", e.getMessage());
        }
    }

    /**
     * @param enseignant L'enseignant pour lequel rechercher.
     * @param jourDisponible les jours de disponibilités spécifiques de l'enseignant
     * @param heureDebutDisponibilite L'heure de début de disponibilité à rechercher.
     * @param heureFinDisponibilite L'heure de fin de disponibilité à rechercher.
     * @return un enseignant spécifique avec ses disponibilités
     */
    @Override
    public Optional<Enseignant> getEnseignantByCoursDEnseignementsAndHeureDebutDisponibiliteAndAndHeureFinDisponibilite(final Enseignant enseignant, final DayOfWeek jourDisponible, final LocalTime heureDebutDisponibilite, final LocalTime heureFinDisponibilite) {

        Disponibilite disponibilite = enseignant.getHueresDisponibilites().get(jourDisponible);
        if (disponibilite != null){
            if (!heureDebutDisponibilite.isAfter(disponibilite.getHeureDebut()) && !heureFinDisponibilite.isAfter(disponibilite.getHeureFin())){
                return Optional.of(enseignant); // Enseignant disponible.
            }
        }
        return Optional.empty(); // pas d'enseignant de disponible trouvé.
    }


    /**
     * @return une liste d'enseignants
     */
    @Override
    public Set<Enseignant> getAllEnseignants() {
        try {
            List<Enseignant> enseignantList = enseignantRepository.findAll();
            return new HashSet<>(enseignantList);
        } catch (Exception e) {
            throw KaladewnManagementException.throwExceptionWithTemplate(EntityType.ENSEIGNANT, ExceptionType.DUPLICATE_ENTITY,
                    "getAllEnseignants: ", e.getMessage());
        }
    }

    /**
     * @param id de l'enseignant à supprimer
     */
    @Override
    public void deleteById(final Long id) {

        try {
            Optional<Enseignant> enseignant = enseignantRepository.findById(id);
            enseignantRepository.delete(enseignant.get());
        } catch (Exception e) {
            throw KaladewnManagementException.throwExceptionWithTemplate(EntityType.ENSEIGNANT, ExceptionType.DUPLICATE_ENTITY,
                    "deleteById: ", e.getMessage());
        }
    }

    /**
     * @param enseignant 
     * @param jourDisponible
     * @param heureDebut
     * @param heureFin
     * @return
     */
    @Override
    public Optional<Enseignant> getEnseignantByCoursDEnseignementsAndHeureDebutDisponibiliteAndHeureFinDisponibilite(final Enseignant enseignant, final DayOfWeek jourDisponible, final LocalTime heureDebut, final LocalTime heureFin) {
        return Optional.empty();
    }

    /**
     * Récupère les enseignants disponibles pour un jour et une plage horaire spécifique.
     * @param jourDisponible Le jour de la semaine (ex: LUNDI)
     * @param heureDebut L'heure de début de la disponibilité
     * @param heureFin L'heure de fin de la disponibilité
     * @return Liste des enseignants disponibles
     */
    public List<Enseignant> getEnseignantsDisponibles(DayOfWeek jourDisponible, LocalTime heureDebut, LocalTime heureFin) {
        // Récupérer tous les enseignants
        List<Enseignant> enseignants = enseignantRepository.findAll();

        // Filtrer les enseignants selon leur disponibilité
        return enseignants.stream()
                .filter(enseignant -> enseignant.getHueresDisponibilites().containsKey(jourDisponible))  // Vérifie le jour disponible
                .filter(enseignant -> {
                    Disponibilite disponibilite = enseignant.getHueresDisponibilites().get(jourDisponible);
                    return disponibilite != null &&
                            !heureDebut.isBefore(disponibilite.getHeureDebut()) &&
                            !heureFin.isAfter(disponibilite.getHeureFin());  // Vérifie les heures
                })
                .collect(Collectors.toList());
    }
}
