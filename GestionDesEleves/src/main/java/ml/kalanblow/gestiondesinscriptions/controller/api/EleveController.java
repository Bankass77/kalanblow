package ml.kalanblow.gestiondesinscriptions.controller.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import ml.kalanblow.gestiondesinscriptions.model.Eleve;
import ml.kalanblow.gestiondesinscriptions.service.EleveService;

@RestController
@RequestMapping("/api/eleves")
@Slf4j
public class EleveController {

    private EleveService eleveService;

    @Autowired
    public EleveController(final EleveService eleveService) {
        this.eleveService = eleveService;
    }

    @GetMapping
    public ResponseEntity<List<Eleve>> findAll() {
        List<Eleve> eleves = eleveService.getAllEleves();
        log.info("Liste des élèves  {}: ", eleves);
        return ResponseEntity.ok(eleves);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Eleve> findById(@PathVariable Long id) {
        Optional<Eleve> eleve = eleveService.FindEleveById(id);
        log.info( "L'élève avec trouvé avec cet id {}", eleve);
        return eleve.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/inscrire")
    public ResponseEntity<Eleve> create(@RequestBody Eleve eleve) {
        if (eleve == null) {
            return ResponseEntity.badRequest().body(null); // Vérifie que l'objet élève n'est pas nul
        }
        Eleve nouvelEleve = eleveService.inscrireNouveauEleve(eleve);
        log.info("L'élève a été crée {}: ", nouvelEleve);
        return ResponseEntity.status(HttpStatus.CREATED).body(nouvelEleve);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Eleve> update(@PathVariable long id, @RequestBody Eleve eleve) {
        Eleve updatedEleve = eleveService.mettreAjourEleve(id, eleve);
        log.info("L'élève dont l'id est {}: ", updatedEleve.getEleveId()+ " a été mise à jour.");
        return ResponseEntity.ok(updatedEleve);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        log.info("L'élève  avec l'identifiant {}: ", id + " a été mise supprimé.");
        eleveService.supprimerEleve(id);
    }
}
