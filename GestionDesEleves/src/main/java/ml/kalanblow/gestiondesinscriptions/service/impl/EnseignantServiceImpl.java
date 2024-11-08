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
import ml.kalanblow.gestiondesinscriptions.exception.EntityNotFoundException;
import ml.kalanblow.gestiondesinscriptions.model.Disponibilite;
import ml.kalanblow.gestiondesinscriptions.model.Email;
import ml.kalanblow.gestiondesinscriptions.model.Enseignant;
import ml.kalanblow.gestiondesinscriptions.model.Etablissement;
import ml.kalanblow.gestiondesinscriptions.model.User;
import ml.kalanblow.gestiondesinscriptions.repository.EnseignantRepository;
import ml.kalanblow.gestiondesinscriptions.service.EnseignantService;

/**
 * Service d'implémentation pour gérer les opérations sur les enseignants.
 * Ce service utilise un référentiel d'enseignants et un mapper de modèles pour effectuer
 * les opérations CRUD ainsi que les opérations de recherche personnalisées sur les enseignants.
 */
@Service
@Transactional
public class EnseignantServiceImpl implements EnseignantService {

    private final EnseignantRepository enseignantRepository;
    private final ModelMapper modelMapper;

    /**
     * Constructeur de `EnseignantServiceImpl` pour injecter les dépendances requises.
     *
     * @param enseignantRepository Référentiel des enseignants pour les opérations CRUD
     * @param modelMapper Mapper de modèles pour mapper les entités
     */
    @Autowired
    public EnseignantServiceImpl(final EnseignantRepository enseignantRepository, ModelMapper modelMapper) {
        this.enseignantRepository = enseignantRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Crée un nouvel enseignant dans le système. Si l'enseignant n'a pas de rôle,
     * le rôle `TEACHER` est ajouté par défaut.
     *
     * @param enseignant Enseignant à créer
     * @return L'enseignant créé et sauvegardé dans la base de données
     */
    @Override
    public Enseignant createEnseignant(final Enseignant enseignant) {
        assignTeacherRoleIfAbsent(enseignant.getUser());
        return enseignantRepository.saveAndFlush(enseignant);
    }

    /**
     * Met à jour les informations d'un enseignant existant.
     *
     * @param enseignantId ID de l'enseignant à mettre à jour
     * @param enseignant Nouvelles informations de l'enseignant
     * @return L'enseignant mis à jour
     */
    @Override
    public Enseignant updateEnseignant(final Long enseignantId, final Enseignant enseignant) {
        Enseignant enseignantToUpdate = findEnseignantById(enseignantId)
                .orElseThrow(() -> new EntityNotFoundException( enseignantId ,Enseignant.class));
        modelMapper.map(enseignant, enseignantToUpdate);
        return enseignantRepository.save(enseignantToUpdate);
    }

    /**
     * Recherche un enseignant en fonction de son matricule.
     *
     * @param leMatricule Matricule unique de l'enseignant
     * @return Un `Optional` contenant l'enseignant correspondant, s'il existe
     */
    @Override
    public Optional<Enseignant> findByLeMatricule(final String leMatricule) {
        return enseignantRepository.findByLeMatricule(leMatricule);
    }

    /**
     * Recherche tous les enseignants d'un établissement spécifique.
     *
     * @param etablissement Établissement auquel les enseignants sont associés
     * @return Liste des enseignants associés à l'établissement donné
     */
    @Override
    public List<Enseignant> findByEtablissement(final Etablissement etablissement) {
        return enseignantRepository.findByEtablissement(etablissement);
    }
    /**
     * Recherche un enseignant par son ID.
     *
     * @param enseignantId L'identifiant unique de l'enseignant à rechercher
     * @return Un `Optional` contenant l'enseignant correspondant, s'il existe
     */
    @Override
    public Optional<Enseignant> findEnseignantById(final Long enseignantId) {
        return enseignantRepository.findById(enseignantId);
    }

    /**
     * Recherche un enseignant par une partie de son adresse e-mail.
     *
     * @param email Adresse e-mail partielle ou complète de l'enseignant
     * @return Un `Optional` contenant l'enseignant correspondant, s'il existe
     */
    @Override
    public Optional<Enseignant> searchAllByEmailIsLike(final Email email) {
        return enseignantRepository.findByUserEmail(email.asString());
    }

    /**
     * Récupère les enseignants créés entre deux dates spécifiques.
     *
     * @param debut Date de début de la plage de recherche
     * @param fin Date de fin de la plage de recherche
     * @return Liste des enseignants créés entre les deux dates données
     */
    @Override
    public List<Enseignant> getEnseignantByUserCreatedDateIsBetween(final LocalDate debut, final LocalDate fin) {
        return enseignantRepository.getEnseignantByUserCreatedDateIsBetween(debut, fin);
    }

    /**
     * Récupère un enseignant disponible pour un jour spécifique et une plage horaire.
     *
     * @param enseignant Enseignant à vérifier
     * @param jourDisponible Jour de disponibilité à vérifier
     * @param heureDebutDisponibilite Heure de début de la disponibilité
     * @param heureFinDisponibilite Heure de fin de la disponibilité
     * @return Un `Optional` contenant l'enseignant disponible, s'il l'est pour le jour et les heures donnés
     */
    @Override
    public Optional<Enseignant> getEnseignantByCoursDEnseignementsAndHeureDebutDisponibiliteAndAndHeureFinDisponibilite(
            final Enseignant enseignant, final DayOfWeek jourDisponible,
            final LocalTime heureDebutDisponibilite, final LocalTime heureFinDisponibilite) {

        Disponibilite disponibilite = enseignant.getHueresDisponibilites().get(jourDisponible);
        if (disponibilite != null) {
            if (!heureDebutDisponibilite.isAfter(disponibilite.getHeureDebut()) && !heureFinDisponibilite.isAfter(disponibilite.getHeureFin())) {
                return Optional.of(enseignant); // Enseignant disponible.
            }
        }

        return Optional.empty();
    }

    /**
     * Récupère tous les enseignants dans le système.
     *
     * @return Ensemble des enseignants enregistrés
     */
    @Override
    public Set<Enseignant> getAllEnseignants() {
        return new HashSet<>(enseignantRepository.findAll());
    }

    /**
     * Récupère les enseignants disponibles pour un jour spécifique et une plage horaire.
     *
     * @param jourDisponible Le jour de disponibilité à vérifier
     * @param heureDebut L'heure de début de la disponibilité
     * @param heureFin L'heure de fin de la disponibilité
     * @return Liste des enseignants disponibles pour les critères donnés
     */
    public List<Enseignant> getEnseignantsDisponibles(DayOfWeek jourDisponible, LocalTime heureDebut, LocalTime heureFin) {
        return enseignantRepository.findAll().stream()
                .filter(enseignant -> isAvailable(enseignant.getHueresDisponibilites().get(jourDisponible), heureDebut, heureFin))
                .collect(Collectors.toList());
    }

    /**
     * Supprime un enseignant du système en utilisant son ID.
     *
     * @param enseignantId ID de l'enseignant à supprimer
     */
    @Override
    public void deleteEnseignant(final Long enseignantId) {
        Optional<Enseignant> enseignant = findEnseignantById(enseignantId);
        enseignant.ifPresent(enseignantRepository::delete);
    }

    /**
     * Assigne le rôle d'enseignant (`UserRole.TEACHER`) à l'utilisateur s'il n'en a pas déjà.
     * Si l'utilisateur n'a aucun rôle ou que la liste des rôles est vide, un nouveau rôle `TEACHER`
     * est attribué. Si l'utilisateur a déjà des rôles mais que `TEACHER` est absent, ce rôle est ajouté.
     *
     * @param user L'utilisateur à qui le rôle d'enseignant peut être assigné.
     */
    private void assignTeacherRoleIfAbsent(User user) {
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(new HashSet<>(Set.of(UserRole.TEACHER)));
        } else {
            user.getRoles().add(UserRole.TEACHER);
        }
    }

    /**
     * Vérifie si une plage horaire spécifiée se situe dans la disponibilité de l'enseignant.
     * Cette méthode compare l'heure de début et l'heure de fin de la plage demandée aux
     * heures de disponibilité de l'enseignant pour déterminer s'il est disponible.
     *
     * @param disponibilite La disponibilité de l'enseignant, incluant les heures de début et de fin.
     * @param heureDebut    L'heure de début de la plage à vérifier.
     * @param heureFin      L'heure de fin de la plage à vérifier.
     * @return `true` si la plage horaire spécifiée est incluse dans la disponibilité de l'enseignant,
     *         `false` sinon.
     */
    private boolean isAvailable(Disponibilite disponibilite, LocalTime heureDebut, LocalTime heureFin) {
        return disponibilite != null &&
                !heureDebut.isBefore(disponibilite.getHeureDebut()) &&
                !heureFin.isAfter(disponibilite.getHeureFin());
    }

}
