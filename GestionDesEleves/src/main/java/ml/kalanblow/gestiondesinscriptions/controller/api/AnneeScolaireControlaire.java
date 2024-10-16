package ml.kalanblow.gestiondesinscriptions.controller.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ml.kalanblow.gestiondesinscriptions.model.AnneeScolaire;
import ml.kalanblow.gestiondesinscriptions.service.AnneeScolaireService;

@RestController
@RequestMapping("/api/anneescolaires")
public class AnneeScolaireControlaire {

    private final AnneeScolaireService anneeScolaireService;

    @Autowired
    public AnneeScolaireControlaire(final AnneeScolaireService anneeScolaireService) {
        this.anneeScolaireService = anneeScolaireService;
    }

    @PostMapping(value = "/creer")
    public ResponseEntity<?> creerAnneeScolaire(@Validated @RequestBody AnneeScolaire anneeScolaire, BindingResult result) {

        if (result.hasErrors()) {
            List<String> messagesErrors = result.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(messagesErrors);
        }

        Optional<AnneeScolaire> nouvelleAnneeScolaire = anneeScolaireService.createNewAnneeScolaire(anneeScolaire);

        return new ResponseEntity<>(nouvelleAnneeScolaire, HttpStatus.CREATED);
    }


    @GetMapping(value = "/{anneeSclaireId}")
    public ResponseEntity<AnneeScolaire> getAnneeSclaire(@PathVariable long anneeSclaireId) {
        Optional<AnneeScolaire> anneeScolaire = anneeScolaireService.findById(anneeSclaireId);
        return ResponseEntity.ofNullable(anneeScolaire.get());
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<AnneeScolaire> updateResource(@PathVariable long id, @RequestBody AnneeScolaire anneeScolaire) {

        Optional<AnneeScolaire> anneeScolaire1 = anneeScolaireService.mettreAJourAnneeScolaire(id, anneeScolaire);
        return anneeScolaire1.map(scolaire -> ResponseEntity.status(HttpStatus.FOUND).body(scolaire)).orElse(null);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteResource(@PathVariable long id) {

        Optional<AnneeScolaire> anneeScolaire = anneeScolaireService.findById(id);

        if (anneeScolaire.isPresent()) {
            return ResponseEntity.status(HttpStatus.FOUND).build();
        }

        return null;
    }

    @GetMapping("/{debut}/{fin}")
    ResponseEntity<AnneeScolaire> findByAnneeScolaireDebutAndAnneeScolaireFin (@PathVariable int debut, @PathVariable int fin){
        Optional<AnneeScolaire> anneeScolaire= anneeScolaireService.findByAnneeScolaireDebutAndAnneeScolaireFin(debut,fin);
        return anneeScolaire.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.noContent().build());
    }

    @GetMapping
    public ResponseEntity<List<AnneeScolaire>> listesAnneeScolaire(){
        List<AnneeScolaire> anneeScolaires = anneeScolaireService.findAll();
        return ResponseEntity.ok(anneeScolaires);
    }
}
