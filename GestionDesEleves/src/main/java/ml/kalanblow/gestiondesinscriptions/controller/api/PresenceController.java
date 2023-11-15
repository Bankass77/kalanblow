/*
package ml.kalanblow.gestiondesinscriptions.controller.api;

import ml.kalanblow.gestiondesinscriptions.model.Cours;
import ml.kalanblow.gestiondesinscriptions.model.Eleve;
import ml.kalanblow.gestiondesinscriptions.service.CoursService;
import ml.kalanblow.gestiondesinscriptions.service.EleveService;
import ml.kalanblow.gestiondesinscriptions.service.PresenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/presences")
public class PresenceController {

    private final PresenceService presenceService;
    private final EleveService eleveService;

    private final CoursService coursService;

    @Autowired
    public PresenceController(PresenceService presenceService, EleveService eleveService, CoursService coursService) {
        this.presenceService = presenceService;
        this.eleveService = eleveService;
        this.coursService=coursService;
    }

    @GetMapping("/eleves/{eleveId}")
    public ResponseEntity<?> getPresenceByEleveId(@PathVariable Long eleveId) {
        Optional<Eleve> eleveOpt = eleveService.obtenirEleveParSonId(eleveId);

        if (eleveOpt.isPresent()) {
            Eleve eleve = eleveOpt.get();
            return ResponseEntity.ok(presenceService.findDistinctByCoursAndEleveUserNameOrderByEleve(eleve.getSalle().getCoursPlanifies().stream().findFirst().get(), eleve));
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/cours/{coursId}")
    public ResponseEntity<?> getPresenceByCoursId(@PathVariable Long coursId) {
        Optional<Cours> coursOpt = coursService.getCoursById(coursId);

        if (coursOpt.isPresent()) {
            Cours cours = coursOpt.get();
            return ResponseEntity.ok(presenceService.findAppelDePresenceByCours_Enseignant(cours));
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/effectuer-appel")
    public ResponseEntity<?> effectuerAppel(@RequestParam String dateActuelle, @PageableDefault(size = 20) Pageable pageable) {
        LocalDate date = LocalDate.parse(dateActuelle);
        presenceService.effectuerAppelDesEleves(date, pageable);
        return ResponseEntity.ok("Appel effectué avec succès.");
    }


}
*/
