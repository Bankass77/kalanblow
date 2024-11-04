package ml.kalanblow.gestiondesinscriptions.controller.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import ml.kalanblow.gestiondesinscriptions.model.AnneeScolaire;
import ml.kalanblow.gestiondesinscriptions.model.Classe;
import ml.kalanblow.gestiondesinscriptions.model.Eleve;
import ml.kalanblow.gestiondesinscriptions.model.Etablissement;
import ml.kalanblow.gestiondesinscriptions.service.ClasseService;
import ml.kalanblow.gestiondesinscriptions.service.EleveService;

@RestController
@RequestMapping("/api/classes")
@Slf4j
@Validated
public class ClasseController {

    private final ClasseService classeService;

    private final EleveService eleveService;

    @Autowired
    public ClasseController(final ClasseService classeService,EleveService eleveService) {
        this.classeService = classeService;
        this.eleveService = eleveService;
    }

    // Créer une nouvelle classe
    @PostMapping(value = "/creer", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Classe> createClasse(@RequestBody @Valid Classe classe) {
        if (classe == null) {
            return ResponseEntity.badRequest().body(null);
        }
        Classe nouvelleClasse = classeService.createClasse(classe);
        return new ResponseEntity<>(nouvelleClasse, HttpStatus.CREATED);
    }

    // Mettre à jour une classe existante
    @PutMapping("/{id}")
    public ResponseEntity<Classe> updateClasse(@PathVariable Long classeId, @RequestBody Classe classe) {

    return  classeService.findByClasseById(classeId)
            .flatMap(existingClasse -> classeService.updateClasse(classeId, classe))
            .map(updateClasse -> new ResponseEntity<>(updateClasse, HttpStatus.OK))
            .orElse(ResponseEntity.notFound().build());
    }

    // Supprimer une classe par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClasse(@PathVariable Long classeId) {
        if (classeService.findByClasseById(classeId).isPresent()) {
            classeService.deleteClasse(classeId);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Trouver par le nom de la classe
    @GetMapping("/listes/{nom}")
    public ResponseEntity<List<Classe>> findByNom(@RequestParam String nom) {
        List<Classe> classes = classeService.findByNom(nom);
        return classes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(classes);
    }

    // Trouver par l'établissement
    @GetMapping("/{etablissement}")
    public ResponseEntity<List<Classe>> findByEtablissement(@RequestParam Etablissement etablissement) {
        List<Classe> classes = classeService.findByEtablissement(etablissement);
        return classes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(classes);
    }

    // Trouver par l'année scolaire
    @GetMapping("/{anneescolaire}")
    public ResponseEntity<List<Classe>> findByAnneeScolaire(@RequestParam AnneeScolaire anneeScolaire) {
        List<Classe> classes = classeService.findByAnneeScolaire(anneeScolaire);
        return classes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(classes);
    }

    // Trouver une classe par ID et par l'établissement
    @GetMapping("/trouver/{classeEtablissement}")
    public ResponseEntity<Classe> findByClasseIdAndEtablissement(@RequestParam Long classeId, @RequestParam Etablissement etablissement) {
        Optional<Classe> classeOptional = classeService.findByClasseById(classeId);
        return classeOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Compter le nombre de classes par établissement
    @GetMapping("/count/{etablissement}")
    public ResponseEntity<Integer> countByEtablissement(@RequestParam Etablissement etablissement) {
        int count = Math.toIntExact(classeService.countByEtablissement(etablissement));
        return ResponseEntity.ok(count);
    }

    // Trouver une classe par son nom
    @GetMapping("/{nom}")
    public ResponseEntity<Classe> findByClasseName(@RequestParam String nom) {
        Optional<Classe> classeOptional = classeService.findByClasseName(nom);
        return classeOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint pour récupérer les élèves d'une classe avec pagination
    @GetMapping("/pagine/classe/{classeId}")
    public ResponseEntity<Page<Eleve>> getElevesPagineParClasse(
            @PathVariable Long classeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        // Récupérer la classe via le repository ou service
        Optional<Classe> classe = classeService.findByClasseById(classeId);

        Page<Eleve> elevesPage = eleveService.getElevesPagineParClasse(classe.get(), page, size);
        return ResponseEntity.ok(elevesPage);
    }
}

