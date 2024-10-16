package ml.kalanblow.gestiondesinscriptions.controller.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import ml.kalanblow.gestiondesinscriptions.model.Eleve;
import ml.kalanblow.gestiondesinscriptions.model.Parent;
import ml.kalanblow.gestiondesinscriptions.service.ClasseService;
import ml.kalanblow.gestiondesinscriptions.service.EleveService;

@RestController
@RequestMapping("/api/eleves")
@Slf4j
public class EleveController {
    private final EleveService eleveService;

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
        log.info("L'élève avec trouvé avec cet id {}", eleve);
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

    @PutMapping("/{id}")
    public ResponseEntity<Eleve> update(@PathVariable long id, @RequestBody Eleve eleve) {
        Eleve updatedEleve = eleveService.mettreAjourEleve(id, eleve);
        log.info("L'élève dont l'id est {}: ", updatedEleve.getEleveId() + " a été mise à jour.");
        return ResponseEntity.ok(updatedEleve);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        log.info("L'élève  avec l'identifiant {}: ", id + " a été mise supprimé.");
        eleveService.supprimerEleve(id);
    }

    @GetMapping("/{parent}")
    public ResponseEntity<Eleve> getEleveParents(Parent parent) {
        List<Eleve> eleves = eleveService.getEleveParents(parent);

        for (Eleve eleve : eleves) {
            return ResponseEntity.ok(eleve);
        }
        return null;
    }

    @GetMapping("/{ineNumber}")
    public ResponseEntity<Eleve> chercherEleve(@PathVariable String ineNumber) {
        Optional<Eleve> optionalEleve = eleveService.findByIneNumber(ineNumber);

        return optionalEleve.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/{nom}/{classe}")
    public ResponseEntity<Eleve> findByNomAndClasse(@PathVariable String nom, String classe) {
        List<Eleve> eleves = eleveService.findByNomAndClasse(nom, classe);
        for (Eleve eleve : eleves) {
            return ResponseEntity.ok(eleve);
        }
        return null;
    }

    @GetMapping("/{phoneNumber}")
    public ResponseEntity<Eleve> findUserByPhoneNumber(@PathVariable String phoneNumber) {
        Optional<Eleve> eleve = eleveService.findUserByPhoneNumber(phoneNumber);
        return eleve.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping("/{nom}/{prenom}")
    public ResponseEntity<List<Eleve>> findByUserUsername(@PathVariable String nom, @PathVariable String prenom) {
        List<Eleve> eleves = eleveService.findByUserUsername(prenom, nom);
        return ResponseEntity.ok(eleves);
    }

    @GetMapping("/{email}")
    public ResponseEntity<Eleve> findUserByEmail(@PathVariable String email) {
        Optional<Eleve> eleveOptional = eleveService.findUserByEmail(email);
        return eleveOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    // Endpoint pour récupérer les élèves avec pagination
    @GetMapping("/pagine")
    public ResponseEntity<Page<Eleve>> getElevesPagine(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Eleve> elevesPage = eleveService.getElevesPagine(page, size);
        return ResponseEntity.ok(elevesPage);
    }

}
