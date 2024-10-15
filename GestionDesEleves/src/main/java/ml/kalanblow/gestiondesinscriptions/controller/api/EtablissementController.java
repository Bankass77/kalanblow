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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ml.kalanblow.gestiondesinscriptions.exception.EntityType;
import ml.kalanblow.gestiondesinscriptions.exception.ExceptionType;
import ml.kalanblow.gestiondesinscriptions.exception.KaladewnManagementException;
import ml.kalanblow.gestiondesinscriptions.model.Address;
import ml.kalanblow.gestiondesinscriptions.model.Email;
import ml.kalanblow.gestiondesinscriptions.model.Etablissement;
import ml.kalanblow.gestiondesinscriptions.model.PhoneNumber;
import ml.kalanblow.gestiondesinscriptions.service.EtablissementService;
import static ml.kalanblow.gestiondesinscriptions.exception.KaladewnManagementException.throwExceptionWithId;

@RestController
@RequestMapping("/api/etablissement")
public class EtablissementController {

    private final EtablissementService etablissementService;

    @Autowired
    public EtablissementController(final EtablissementService etablissementService) {
        this.etablissementService = etablissementService;
    }

    /**
     * @param etablissement
     * @param result        message d'erreur
     * @return un Etablissement
     */
    @PostMapping(value = "/creer")
    public ResponseEntity<?> inscrireEleve(@Validated @RequestBody Etablissement etablissement, BindingResult result) {

        if (result.hasErrors()) {

            List<String> messagesErrors = result.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(messagesErrors);
        }

        Etablissement nouveauEtablissement = etablissementService.createEtablissement(etablissement);
        return new ResponseEntity<>(nouveauEtablissement, HttpStatus.CREATED);
    }

    /**
     * @param id de l'établissement
     * @return Etablissement
     */
    @GetMapping("/{id}")
    public ResponseEntity<Etablissement> trouverEtablissementScolaire(@PathVariable long id) {

        try {
            Etablissement etablissement = etablissementService.trouverEtablissementScolaireParSonIdentifiant(id);
            if (etablissement != null) {

                return ResponseEntity.ok(etablissement);
            }
        } catch (Exception e) {
            throw KaladewnManagementException.throwExceptionWithId(EntityType.ETABLISSEMENTSCOLAIRE, ExceptionType.ENTITY_NOT_FOUND, e.getMessage());
        }

        return null;
    }

    /**
     * @param email de l'établissement
     * @return Etablissement
     */
    @GetMapping("/{email}")
    public ResponseEntity<Etablissement> trouverEtablissementScolairePasonEmail(@PathVariable String email) {

        try {
            Optional<Etablissement> etablissement = etablissementService.findEtablissementScolaireByEmail(new Email(email));
            if (etablissement.isPresent()) {

                return ResponseEntity.ok(etablissement.get());
            }
        } catch (Exception e) {
            throw KaladewnManagementException.throwExceptionWithId(EntityType.ETABLISSEMENTSCOLAIRE, ExceptionType.ENTITY_NOT_FOUND, e.getMessage());
        }

        return null;
    }

    /**
     * @param phone numéro de téléphone de l'établissement
     * @return Etablissement en fonction du numéro de téléphone
     */
    @GetMapping("/{phonenumber}")
    public ResponseEntity<Etablissement> trouverEtablissementScolaireParNumeroTelephone(@PathVariable String phone) {

        try {
            Optional<Etablissement> etablissement = etablissementService.findEtablissementScolaireByPhoneNumber(new PhoneNumber(phone));
            if (etablissement.isPresent()) {

                return ResponseEntity.ok(etablissement.get());
            }
        } catch (Exception e) {
            throw KaladewnManagementException.throwExceptionWithId(EntityType.ETABLISSEMENTSCOLAIRE, ExceptionType.ENTITY_NOT_FOUND, e.getMessage());
        }

        return null;
    }

    /**
     * @param adresse de l'établissement
     * @return Etablissement
     */
    @GetMapping("/{adresseetablissement}")
    public ResponseEntity<Etablissement> trouverEtablissementScolaireParAdresse(@PathVariable Address adresse) {

        try {
            Optional<Etablissement> etablissement = etablissementService.findEtablissementScolaireByAddress(adresse);
            if (etablissement.isPresent()) {

                return ResponseEntity.ok(etablissement.get());
            }
        } catch (Exception e) {
            throw KaladewnManagementException.throwExceptionWithId(EntityType.ETABLISSEMENTSCOLAIRE, ExceptionType.ENTITY_NOT_FOUND, e.getMessage());
        }

        return null;
    }

    /**
     * @param etablisementScolaireId identifiant de l'établissement
     */
    @DeleteMapping("/{id}")
    public void deleteEtablissement(Long etablisementScolaireId) {

        try {
            if (etablisementScolaireId != null) {
                etablissementService.deleteEtablissement(etablisementScolaireId);
            }
        } catch (Exception e) {
            throw KaladewnManagementException.throwException("Id", e.getMessage());
        }

    }

    /**
     * @return Etablissement données de l'établissement
     * @param etablisementScolaireId identifiant établissement
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Etablissement> updateEtablissement(Long etablisementScolaireId, Etablissement etablissement) {

        try {
            Etablissement etablissement1 = etablissementService.updateEtablissement(etablisementScolaireId, etablissement);
            return ResponseEntity.ok(etablissement1);
        } catch (Exception e) {

            throw KaladewnManagementException.throwExceptionWithId(EntityType.ETABLISSEMENTSCOLAIRE, ExceptionType.ENTITY_NOT_FOUND, e.getMessage());
        }
    }

    /**
     * @param nom de l'établissement
     * @return un Etablissement
     */
    @GetMapping("/nom")
    public ResponseEntity<Etablissement> trouverEtablissementScolaireParSonNom(String nom) {

        try {
            Etablissement etablissement = etablissementService.trouverEtablissementScolaireParSonNom(nom);
            return ResponseEntity.ok(etablissement);
        } catch (Exception e) {

            throw KaladewnManagementException.throwExceptionWithId(EntityType.ETABLISSEMENTSCOLAIRE, ExceptionType.ENTITY_NOT_FOUND, e.getMessage());
        }
    }

    /**
     * @param identifiant de l'établissement
     * @return un Etablissement
     */
    @GetMapping("/identifiant")
    public ResponseEntity<Etablissement> trouverEtablissementScolaireParSonIdentifiant(String identifiant) {

        try {
            Optional<Etablissement> etablissement = etablissementService.findEtablissementScolaireByIdentiantEtablissement(identifiant);
            return ResponseEntity.ok(etablissement.get());
        } catch (Exception e) {

            throw KaladewnManagementException.throwExceptionWithId(EntityType.ETABLISSEMENTSCOLAIRE, ExceptionType.ENTITY_NOT_FOUND, e.getMessage());
        }
    }

}
