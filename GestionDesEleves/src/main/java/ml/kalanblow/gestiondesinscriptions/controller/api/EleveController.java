package ml.kalanblow.gestiondesinscriptions.controller.api;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import ml.kalanblow.gestiondesinscriptions.exception.KaladewnManagementException;
import ml.kalanblow.gestiondesinscriptions.model.Eleve;
import ml.kalanblow.gestiondesinscriptions.model.Email;
import ml.kalanblow.gestiondesinscriptions.model.JwtResponse;
import ml.kalanblow.gestiondesinscriptions.request.AuthenticationRequest;
import ml.kalanblow.gestiondesinscriptions.request.CreateEleveParameters;
import ml.kalanblow.gestiondesinscriptions.request.EditEleveParameters;
import ml.kalanblow.gestiondesinscriptions.request.RefreshTokenRequest;
import ml.kalanblow.gestiondesinscriptions.response.AuthenticationResponse;
import ml.kalanblow.gestiondesinscriptions.response.RefreshTokenResponse;
import ml.kalanblow.gestiondesinscriptions.security.jwt.JwtHelper;
import ml.kalanblow.gestiondesinscriptions.service.EleveService;
import ml.kalanblow.gestiondesinscriptions.service.RefreshTokenService;
import ml.kalanblow.gestiondesinscriptions.util.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
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
@PreAuthorize("hasAnyRole('ADMIN','TEACHER', 'STUDENT','USER')")
@RequestMapping("/api/v1/kalanden")
@Tag(name = "Eleve", description = "Rest API pour décrire les informations des élèves de Kalanblow.")
public class EleveController {

    private final EleveService eleveService;
    private final AuthenticationManager authenticationManager;
    private final JwtHelper helper;
    private final RefreshTokenService refreshTokenService;
    /**
     * Constructeur de la classe EleveController.
     *
     * @param userService Le service d'élèves utilisé pour effectuer les opérations.
     */

    public EleveController(EleveService userService, JwtHelper jwtHelper,AuthenticationManager authenticationManager,
                           RefreshTokenService refreshTokenService) {
        this.eleveService = userService;
        this.helper= jwtHelper;
        this.authenticationManager=authenticationManager;
        this.refreshTokenService= refreshTokenService;
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
    @GetMapping
    @PreAuthorize("hasAuthority('READ_PRIVILEGE') and hasRole('ADMIN')")
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
    @PostMapping (value = "/")
    public ResponseEntity<ApiResponse<Eleve>> creerNouveauEleve(@RequestBody CreateEleveParameters createEleveParameters) {

        try {

            Eleve newEleve = eleveService.ajouterUnEleve(createEleveParameters);

            return ResponseEntity.ok(new ApiResponse<>(newEleve, "Elève crée avec succès."));

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
    @GetMapping(value = "/cleIdentification/{eleveId}",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Eleve>> obtenirEleveParSonId(@PathVariable Long eleveId) {
        log.info("Un Elève a été  trouvé avec : eleveId={}", eleveId);
        try {
            String successMessage = "Elève récupéré avec succès. Eleve ID : " + eleveId;
            return ResponseEntity.ok(new ApiResponse<>(eleveService.obtenirEleveParSonId(eleveId).get(), successMessage));
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
    @PutMapping(value = "/eleveId/{id}")
    @PreAuthorize("hasAuthority('UPDATE_PRIVILEGE') and hasAnyRole('ADMIN','USER')")
    public ResponseEntity<ApiResponse<Eleve>> mettreAjourEleve(@PathVariable Long id, @RequestParam EditEleveParameters parameters) {

        try {
            String successMessage = "Elève a été mise à jour avec succès. Eleve ID : " + id;
            Eleve eleve = new Eleve();
            eleve.setIneNumber(parameters.getStudentIneNumber());
            eleve.setDateDeNaissance(parameters.getDateDeNaissance());
            eleve.setAge(parameters.getAge());
             eleve.setPere(parameters.getPere());
             eleve.setMere(parameters.getMere());
            eleve.setAbsences(parameters.getAbsences());
            eleve.setEtablissement(parameters.getEtablissement());
            return ResponseEntity.ok(new ApiResponse<>(eleveService.mettreAjourUtilisateur(id, parameters), successMessage));
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
    @GetMapping(value = "/email/{email}")
    public ResponseEntity<ApiResponse<Eleve>> chercherEleveParEmail(@PathVariable String email) {

        Optional<Eleve> eleve = eleveService.chercherParEmail(email);

        if (eleve.isPresent()) {
            Eleve eleve1 = eleve.get();

            String successMessage = "Elève récupéré avec  Email : " + email + "succès";

            return ResponseEntity.ok(new ApiResponse<>(eleve1, successMessage));
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
    @GetMapping(value = "/telephone/{telephone}")
    public ResponseEntity<ApiResponse<Eleve>> recupererEleveParTelephone(@PathVariable String telephone) {

        Optional<Eleve> elev = eleveService.recupererEleveParTelephone(telephone);

        if (elev.isPresent()) {
            Eleve telephoneEleve = elev.get();

            String successMessage = "Elève récupéré avec le numéro de téléphone suivant: " + telephone + " avec succès";

            return ResponseEntity.ok(new ApiResponse<>(telephoneEleve, successMessage));
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
    @GetMapping(value = "/numeroIdentification/{numeroIne}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Eleve>> chercherEleveParSonNumeroIne(@PathVariable String numeroIne) {
        log.info("Elève trouvé avec : numeroIne={}", numeroIne);
        try {
            return ResponseEntity.ok(new ApiResponse<>(eleveService.chercherParSonNumeroIne(numeroIne).get(), "Elève récupérer par numero ine avec succès."));
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
            return ResponseEntity.ok(new ApiResponse<>(eleveService.recupererEleveParPrenomEtNom(prenom, nomDeFamille), "Eleve récupéré par son prénom et nom de famille."));
        } catch (KaladewnManagementException.EntityNotFoundException e) {

            return null;
        }
    }


    /**
     * @param eleveId
     * @return Une liste d'élèves correspondant aux critères de recherche, encapsulée dans une ResponseEntity contenant un ApiResponse, ou null en cas d'exception KaladewnManagementException.EntityNotFoundException.
     */
    @DeleteMapping(value = "/supprimerEleve/{eleveId}")
    @PreAuthorize("hasAuthority('DELETE_PRIVILEGE') and hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<Eleve>>> supprimerEleveParSonIdentifiant(@PathVariable long eleveId) {

        eleveService.supprimerEleveParSonId(eleveId);
        return ResponseEntity.ok(new ApiResponse<>());
    }

    @PostMapping("/register")
    public ResponseEntity<?> postUser(@RequestBody CreateEleveParameters createEleveParameters){
        try{
            eleveService.ajouterUnEleve(createEleveParameters);
            return new ResponseEntity<>(createEleveParameters, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody CreateEleveParameters createEleveParameters) {

        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(createEleveParameters.getEmail(), createEleveParameters.getPassword()));
            String email = authentication.getName();
            Eleve eleve = new Eleve();
            eleve.setEmail(new Email(email));
            String token = helper.generateToken((UserDetails) eleve);

            JwtResponse jwtResponse = new JwtResponse(email, token);
            return ResponseEntity.ok(jwtResponse);
        } catch (AuthenticationException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @PostMapping("/authenticate")
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
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse authenticationResponse = eleveService.authenticate(request);
        ResponseCookie jwtCookie = helper.generateJwtCookie(authenticationResponse.getAccessToken());
        ResponseCookie refreshTokenCookie = refreshTokenService.generateRefreshTokenCookie(authenticationResponse.getRefreshToken());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE,jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE,refreshTokenCookie.toString())
                .body(authenticationResponse);
    }
    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(refreshTokenService.generateNewToken(request));
    }

    @PostMapping("/refresh-token-cookie")
    public ResponseEntity<Void> refreshTokenCookie(HttpServletRequest request) {
        String refreshToken = refreshTokenService.getRefreshTokenFromCookies(request);
        RefreshTokenResponse refreshTokenResponse = refreshTokenService
                .generateNewToken(new RefreshTokenRequest(refreshToken));
        ResponseCookie NewJwtCookie = helper.generateJwtCookie(refreshTokenResponse.getAccessToken());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, NewJwtCookie.toString())
                .build();
    }
    @GetMapping("/info")
    public Authentication getAuthentication(@RequestBody AuthenticationRequest request){
        return     authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request){
        String refreshToken = refreshTokenService.getRefreshTokenFromCookies(request);
        if(refreshToken != null) {
            refreshTokenService.deleteByToken(refreshToken);
        }
        ResponseCookie jwtCookie = helper.getCleanJwtCookie();
        ResponseCookie refreshTokenCookie = refreshTokenService.getCleanRefreshTokenCookie();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE,jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE,refreshTokenCookie.toString())
                .build();

    }
}
