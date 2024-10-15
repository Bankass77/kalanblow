package ml.kalanblow.gestiondesinscriptions.controller.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

import lombok.extern.slf4j.Slf4j;
import ml.kalanblow.gestiondesinscriptions.exception.EntityType;
import ml.kalanblow.gestiondesinscriptions.exception.ExceptionType;
import ml.kalanblow.gestiondesinscriptions.exception.KaladewnManagementException;
import ml.kalanblow.gestiondesinscriptions.model.AnneeScolaire;
import ml.kalanblow.gestiondesinscriptions.model.Classe;
import ml.kalanblow.gestiondesinscriptions.model.Etablissement;
import ml.kalanblow.gestiondesinscriptions.service.ClasseService;

@RestController
@RequestMapping("/api/classes")
@Slf4j
public class ClasseController {

    private final ClasseService classeService;

    @Autowired
    public ClasseController(final ClasseService classeService) {
        this.classeService = classeService;
    }

    // Créer une nouvelle classe
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Classe> createClasse(@RequestBody @Validated Classe classe) {
        if (classe == null) {
            return ResponseEntity.badRequest().body(null);
        }
        Classe nouvelleClasse = classeService.createClasse(classe);
        return new ResponseEntity<>(nouvelleClasse, HttpStatus.CREATED);
    }

    // Mettre à jour une classe existante
    @PutMapping("/update/{id}")
    public ResponseEntity<Classe> updateClasse(@PathVariable("id") Long classeId, @RequestBody Classe classe) {
        Optional<Classe> classeOptional = classeService.findByClasseId(classeId);

        if (classeOptional.isPresent()) {
            Classe classeMiseAJour = classeService.updateClasse(classeId, classe);
            return new ResponseEntity<>(classeMiseAJour, HttpStatus.OK);
        }
        throw new KaladewnManagementException().throwException(EntityType.SALLEDECLASSE, ExceptionType.ENTITY_EXCEPTION,
                "Classe non trouvée pour l'ID : " + classeId);
    }

    // Supprimer une classe par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClasse(@PathVariable("id") Long classeId) {
        if (classeService.findByClasseId(classeId).isPresent()) {
            classeService.deleteClasse(classeId);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Trouver par le nom de la classe
    @GetMapping("/nom")
    public ResponseEntity<List<Classe>> findByNom(@RequestParam String nom) {
        List<Classe> classes = classeService.findByNom(nom);
        return classes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(classes);
    }

    // Trouver par l'établissement
    @GetMapping("/etablissement")
    public ResponseEntity<List<Classe>> findByEtablissement(@RequestParam Etablissement etablissement) {
        List<Classe> classes = classeService.findByEtablissement(etablissement);
        return classes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(classes);
    }

    // Trouver par l'année scolaire
    @GetMapping("/anneescolaire")
    public ResponseEntity<List<Classe>> findByAnneeScolaire(@RequestParam AnneeScolaire anneeScolaire) {
        List<Classe> classes = classeService.findByAnneeScolaire(anneeScolaire);
        return classes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(classes);
    }

    // Trouver une classe par ID et par l'établissement
    @GetMapping("/classeEtablissement")
    public ResponseEntity<Classe> findByClasseIdAndEtablissement(@RequestParam Long classeId, @RequestParam Etablissement etablissement) {
        Optional<Classe> classeOptional = classeService.findByClasseId(classeId);
        return classeOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Compter le nombre de classes par établissement
    @GetMapping("/count")
    public ResponseEntity<Integer> countByEtablissement(@RequestParam Etablissement etablissement) {
        int count = Math.toIntExact(classeService.countByEtablissement(etablissement));
        return ResponseEntity.ok(count);
    }

    // Trouver une classe par son nom
    @GetMapping("/cle")
    public ResponseEntity<Classe> findByClasseName(@RequestParam String nom) {
        Optional<Classe> classeOptional = classeService.findByClasseName(nom);
        return classeOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}

