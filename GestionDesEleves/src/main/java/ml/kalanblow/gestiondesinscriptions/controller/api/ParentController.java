package ml.kalanblow.gestiondesinscriptions.controller.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import ml.kalanblow.gestiondesinscriptions.exception.EntityType;
import ml.kalanblow.gestiondesinscriptions.exception.ExceptionType;
import ml.kalanblow.gestiondesinscriptions.exception.KaladewnManagementException;
import ml.kalanblow.gestiondesinscriptions.model.Parent;
import ml.kalanblow.gestiondesinscriptions.service.ParentService;

@RestController
@RequestMapping("/api/parents")
@Slf4j
public class ParentController {

    private final ParentService parentService;

    @Autowired
    public ParentController(final ParentService parentService) {
        this.parentService = parentService;
    }

    // Mettre à jour une classe existante
    @PutMapping("/update/{id}")
    public ResponseEntity<Parent> updateClasse(@PathVariable("id") Long parentId, @RequestBody Parent parent) {
        Optional<Parent> parent1 = parentService.getParentById(parentId);

        if (parent1.isPresent()) {
            Optional<Parent> parentMiseAJour = parentService.updateParents(parentId, parent1.get());
            return new ResponseEntity<>(parentMiseAJour.get(), HttpStatus.OK);
        }
        throw new KaladewnManagementException().throwException(EntityType.PARENT, ExceptionType.ENTITY_EXCEPTION,
                "updateClasse : " + parentId);
    }


    @GetMapping("/{email}")
    public ResponseEntity<Parent> deleteClasse(@PathVariable("email") String email) {
        Optional<Parent> optionalParent = parentService.findByUserEmail(email);
        return optionalParent.map(parent -> ResponseEntity.ok(parent)).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
