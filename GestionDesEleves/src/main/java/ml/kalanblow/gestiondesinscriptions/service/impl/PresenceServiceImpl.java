package ml.kalanblow.gestiondesinscriptions.service.impl;

import lombok.extern.slf4j.Slf4j;
import ml.kalanblow.gestiondesinscriptions.model.*;
import ml.kalanblow.gestiondesinscriptions.repository.PresenceRepository;
import ml.kalanblow.gestiondesinscriptions.request.CreatePresenceParameters;
import ml.kalanblow.gestiondesinscriptions.request.EditPresenceParameters;
import ml.kalanblow.gestiondesinscriptions.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
@Slf4j
public class PresenceServiceImpl implements PresenceService {

    private final PresenceRepository appelDePresenceRepository;
    private final EleveService eleveService;
    private final SalleService salleDeClasseService;
    private final AbsenceEleveService absenceEleveService;
    private final PeriodeService anneeScolaireService;
    private final EnseignantService enseignantService;
    private final MatiereService matiereService;
    private final CoursService coursDEnseignementService;

    @Autowired
    public PresenceServiceImpl(PresenceRepository appelDePresenceRepository, EleveService eleveService,
                               SalleService salleDeClasseService, AbsenceEleveService absenceEleveService,
                               PeriodeService anneeScolaireService, EnseignantService enseignantService,
                               MatiereService matiereService, CoursService coursDEnseignementService) {
        this.appelDePresenceRepository = appelDePresenceRepository;
        this.eleveService = eleveService;
        this.salleDeClasseService = salleDeClasseService;
        this.absenceEleveService = absenceEleveService;
        this.anneeScolaireService = anneeScolaireService;
        this.enseignantService = enseignantService;
        this.matiereService = matiereService;
        this.coursDEnseignementService = coursDEnseignementService;
    }


    /**
     * Recherche un appel de présence distinct en fonction du cours d'enseignement et de l'élève, trié par le nom de l'élève.
     *
     * @param coursDEnseignement Le cours d'enseignement pour lequel rechercher l'appel de présence.
     * @param eleve              L'élève pour lequel rechercher l'appel de présence.
     * @return Une instance facultative (Optional) de l'appel de présence trouvé, ou une instance vide si aucun appel de présence correspondant n'est trouvé.
     */
    @Override
    public Optional<Presence> findDistinctByCoursAndEleveUserNameOrderByEleve(Cours coursDEnseignement, Eleve eleve) {
        return appelDePresenceRepository.findDistinctByCoursAndEleveUserNameOrderByEleve(coursDEnseignement, eleve);
    }

    /**
     * Recherche un appel de présence en fonction du cours d'enseignement et de l'enseignant spécifiés.
     *
     * @param coursDEnseignement Le cours d'enseignement pour lequel rechercher l'appel de présence.
     * @return Une instance facultative (Optional) de l'appel de présence trouvé, ou une instance vide si aucun appel de présence correspondant n'est trouvé.
     */
    @Override
    public Optional<Presence> findAppelDePresenceByCours_Enseignant(Cours coursDEnseignement) {
        return appelDePresenceRepository.findAppelDePresenceByCours_Enseignant(coursDEnseignement);
    }

    /**
     * Recherche un appel de présence en fonction du cours d'enseignement et de l'horaire de classe spécifiés.
     *
     * @param horaireClasse L'horaire de classe pour lequel rechercher l'appel de présence.
     * @return Une instance facultative (Optional) de l'appel de présence trouvé, ou une instance vide si aucun appel de présence correspondant n'est trouvé.
     */
    @Override
    public Optional<Presence> findAppelDePresenceByCours_HoraireClasses(Horaire horaireClasse) {
        return appelDePresenceRepository.findAppelDePresenceByCours_Horaires(horaireClasse);
    }

    /**
     * Effectue l'appel des élèves pour une date donnée.
     *
     * @param dateActuelle La date pour laquelle l'appel des élèves doit être effectué.
     * @Param  pageable
     */
    @Override
    public void effectuerAppelDesEleves(LocalDate dateActuelle, Salle salleDeClasse, LocalTime heureDebut, LocalTime heureFin) {
        Pageable pageable = PageRequest.of(0, 100);
        Page<Eleve> pageDesEleves = eleveService.recupererLaListeDesElevesParClasseEtDate(salleDeClasse, dateActuelle, pageable);

        if (pageDesEleves.hasContent()) {
            List<Eleve> listeEleve = pageDesEleves.getContent();
            for (Eleve eleve : listeEleve) {
                // Filtrer les cours par plage horaire
                Optional<Cours> coursOpt = eleve.getSalle().getCoursPlanifies().stream()
                        .filter(cours -> cours.getHoraires().stream().findFirst().get().isWithin(heureDebut, heureFin))
                        .findFirst();

                if (coursOpt.isPresent()) {
                    Cours cours = coursOpt.get();
                    enregistrerPresenceEleve(eleve, dateActuelle, cours);
                } else {
                    log.warn("Aucun cours trouvé pour l'élève {} dans la plage horaire spécifiée", eleve.getUserName());
                }
            }
        } else {
            log.warn("Aucun élève trouvé pour la date : {} et la salle de classe : {}", dateActuelle, salleDeClasse.getNomDeLaSalle());
        }
    }

    /**
     * Enregistre la présence de l'élève pour une date spécifiée.
     *
     * @param eleve L'élève dont la présence doit être enregistrée.
     * @param date  La date pour laquelle la présence de l'élève est enregistrée.
     */
    @Override
    public void enregistrerPresenceEleve(Eleve eleve, LocalDate date, Cours cours) {
        Presence presence = new Presence();
        presence.setDateAppel(date.atStartOfDay());
        presence.setCours(cours);
        presence.setEleve(eleve);
        presence.setAbsences(eleve.getAbsences());
        appelDePresenceRepository.save(presence);
    }

    @Override
    public List<Presence> findByDateAppel(LocalDate dateAppel) {
        return appelDePresenceRepository.findByDateAppel(dateAppel);
    }

    /**
     * Crée une nouvelle présence en utilisant les paramètres spécifiés.
     *
     * @param createPresenceParameters Les paramètres pour créer une présence.
     * @return Une option contenant la nouvelle présence si la création réussit, sinon Option vide.
     */
    @Override
    public Optional<Presence> createPresence(CreatePresenceParameters createPresenceParameters) {
        return Optional.empty();
    }

    /**
     * Modifie une présence existante identifiée par l'ID spécifié en utilisant les paramètres d'édition spécifiés.
     *
     * @param id                     L'identifiant de la présence à éditer.
     * @param editPresenceParameters Les paramètres pour effectuer la modification.
     * @return Une option contenant la présence modifiée si l'édition réussit, sinon Option vide.
     */
    @Override
    public Optional<Presence> doEditPresence(Long id, EditPresenceParameters editPresenceParameters) {
        return Optional.empty();
    }
}
