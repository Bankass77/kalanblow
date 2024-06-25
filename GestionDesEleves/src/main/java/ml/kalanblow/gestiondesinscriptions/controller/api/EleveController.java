package ml.kalanblow.gestiondesinscriptions.controller.api;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import ml.kalanblow.gestiondesinscriptions.exception.KaladewnManagementException;
import ml.kalanblow.gestiondesinscriptions.model.Eleve;
import ml.kalanblow.gestiondesinscriptions.request.CreateEleveParameters;
import ml.kalanblow.gestiondesinscriptions.request.EditEleveParameters;
import ml.kalanblow.gestiondesinscriptions.service.EleveService;
import ml.kalanblow.gestiondesinscriptions.util.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Cette classe représente un contrôleur REST pour les opérations liées aux élèves
 * dans l'API v1. Elle gère les requêtes HTTP pour la récupération, la création,
 * la mise à jour et la recherche d'élèves.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/eleves")
@Tag(name = "Eleve", description = "Rest API pour décrire les informations des élèves de Kalanblow.")
public class EleveController {

    private final EleveService eleveService;

    /**
     * Constructeur de la classe EleveController.
     *
     * @param userService Le service d'élèves utilisé pour effectuer les opérations.
     */

    public EleveController(EleveService userService) {
        this.eleveService = userService;
    }


    /**
     * Gère la requête GET pour récupérer tous les élèves paginés.
     *
     * @param pageable Les paramètres de pagination.
     * @return Une ResponseEntity contenant une ApiResponse avec la liste des élèves récupérée.
     */
    @Operation(
            summary = "${api.kalanblow-getion-eleve.get-kalanblow-getion-eleve.description}",
            description = "${api.kalanblow-getion-eleve.get-kalanblow-getion-eleve.notes}"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description =
                    "${api.responseCodes.ok.description}"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description =
                    "${api.responseCodes.badRequest.description}"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description =
                    "${api.responseCodes.notFound.description}"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "422", description =
                    "${api.responseCodes.unprocessableEntity.description}")
    })
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<ApiResponse<Page<Eleve>>> recupererTousLesEleves(Pageable pageable) {
        try {
       Page<Eleve> eleves = eleveService.obtenirListeElevePage(pageable);
            log.info("La liste des élèves est: Eleves={}", eleves);
            return ResponseEntity.ok(new ApiResponse<>(eleves, "liste des élèves récupérer avec succès."));
        } catch (KaladewnManagementException.EntityNotFoundException e) {

            return ResponseEntity.notFound().build();
        }
    }


    /**
     * Gère la requête POST pour créer un nouveau élève.
     *
     * @param createEleveParameters Les paramètres pour créer un nouvel élève.
     * @return Une ResponseEntity contenant une ApiResponse avec l'élève créé.
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse<Eleve>> creerNouveauEleve(@RequestBody CreateEleveParameters createEleveParameters) {

        try {

            Eleve newEleve = eleveService.ajouterUnEleve(createEleveParameters);

            return ResponseEntity.ok(new ApiResponse<Eleve>(newEleve, "Elève crée avec succès."));

        } catch (KaladewnManagementException.EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Gère la requête GET pour obtenir un élève par son ID.
     *
     * @param eleveId L'ID de l'élève à obtenir.
     * @return Une ResponseEntity contenant une ApiResponse avec l'élève récupéré.
     */
    @Operation(
            summary = "${api.kalanblow-getion-eleve.get-kalanblow-getion-eleve.description}",
            description = "${api.kalanblow-getion-eleve.get-kalanblow-getion-eleve.notes}"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description =
                    "${api.responseCodes.ok.description}"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description =
                    "${api.responseCodes.badRequest.description}"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description =
                    "${api.responseCodes.notFound.description}"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "422", description =
                    "${api.responseCodes.unprocessableEntity.description}")
    })
    @RequestMapping(value = "/cleIdentification/{eleveId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Eleve>> obtenirEleveParSonId(@PathVariable Long eleveId) {
        log.info("Un Elève a été  trouvé avec : eleveId={}", eleveId);
        try {
            String successMessage = "Elève récupéré avec succès. Eleve ID : " + eleveId;
            return ResponseEntity.ok(new ApiResponse<Eleve>(eleveService.obtenirEleveParSonId(eleveId).get(), successMessage));
        } catch (KaladewnManagementException.EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Gère la requête PUT pour mettre à jour un élève par son ID.
     *
     * @param id         L'ID de l'élève à mettre à jour.
     * @param parameters Les paramètres pour mettre à jour l'élève.
     * @return Une ResponseEntity contenant une ApiResponse avec l'élève mis à jour.
     */
    @RequestMapping(value = "/eleveId/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ApiResponse<Eleve>> mettreAjourEleve(@PathVariable Long id, @RequestParam EditEleveParameters parameters) {

        try {
            String successMessage = "Elève a été mise à jour avec succès. Eleve ID : " + id;
            return ResponseEntity.ok(new ApiResponse<Eleve>(eleveService.mettreAjourUtilisateur(id, parameters), successMessage));
        } catch (KaladewnManagementException.DuplicateEntityException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    /**
     * Gère la requête GET pour chercher un élève par son email.
     *
     * @param email L'email de l'élève à chercher.
     * @return Une ResponseEntity contenant une ApiResponse avec l'élève récupéré par son email.
     */

    @Operation(
            summary = "${api.kalanblow-getion-eleve.get-kalanblow-getion-eleve.description}",
            description = "${api.kalanblow-getion-eleve.get-kalanblow-getion-eleve.notes}"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description =
                    "${api.responseCodes.ok.description}"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description =
                    "${api.responseCodes.badRequest.description}"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description =
                    "${api.responseCodes.notFound.description}"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "422", description =
                    "${api.responseCodes.unprocessableEntity.description}")
    })
    @RequestMapping(value = "/email/{email}", method = RequestMethod.GET)
    public ResponseEntity<ApiResponse<Eleve>> chercherEleveParEmail(@PathVariable String email) {

        Optional<Eleve> eleve = eleveService.chercherParEmail(email);

        if (eleve.isPresent()) {
            Eleve eleve1 = eleve.get();

            String successMessage = "Elève récupéré avec  Email : " + email + "succès";

            ResponseEntity<ApiResponse<Eleve>> response = ResponseEntity.ok(new ApiResponse<Eleve>(eleve1, successMessage));
            return response;
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Récupère un élève en utilisant son numéro de téléphone.
     *
     * @param telephone Le numéro de téléphone de l'élève à récupérer.
     * @return Une ResponseEntity contenant un ApiResponse contenant l'élève si trouvé avec succès, sinon une réponse 404 (Not Found).
     */

    @Operation(
            summary = "${api.kalanblow-getion-eleve.get-kalanblow-getion-eleve.description}",
            description = "${api.kalanblow-getion-eleve.get-kalanblow-getion-eleve.notes}"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description =
                    "${api.responseCodes.ok.description}"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description =
                    "${api.responseCodes.badRequest.description}"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description =
                    "${api.responseCodes.notFound.description}"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "422", description =
                    "${api.responseCodes.unprocessableEntity.description}")
    })
    @RequestMapping(value = "/telephone/{telephone}", method = RequestMethod.GET)
    public ResponseEntity<ApiResponse<Eleve>> recupererEleveParTelephone(@PathVariable String telephone) {

        Optional<Eleve> elev = eleveService.recupererEleveParTelephone(telephone);

        if (elev.isPresent()) {
            Eleve telephoneEleve = elev.get();

            String successMessage = "Elève récupéré avec le numéro de téléphone suivant: " + telephone + " avec succès";

            ResponseEntity<ApiResponse<Eleve>> response = ResponseEntity.ok(new ApiResponse<Eleve>(telephoneEleve, successMessage));
            return response;
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    /**
     * Gère la requête GET pour chercher un élève par son numéro INE.
     *
     * @param numeroIne Le numéro INE de l'élève à chercher.
     * @return Une ResponseEntity contenant une ApiResponse avec l'élève récupéré par son numéro INE.
     */
    @RequestMapping(value = "/numeroIdentification/{numeroIne}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Eleve>> chercherEleveParSonNumeroIne(@PathVariable String numeroIne) {
        log.info("Elève trouvé avec : numeroIne={}", numeroIne);
        try {
            return ResponseEntity.ok(new ApiResponse<Eleve>(eleveService.chercherParSonNumeroIne(numeroIne).get(), "Elève récupérer par numero ine avec succès."));
        } catch (KaladewnManagementException.EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


    /**
     * Récupère une liste d'élèves en utilisant leur prénom et nom de famille.
     *
     * @param prenom       Le prénom de l'élève à rechercher.
     * @param nomDeFamille Le nom de famille de l'élève à rechercher.
     * @return Une liste d'élèves correspondant aux critères de recherche, encapsulée dans une ResponseEntity contenant un ApiResponse, ou null en cas d'exception KaladewnManagementException.EntityNotFoundException.
     */
    @RequestMapping(value = "/eleve/{prenom}/{nomDeFamille}")
    public ResponseEntity<ApiResponse<Optional<Eleve>>> recupererEleveParPrenomNom(@PathVariable("prenom") String prenom, @PathVariable("nomDeFamille") String nomDeFamille) {
        log.info("Elève trouvé avec : prenom  et nom={},{}", prenom, nomDeFamille);
        try {
            return ResponseEntity.ok(new ApiResponse<Optional<Eleve>>(eleveService.recupererEleveParPrenomEtNom(prenom, nomDeFamille), "Eleve récupéré par son prénom et nom de famille."));
        } catch (KaladewnManagementException.EntityNotFoundException e) {

            //TODO  à finir
            return null;
        }
    }


    /**
     * @param eleveId
     * @return Une liste d'élèves correspondant aux critères de recherche, encapsulée dans une ResponseEntity contenant un ApiResponse, ou null en cas d'exception KaladewnManagementException.EntityNotFoundException.
     */
    @RequestMapping(value = "/supprimerEleve/{eleveId}", method = RequestMethod.DELETE)
    public ResponseEntity<ApiResponse<List<Eleve>>> supprimerEleveParSonIdentifiant(@PathVariable long eleveId) {

        eleveService.supprimerEleveParSonId(eleveId);
        return ResponseEntity.ok(new ApiResponse<>());
    }
}
