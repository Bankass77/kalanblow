package ml.kalanblow.gestiondesinscriptions.service.impl;

import lombok.extern.slf4j.Slf4j;
import ml.kalanblow.gestiondesinscriptions.model.*;
import ml.kalanblow.gestiondesinscriptions.repository.PresenceRepository;
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
public class AppelDePresenceServiceImpl implements AppelDePresenceService {

    private final PresenceRepository appelDePresenceRepository;

    private final EleveService eleveService;

    private final SalleDeClasseService salleDeClasseService;

    private final AbsenceEleveService absenceEleveService;

    private final AnneeScolaireService anneeScolaireService;

    private final EnseignantService enseignantService;

    private final MatiereService matiereService;

    private final CoursDEnseignementService coursDEnseignementService;

    @Autowired
    public AppelDePresenceServiceImpl(PresenceRepository appelDePresenceRepository, EleveService eleveService, SalleDeClasseService salleDeClasseService,
            AbsenceEleveService absenceEleveService, AnneeScolaireService anneeScolaireService, EnseignantService enseignantService,
            MatiereService matiereService, CoursDEnseignementService coursDEnseignementService) {
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
     * @param coursDEnseignement
     *         Le cours d'enseignement pour lequel rechercher l'appel de présence.
     * @param eleve
     *         L'élève pour lequel rechercher l'appel de présence.
     * @return Une instance facultative (Optional) de l'appel de présence trouvé, ou une instance vide si aucun appel de présence correspondant n'est trouvé.
     */
    @Override
    public Optional<Presence> findDistinctByCoursAndEleveUserNameOrderByEleve(Cours coursDEnseignement, Eleve eleve) {
        return appelDePresenceRepository.findDistinctByCoursAndEleveUserNameOrderByEleve(coursDEnseignement, eleve);
    }

    /**
     * Recherche un appel de présence en fonction du cours d'enseignement et de l'enseignant spécifiés.
     *
     * @param coursDEnseignement
     *         Le cours d'enseignement pour lequel rechercher l'appel de présence.
     * @return Une instance facultative (Optional) de l'appel de présence trouvé, ou une instance vide si aucun appel de présence correspondant n'est trouvé.
     */
    @Override
    public Optional<Presence> findAppelDePresenceByCours_Enseignant(Cours coursDEnseignement) {
        return appelDePresenceRepository.findAppelDePresenceByCours_Enseignant(coursDEnseignement);
    }

    /**
     * Recherche un appel de présence en fonction du cours d'enseignement et de l'horaire de classe spécifiés.
     *
     * @param horaireClasse
     *         L'horaire de classe pour lequel rechercher l'appel de présence.
     * @return Une instance facultative (Optional) de l'appel de présence trouvé, ou une instance vide si aucun appel de présence correspondant n'est trouvé.
     */
    @Override
    public Optional<Presence> findAppelDePresenceByCours_HoraireClasses(Horaire horaireClasse) {
        return appelDePresenceRepository.findAppelDePresenceByCours_Horaires(horaireClasse);
    }

    /**
     * Effectue l'appel des élèves pour une date donnée.
     *
     * @param dateActuelle
     *         La date pour laquelle l'appel des élèves doit être effectué.
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
                        .filter(cours -> cours.getTimeslot().getHoraire().isWithin(heureDebut, heureFin))
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



    @Override
    public List<Presence> findByDateAppel(LocalDate dateAppel) {
        return null;
    }

    @Override
    public void enregistrerPresenceEleve(Eleve eleve, LocalDate dateActuelle, Cours cours) {
        Presence presence = new Presence();
        presence.setDateAppel(dateActuelle.atStartOfDay());
        presence.setCours(cours);
        presence.setEleve(eleve);
        presence.setAbsences(eleve.getAbsences());
        appelDePresenceRepository.save(presence);
    }
}
