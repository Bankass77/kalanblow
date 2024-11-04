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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import ml.kalanblow.gestiondesinscriptions.model.Address;
import ml.kalanblow.gestiondesinscriptions.model.Email;
import ml.kalanblow.gestiondesinscriptions.model.Etablissement;
import ml.kalanblow.gestiondesinscriptions.model.PhoneNumber;
import ml.kalanblow.gestiondesinscriptions.service.EtablissementService;


@RestController
@RequestMapping("/api/etablissements")
@Validated
public class EtablissementController {

    private final EtablissementService etablissementService;

    @Autowired
    public EtablissementController(final EtablissementService etablissementService) {
        this.etablissementService = etablissementService;
    }

    /**
     * Crée un nouvel établissement.
     * @param etablissement
     * @param result message d'erreur
     * @return un Etablissement
     */
    @PostMapping(value = "/creer")
    public ResponseEntity<?> inscrireEleve(@Valid @RequestBody Etablissement etablissement, BindingResult result) {
        if (result.hasErrors()) {
            List<String> messagesErrors = result.getFieldErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(messagesErrors);
        }

        Etablissement nouveauEtablissement = etablissementService.createEtablissement(etablissement);
        return new ResponseEntity<>(nouveauEtablissement, HttpStatus.CREATED);
    }

    /**
     * Trouve un établissement par ID.
     * @param id identifiant de l'établissement
     * @return Etablissement
     */
    @GetMapping("/{id}")
    public ResponseEntity<Etablissement> trouverEtablissementScolaire(@PathVariable long id) {
        Etablissement etablissement = etablissementService.trouverEtablissementScolaireParSonIdentifiant(id);
        return ResponseEntity.ok(etablissement);
    }

    /**
     * Trouve un établissement par email.
     * @param email de l'établissement
     * @return Etablissement
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<Etablissement> trouverEtablissementScolairePasonEmail(@PathVariable String email) {
        Optional<Etablissement> etablissement = etablissementService.findEtablissementScolaireByEmail(new Email(email));
        return etablissement.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Trouve un établissement par numéro de téléphone.
     * @param phone numéro de téléphone
     * @return Etablissement
     */
    @GetMapping("/phone/{phone}")
    public ResponseEntity<Etablissement> trouverEtablissementScolaireParNumeroTelephone(@PathVariable String phone) {
        Optional<Etablissement> etablissement = etablissementService.findEtablissementScolaireByPhoneNumber(new PhoneNumber(phone));
        return etablissement.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Trouve un établissement par adresse.
     * @param adresse adresse de l'établissement
     * @return Etablissement
     */
    @GetMapping("/adresse/{adresse}")
    public ResponseEntity<Etablissement> trouverEtablissementScolaireParAdresse(@PathVariable Address adresse) {
        Optional<Etablissement> etablissement = etablissementService.findEtablissementScolaireByAddress(adresse);
        return etablissement.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Supprime un établissement par ID.
     * @param etablisementScolaireId identifiant de l'établissement
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEtablissement(@PathVariable Long etablisementScolaireId) {
        etablissementService.deleteEtablissement(etablisementScolaireId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Met à jour un établissement par ID.
     * @param etablisementScolaireId identifiant de l'établissement
     * @param etablissement données de l'établissement à mettre à jour
     * @return Etablissement mis à jour
     */
    @PutMapping("/{id}")
    public ResponseEntity<Etablissement> updateEtablissement(@PathVariable Long etablisementScolaireId,
                                                             @RequestBody Etablissement etablissement) {
        Etablissement etablissement1 = etablissementService.updateEtablissement(etablisementScolaireId, etablissement);
        return ResponseEntity.ok(etablissement1);
    }

    /**
     * Trouve un établissement par son nom.
     * @param nom nom de l'établissement
     * @return Etablissement
     */
    @GetMapping("/nom/{nom}")
    public ResponseEntity<Etablissement> trouverEtablissementScolaireParSonNom(@PathVariable String nom) {
        Etablissement etablissement = etablissementService.trouverEtablissementScolaireParSonNom(nom);
        return ResponseEntity.ok(etablissement);
    }

    /**
     * Trouve un établissement par son identifiant.
     * @param identifiant identifiant de l'établissement
     * @return Etablissement
     */
    @GetMapping("/identifiant/{identifiant}")
    public ResponseEntity<Etablissement> trouverEtablissementScolaireParSonIdentifiant(@PathVariable String identifiant) {
        Optional<Etablissement> etablissement = etablissementService.findEtablissementScolaireByIdentiantEtablissement(identifiant);
        return etablissement.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Charge un logo pour un établissement par ID.
     * @param id identifiant de l'établissement
     * @param logoFile fichier du logo
     * @return Etablissement avec logo mis à jour
     */
    @PostMapping("/{id}/upload-logo")
    public ResponseEntity<Etablissement> uploadLogo(@PathVariable Long id, @RequestParam("logo") MultipartFile logoFile) {
        Etablissement updatedEtablissement = etablissementService.uploadLogo(id, logoFile);
        return ResponseEntity.ok(updatedEtablissement);
    }
}

