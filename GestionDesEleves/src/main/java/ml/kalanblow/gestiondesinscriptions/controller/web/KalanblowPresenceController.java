package ml.kalanblow.gestiondesinscriptions.controller.web;

import ml.kalanblow.gestiondesinscriptions.exception.KaladewnManagementException;
import ml.kalanblow.gestiondesinscriptions.model.Cours;
import ml.kalanblow.gestiondesinscriptions.model.Eleve;
import ml.kalanblow.gestiondesinscriptions.model.Salle;
import ml.kalanblow.gestiondesinscriptions.service.CoursService;
import ml.kalanblow.gestiondesinscriptions.service.EleveService;
import ml.kalanblow.gestiondesinscriptions.service.PresenceService;
import ml.kalanblow.gestiondesinscriptions.service.SalleDeClasseService;
import ml.kalanblow.gestiondesinscriptions.service.impl.SalleDeClasseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Controller
@RequestMapping("/presences")
public class KalanblowPresenceController {

    private final PresenceService presenceService;
    private final EleveService eleveService;

    private final CoursService coursService;

    private final SalleDeClasseService salleDeClasseService;

    @Autowired
    public KalanblowPresenceController(PresenceService presenceService, EleveService eleveService, CoursService coursService,
            SalleDeClasseService salleDeClasseService) {
        this.presenceService = presenceService;
        this.eleveService = eleveService;
        this.coursService = coursService;
        this.salleDeClasseService = salleDeClasseService;
    }

    @GetMapping("/eleves/{eleveId}")
    public String getPresenceByEleveId(@PathVariable Long eleveId, Model model) {
        Optional<Eleve> eleveOpt = eleveService.obtenirEleveParSonId(eleveId);

        if (eleveOpt.isPresent()) {
            Eleve eleve = eleveOpt.get();
            model.addAttribute("presence", presenceService.findDistinctByCoursAndEleveUserNameOrderByEleve(eleve.getSalle().getCoursPlanifies().stream().findFirst().get(), eleve));
            return "presenceDetails";
        }

        return "eleveNotFound";
    }

    @GetMapping("/cours/{coursId}")
    public String getPresenceByCoursId(@PathVariable Long coursId, Model model) {
        Optional<Cours> coursOpt = coursService.getCoursById(coursId);

        if (coursOpt.isPresent()) {
            Cours cours = coursOpt.get();
            model.addAttribute("presence", presenceService.findAppelDePresenceByCours_Enseignant(cours));
            return "presenceDetails";
        }


        return "coursNotFound";
    }

    @PostMapping("/effectuer-appel")
    public String effectuerAppel( @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateActuelle,
            @RequestParam Long salleId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime heureDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime heureFin,
            @PageableDefault(size = 20) Pageable pageable) {
        Salle salleDeClasse = salleDeClasseService.findById(salleId)
                .orElseThrow(() -> new KaladewnManagementException.EntityNotFoundException("Salle de classe non trouvée"));

        presenceService.effectuerAppelDesEleves(dateActuelle, salleDeClasse, heureDebut, heureFin);
        return "appelEffectue";
    }

}
