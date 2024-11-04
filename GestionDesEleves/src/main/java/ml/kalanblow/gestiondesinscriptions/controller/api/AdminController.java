package ml.kalanblow.gestiondesinscriptions.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.kalanblow.gestiondesinscriptions.model.Administrateur;
import ml.kalanblow.gestiondesinscriptions.model.Eleve;
import ml.kalanblow.gestiondesinscriptions.service.AdminService;
import ml.kalanblow.gestiondesinscriptions.service.EleveService;

@RestController
@RequestMapping("/api/admin")
@Slf4j
@Validated
public class AdminController {

    private final  AdminService adminService;
    private  final EleveService eleveService;

    public AdminController(final AdminService adminService, final EleveService eleveService) {
        this.adminService = adminService;
        this.eleveService = eleveService;
    }

    @PostMapping("/ajouter")
    public ResponseEntity<Administrateur> ajouterAdministrateur(@Valid @RequestBody Administrateur admin) {
        Administrateur nouvelAdmin = adminService.ajouterAdministrateur(admin);
        return ResponseEntity.ok(nouvelAdmin);
    }


    @PostMapping("/inscrire-eleve")
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

    @GetMapping("/voir-eleves")
    public ResponseEntity<Page<Eleve>> getElevesPagine(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Eleve> elevesPage = eleveService.getElevesPagine(page, size);
        return ResponseEntity.ok(elevesPage);
    }
}
