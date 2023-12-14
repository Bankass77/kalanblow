package ml.kalanblow.gestiondesinscriptions.controller.web;


import lombok.extern.slf4j.Slf4j;
import ml.kalanblow.gestiondesinscriptions.enums.*;
import ml.kalanblow.gestiondesinscriptions.exception.KaladewnManagementException;
import ml.kalanblow.gestiondesinscriptions.model.Eleve;
import ml.kalanblow.gestiondesinscriptions.model.PhoneNumber;
import ml.kalanblow.gestiondesinscriptions.response.AuthenticationResponse;
import ml.kalanblow.gestiondesinscriptions.response.CreateEleveFormData;
import ml.kalanblow.gestiondesinscriptions.response.EditEleveFormData;
import ml.kalanblow.gestiondesinscriptions.security.jwt.JwtHelper;
import ml.kalanblow.gestiondesinscriptions.service.EleveService;
import ml.kalanblow.gestiondesinscriptions.service.RefreshTokenService;
import ml.kalanblow.gestiondesinscriptions.service.impl.EleveServiceImpl;
import ml.kalanblow.gestiondesinscriptions.util.PhoneNumberPropertyEditor;
import ml.kalanblow.gestiondesinscriptions.validation.CreateUserValidationGroupSequence;
import ml.kalanblow.gestiondesinscriptions.validation.EditUserValidationGroupSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequestMapping("/kalanden")
public class KalanblowEleveController {

    private final EleveService eleveService;

    private final JwtHelper jwtHelper;
    private final RefreshTokenService refreshTokenService;

    @Autowired
    public KalanblowEleveController(EleveService eleveService, JwtHelper jwtHelper, RefreshTokenService refreshTokenService) {
        this.eleveService = eleveService;
        this.jwtHelper = jwtHelper;
        this.refreshTokenService = refreshTokenService;
    }

    @GetMapping
    public ModelAndView listDesEleves(Model model, @SortDefault.SortDefaults({@SortDefault("userName.prenom"),
            @SortDefault("userName.nomDeFamille")}) Pageable pageable) {

        Page<Eleve> elevesPage = eleveService.obtenirListeElevePage(pageable);
        boolean isFirstPage = elevesPage.getNumber() == 0;
        ModelAndView modelAndView = new ModelAndView("eleves/listeDesEleves");
        modelAndView.addObject("eleves", elevesPage);
        modelAndView.addObject("isFirstPage", isFirstPage);
        return modelAndView;
    }

    @GetMapping("/eleve/ajouter")
    @Secured("ROLE_ADMIN")
    public ModelAndView ajouterNouvelEleve(ModelAndView modelAndView) {

        modelAndView = new ModelAndView("eleves/editerEleve");
        modelAndView.addObject("eleve", new CreateEleveFormData());
        modelAndView.addObject("genders", List.of(Gender.MALE, Gender.FEMALE));
        modelAndView.addObject("rolesPossibles", List.of(UserRole.STUDENT.values()));
        modelAndView.addObject("possiblesMaritalStatus", List.of(MaritalStatus.values()));
        modelAndView.addObject("editMode", EditMode.CREATE);
        return modelAndView;
    }

    @PostMapping("/eleve/ajouter")
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthenticationResponse crerUnNouvelEleve(@Validated(CreateUserValidationGroupSequence.class) @RequestBody CreateEleveFormData formData,
                                                    BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new KaladewnManagementException.EntityNotFoundException("Invalid form data");
        }

        Eleve nouveauEleve = eleveService.ajouterUnEleve(formData.toEleveParameters());

        // Explicitly generate a token for the newly created student
        String jwt = jwtHelper.generateToken((UserDetails) nouveauEleve);
        String refreshToken = refreshTokenService.createRefreshToken(nouveauEleve.getId()).getToken();

        // Return the authentication response containing the generated tokens
        return AuthenticationResponse.builder()
                .accessToken(jwt)
                .refreshToken(refreshToken)
                .tokenType(TokenType.BEARER.name())
                .roles(nouveauEleve.getRoles().stream()
                        .flatMap(role -> EleveServiceImpl.mapUserRoleToAuthorities.apply(role).stream())
                        .map(SimpleGrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .email(nouveauEleve.getEmail().asString())
                .id(nouveauEleve.getId())
                .build();
    }


    @GetMapping("/eleve/{id}")
    public ModelAndView editEleveForm(@PathVariable("id") long id, ModelAndView modelAndView) {

        Optional<Eleve> eleve = eleveService.obtenirEleveParSonId(id);
        modelAndView = new ModelAndView("eleves/editerEleve");
        modelAndView.addObject("eleve", EditEleveFormData.fromUser(eleve.get()));
        modelAndView.addObject("genders", List.of(Gender.MALE, Gender.FEMALE));
        modelAndView.addObject("rolesPossibles", List.of(UserRole.STUDENT.values()));
        modelAndView.addObject("possiblesMaritalStatus", List.of(MaritalStatus.values()));
        modelAndView.addObject("editMode", EditMode.UPDATE);

        return modelAndView;
    }

    @PostMapping("/eleve/{id}")
    @Secured("ROLE_ADMIN")
    public ModelAndView aModifierEleve(@PathVariable("id") long id,
                                       @Validated(EditUserValidationGroupSequence.class) @ModelAttribute("eleve") EditEleveFormData formData,
                                       BindingResult bindingResult, ModelAndView modelAndView) {

            if (bindingResult.hasErrors()) {

                modelAndView = new ModelAndView("eleves/editerEleve");
                modelAndView.addObject("genders", List.of(Gender.MALE, Gender.FEMALE));
                modelAndView.addObject("rolesPossibles", List.of(UserRole.STUDENT.values()));
                modelAndView.addObject("possiblesMaritalStatus", List.of(MaritalStatus.values()));
                modelAndView.addObject("editMode", EditMode.UPDATE);
                return modelAndView;

            }
            eleveService.mettreAjourUtilisateur(id, formData.toEleveParameters());

            return new ModelAndView("redirect:/kalanden");

    }

    @PostMapping("/eleve/{id}/delete")
    @Secured("ROLE_ADMIN")
    public String aSupprimerEleve(@PathVariable("id") long id, RedirectAttributes redirectAttributes) {

        Optional<Eleve> eleve = eleveService.obtenirEleveParSonId(id);
        eleveService.supprimerEleveParSonId(eleve.get().getId());
        redirectAttributes.addFlashAttribute("deleteEleveUsername", eleve.get().getUserName().getFullName());

        return "redirect:/kalanden";
    }

    @ModelAttribute("genders")
    public List<Gender> genderList() {
        return List.of(Gender.FEMALE, Gender.MALE);
    }

    @ModelAttribute("rolesPossibles")
    public List<UserRole> rolesPossibles() {

        return Arrays.asList(UserRole.STUDENT);
    }

    @ModelAttribute("possiblesMaritalStatus")
    public List<MaritalStatus> possibleStatusMarital() {

        return List.of(MaritalStatus.SINGLE, MaritalStatus.DIVORCED, MaritalStatus.MARRIED);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(PhoneNumber.class, new PhoneNumberPropertyEditor());
    }
}
