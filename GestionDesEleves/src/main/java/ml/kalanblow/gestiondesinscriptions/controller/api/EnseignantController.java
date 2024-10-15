package ml.kalanblow.gestiondesinscriptions.controller.api;

import java.time.DayOfWeek;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ml.kalanblow.gestiondesinscriptions.model.Enseignant;
import ml.kalanblow.gestiondesinscriptions.service.EnseignantService;

@RestController
@RequestMapping("/api/enseignants") // Pluriel pour correspondre à la ressource "enseignants"
public class EnseignantController {

    private final EnseignantService enseignantService;

    @Autowired
    public EnseignantController(final EnseignantService enseignantService) {
        this.enseignantService = enseignantService;
    }

    /**
     * Crée un nouvel enseignant.
     *
     * @param enseignant
     * @return l'enseignant créé avec le code 201 (CREATED)
     */
    @PostMapping("/create")
    public ResponseEntity<Enseignant> createEnseignant(@Validated @RequestBody Enseignant enseignant) {
        Enseignant nouveauEnseignant = enseignantService.createEnseignant(enseignant);
        return new ResponseEntity<>(nouveauEnseignant, HttpStatus.CREATED);
    }

    /**
     * Met à jour un enseignant existant.
     *
     * @param id l'ID de l'enseignant à mettre à jour
     * @param enseignant les nouvelles informations de l'enseignant
     * @return l'enseignant mis à jour avec le code 200 (OK)
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Enseignant> updateEnseignant(@PathVariable long id, @Validated @RequestBody Enseignant enseignant) {
        Enseignant enseignantMisAJour = enseignantService.updateEnseignant(id, enseignant);
        return new ResponseEntity<>(enseignantMisAJour, HttpStatus.OK);
    }

    /**
     * Retourne la liste des enseignants.
     *
     * @return la liste des enseignants avec le code 200 (OK)
     */
    @GetMapping
    public ResponseEntity<Set<Enseignant>> listeDesEnseignants() {
        Set<Enseignant> enseignants = enseignantService.getAllEnseignants();
        if (enseignants.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(enseignants, HttpStatus.OK);
    }

    /**
     * Retourne un enseignant spécifique.
     *
     * @param id l'ID de l'enseignant
     * @return l'enseignant trouvé avec le code 200 (OK), ou 404 (NOT FOUND) si l'enseignant n'existe pas
     */
    @GetMapping("/{id}")
    public ResponseEntity<Enseignant> recupererEnseignant(@PathVariable long id) {
        Optional<Enseignant> enseignant = enseignantService.findById(id);
        return enseignant.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Supprime un enseignant spécifique.
     *
     * @param id l'ID de l'enseignant à supprimer
     * @return le code 204 (NO CONTENT) si la suppression est réussie, ou 404 (NOT FOUND) si l'enseignant n'existe pas
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerEnseignant(@PathVariable long id) {
        Optional<Enseignant> enseignant = enseignantService.findById(id);
        if (enseignant.isPresent()) {
            enseignantService.deleteEnseignant(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Retourne les jours de disponibilité d'un enseignant spécifique.
     *
     * @param enseignantId l'ID de l'enseignant
     * @return les jours de disponibilité avec le code 200 (OK), ou 404 (NOT FOUND) si l'enseignant n'existe pas
     */
    @GetMapping("/{enseignantId}/disponibilites")
    public ResponseEntity<Set<DayOfWeek>> trouverDisponibiliteEnseignant(@PathVariable long enseignantId) {
        // Rechercher l'enseignant par son ID
        Optional<Enseignant> enseignant = enseignantService.findById(enseignantId);

        // Vérifier si l'enseignant existe
        if (!enseignant.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Récupérer les disponibilités de l'enseignant
        Set<DayOfWeek> disponibilites = enseignantService.getDisponibilitesParEnseignant(enseignant.get());

        // Vérifier si des disponibilités existent
        if (disponibilites == null || disponibilites.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        // Retourner la réponse avec les disponibilités
        return ResponseEntity.ok(disponibilites);
    }
}
