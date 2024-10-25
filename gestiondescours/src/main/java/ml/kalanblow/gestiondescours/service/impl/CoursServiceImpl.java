package ml.kalanblow.gestiondescours.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import ml.kalanblow.gestiondescours.exception.EntityType;
import ml.kalanblow.gestiondescours.exception.ExceptionType;
import ml.kalanblow.gestiondescours.exception.KaladewnManagementException;
import ml.kalanblow.gestiondescours.model.Cours;
import ml.kalanblow.gestiondescours.model.Matiere;
import ml.kalanblow.gestiondescours.model.Salle;
import ml.kalanblow.gestiondescours.repository.CoursRepository;
import ml.kalanblow.gestiondescours.service.CoursService;
import ml.kalanblow.gestiondescours.service.MatiereService;
import ml.kalanblow.gestiondescours.service.SalleService;

@Service
@Slf4j
public class CoursServiceImpl implements CoursService {

    private final CoursRepository coursRepository;
    private final CoursValidationService validationService;
    private final SalleService salleService;
    private final MatiereService matiereService;

    @Autowired
    public CoursServiceImpl(final CoursRepository coursRepository, CoursValidationService validationService, final SalleService salleService, final MatiereService matiereService) {
        this.coursRepository = coursRepository;
        this.validationService = validationService;
        this.matiereService = matiereService;
        this.salleService = salleService;

    }

    /**
     * Recherche de cours par intitulé.
     *
     * @param intitule du cours
     * @return un Cours avec son intitulé
     */
    @Override
    public Optional<Cours> findCoursByIntitule(final String intitule) {
        log.info("Recherche du cours avec l'intitulé : {}", intitule);  // Ajout de logs pour suivre l'exécution
        Optional<Cours> cours = coursRepository.findCoursByIntitule(intitule);  // Remplacement par le repository

        if (cours.isEmpty()) {
            log.error("Cours non trouvé avec l'intitulé : {}", intitule);
            throw KaladewnManagementException.throwExceptionWithId(
                    EntityType.COURS,
                    ExceptionType.ENTITY_NOT_FOUND,
                    intitule,
                    "L'intitulé du cours n'existe pas."
            );
        }

        return cours;
    }

    /**
     * Recherche de cours par salle.
     *
     * @param salle dans laquelle le cours se déroule
     * @return un Cours avec sa salle où le cours se déroule, sinon lève @{@link Exception{KaladewnManagementException}
     */
    @Override
    public Optional<Cours> findCoursBySalle(final Salle salle) {
        log.info("Recherche des cours dans la salle : {}", salle.getNomSalle());
        Optional<Cours> cours = coursRepository.findCoursBySalle(salle);

        if (cours.isEmpty()) {
            log.error("Aucun cours trouvé pour la salle : {}", salle.getNomSalle());
            throw KaladewnManagementException.throwExceptionWithId(
                    EntityType.COURS,
                    ExceptionType.ENTITY_NOT_FOUND,
                    salle.getNomSalle(),
                    "Aucun cours trouvé pour cette salle."
            );
        }
        return cours;
    }

    /**
     * Recherche des cours entre deux dates.
     *
     * @param dateDebut heure de debut du Cours
     * @param dateFin   heure de Fin du Cours
     * @return une liste de cours,  sinon lève @{@link Exception{KaladewnManagementException}
     */
    @Override
    public List<Cours> findAllByDateDebutAndDateFin(final LocalDateTime dateDebut, final LocalDateTime dateFin) {
        log.info("Recherche des cours entre {} et {}", dateDebut, dateFin);
        return coursRepository.findAllByDateDebutAndDateFin(dateDebut, dateFin);
    }

    /**
     * Recherche de cours par enseignant responsable.
     *
     * @param cours de l'élève
     * @return un Cours fait par l'Enseignant
     */
    @Override
    public Optional<Cours> findCoursByEnseignantResponsable(final Cours cours) {
        log.info("Recherche du cours avec enseignant responsable");

        List<Cours> coursList = cours.getEnseignantResponsable().getCours();

        for (Cours coursOptional : coursList) {

            return Optional.ofNullable(coursOptional);
        }
        return Optional.empty();
    }

    /**
     * Recherche de tous les cours dans une salle.
     *
     * @param salle dans laquelle le cours à lieu
     * @return une Liste de Cours
     */
    @Override
    public List<Cours> findAllCoursBySalle(final Salle salle) {
        log.info("Recherche de tous les cours dans la salle : {}", salle.getNomSalle());
        return coursRepository.findAllCoursBySalle(salle);
    }

    @Override
    public Cours createCours(final Cours cours) {
        assert cours.getSalle() != null;
        Salle salle = validationService.validateSalle(Optional.of(cours.getSalle()), cours.getSalle().getSalleId());
        assert cours.getMatiere() != null;
        Matiere matiere = validationService.validateMatiere(Optional.of(cours.getMatiere()), cours.getMatiere().getMatiereId());

        cours.setSalle(salle);
        cours.setMatiere(matiere);

        return coursRepository.save(cours);
    }

    /**
     * @param id    du cours à mettre à jour
     * @param cours à mettre à jour
     * @return un Cours mis à jour
     */
    @Override
    public Cours updateCours(final Long id, final Cours cours) {
        Optional<Cours> coursOptional = findCoursByCoursId(id);
        if (coursOptional.isEmpty()) {
            throw KaladewnManagementException.throwExceptionWithId(EntityType.COURS, ExceptionType.ENTITY_NOT_FOUND, id.toString(), "Cours non trouvé.");
        }
        coursOptional.get().setEleveCours(cours.getEleveCours());
        coursOptional.get().setDuree(cours.getDuree());
        coursOptional.get().setMatiere(cours.getMatiere());
        coursOptional.get().setSalle(cours.getSalle());
        coursOptional.get().setDescription(cours.getDescription());
        coursOptional.get().setIntitule(cours.getIntitule());
        coursOptional.get().setDateDebut(cours.getDateDebut());
        coursOptional.get().setDateFin(cours.getDateFin());
        coursOptional.get().setEnseignantResponsable(cours.getEnseignantResponsable());
        return coursRepository.saveAndFlush(cours);
    }


    /**
     * Recherche de cours par ID.
     *
     * @param id du Cours
     * @return un Cours correspondant à l'Id
     */
    @Override
    public Optional<Cours> findCoursByCoursId(final Long id) {
        log.info("Recherche du cours avec l'ID : {}", id);
        return coursRepository.findById(id);
    }

    /**
     * Listener de Kafka pour l'envoi de message ,
     * une fois que un nouvel élève a été crée
     * @param  eleveId
     */
    @KafkaListener(topics = "eleve-inscription", groupId = "gestion-des-cours")
    public void handleEleveInscription(long eleveId) {
        // Traiter le message reçu de Kafka, ajouter l'élève à un cours
        Optional<Cours> cours = coursRepository.findCoursByIntitule("Mathématique");
        cours.get().getEleveCours().add(eleveId);
        coursRepository.save(cours.get());
    }
}
