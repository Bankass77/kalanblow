package ml.kalanblow.gestiondesinscriptions.controller.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import ml.kalanblow.gestiondesinscriptions.model.Eleve;
import ml.kalanblow.gestiondesinscriptions.model.Parent;
import ml.kalanblow.gestiondesinscriptions.service.EleveService;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/eleves")
@Slf4j
@Validated
public class EleveController {
    private final EleveService eleveService;

    @Autowired
    public EleveController(final EleveService eleveService) {
        this.eleveService = eleveService;
    }

    // Trouver un élève par ID
    @GetMapping(path = "/id/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Eleve> findById(@PathVariable Long id) {
        Optional<Eleve> eleve = eleveService.FindEleveById(id);
        return eleve.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Supprimer un élève par ID
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        eleveService.supprimerEleve(id);
    }

    // Trouver les élèves associés à un parent spécifique
    @GetMapping(value = "/parent",  produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Eleve>> getEleveParents(@RequestParam Parent parent) {
        List<Eleve> eleves = eleveService.getEleveParents(parent);
        return ResponseEntity.ok(eleves);
    }

    // Trouver un élève par numéro INE
    @GetMapping(path = "/ine/{ineNumber}",  produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Eleve> chercherEleve(@PathVariable String ineNumber) {
        Optional<Eleve> optionalEleve = eleveService.findByIneNumber(ineNumber);
        return optionalEleve.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Trouver un élève par nom et classe
    @GetMapping(value = "/nom-classe", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Eleve>> findByNomAndClasse(@RequestParam String nom, @RequestParam String classe) {
        List<Eleve> eleves = eleveService.findByNomAndClasse(nom, classe);
        return ResponseEntity.ok(eleves);
    }

    // Trouver un élève par numéro de téléphone
    @GetMapping(value = "/telephone", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Eleve> findUserByPhoneNumber(@RequestParam String phoneNumber) {
        Optional<Eleve> eleve = eleveService.findUserByPhoneNumber(phoneNumber);
        return eleve.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    // Trouver un élève par nom et prénom
    @GetMapping(value = "/nom-prenom",  produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Eleve>> findByUserUsername(@RequestParam String nom, @RequestParam String prenom) {
        List<Eleve> eleves = eleveService.findByUserUsername(prenom, nom);
        return ResponseEntity.ok(eleves);
    }

    // Trouver un élève par email
    @GetMapping(value = "/email", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Eleve> findUserByEmail(@RequestParam String email) {
        Optional<Eleve> eleveOptional = eleveService.findUserByEmail(email);
        return eleveOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    // Endpoint pour récupérer les élèves avec pagination
    @GetMapping(value = "/listes", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<Eleve>> getElevesPagine(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Eleve> elevesPage = eleveService.getElevesPagine(page, size);
        return ResponseEntity.ok(elevesPage);
    }
}
