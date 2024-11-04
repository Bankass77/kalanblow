package ml.kalanblow.gestiondesinscriptions.controller.api;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import ml.kalanblow.gestiondesinscriptions.model.Email;
import ml.kalanblow.gestiondesinscriptions.model.Enseignant;
import ml.kalanblow.gestiondesinscriptions.model.EnseignantDisponibiliteRequest;
import ml.kalanblow.gestiondesinscriptions.service.EnseignantService;

@RestController
@RequestMapping("/api/enseignants")
@Validated
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
    public ResponseEntity<Enseignant> createEnseignant(@Valid @RequestBody Enseignant enseignant) {
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

    @GetMapping("/{leMatricule}")
    public ResponseEntity<Enseignant>  findByLeMatricule(@PathVariable String leMatricule){
        Optional<Enseignant> enseignant = enseignantService.findByLeMatricule(leMatricule);
        return enseignant.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    @GetMapping("/{email}")
    public ResponseEntity<Enseignant> searchAllByEmailIsLike(@PathVariable String email){
        Optional<Enseignant> enseignant = enseignantService.searchAllByEmailIsLike(new Email(email));
        return enseignant.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }

    @GetMapping("/{debut}/{fin}")
    public ResponseEntity<List<Enseignant>>getEnseignantByUserCreatedDateIsBetween (@PathVariable LocalDate debut, @PathVariable LocalDate fin){
        List<Enseignant> enseignants = enseignantService.getEnseignantByUserCreatedDateIsBetween(debut,fin);
        return  ResponseEntity.ofNullable(enseignants);
    }

    @PostMapping("/disponibilite")
    public ResponseEntity<Enseignant> getEnseignantByDisponibilite(@RequestBody EnseignantDisponibiliteRequest request) {
        Optional<Enseignant> enseignant = enseignantService.getEnseignantByCoursDEnseignementsAndHeureDebutDisponibiliteAndAndHeureFinDisponibilite(
                request.getEnseignant(), request.getJourDisponible(), request.getHeureDebut(), request.getHeureFin());

        return enseignant
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Endpoint pour récupérer les enseignants disponibles selon le jour et la plage horaire.
     * @param jour Le jour de la semaine (ex: LUNDI)
     * @param heureDebut L'heure de début de la plage horaire
     * @param heureFin L'heure de fin de la plage horaire
     * @return Liste des enseignants disponibles
     */
    @GetMapping("/disponibilite")
    public ResponseEntity<List<Enseignant>> getEnseignantsDisponibles(
            @RequestParam DayOfWeek jour,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime heureDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime heureFin) {

        List<Enseignant> enseignants = enseignantService.getEnseignantsDisponibles(jour, heureDebut, heureFin);

        if (!enseignants.isEmpty()) {
            return ResponseEntity.ok(enseignants);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
