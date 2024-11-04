package ml.kalanblow.gestiondesinscriptions.controller.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import ml.kalanblow.gestiondesinscriptions.model.Parent;
import ml.kalanblow.gestiondesinscriptions.service.ParentService;

@RestController
@RequestMapping("/api/parents")
@Slf4j
@Validated
public class ParentController {

    private final ParentService parentService;

    @Autowired
    public ParentController(final ParentService parentService) {
        this.parentService = parentService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Parent> updateClasse(@PathVariable("id") Long parentId, @RequestBody Parent parent) {
        return parentService.getParentById(parentId)
                .flatMap(existingParent -> parentService.updateParents(parentId, parent))
                .map(updatedParent -> new ResponseEntity<>(updatedParent, HttpStatus.OK))
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/{email}")
    public ResponseEntity<Parent> deleteClasse(@PathVariable("email") String email) {
        Optional<Parent> optionalParent = parentService.findByUserEmail(email);
        return optionalParent.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Parent>> getAllParents() {
        List<Parent> parents = parentService.getAllParents();
        return parents.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(parents);
    }

    @GetMapping("/{phonenumber}")
    public ResponseEntity<Parent> getParentByTelephone(@PathVariable String phoneNumber) {
        Optional<Parent> parentOptional = parentService.findByPhoneNumber(phoneNumber);
        return parentOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{parent}")
    public ResponseEntity<List<Parent>> getParentsByEleves(@PathVariable Parent parent) {
        List<Parent> parents = parentService.findParentByEnfants(parent);
        return parents.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(parents);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParent(@PathVariable long id) {
        Optional<Parent> parent = parentService.getParentById(id);
        if (parent.isPresent()) {
            parentService.deleteParent(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{profession}")
    public ResponseEntity<List<Parent>> chercherParentParProfession(@PathVariable String profession){
        List<Parent> parents = parentService.getParentsByProfession(profession);
        return parents.isEmpty() ? ResponseEntity.noContent().build(): ResponseEntity.ok(parents);
    }

    @GetMapping("/{prenom}/{nom}")
    public ResponseEntity<Parent> chercherParent(@PathVariable String prenom, @PathVariable String nom){
        Optional<Parent>  parent = parentService.findByUserUserNamePrenomAndUserUserNameNomDeFamille(prenom, nom);
        return  parent.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }
}
