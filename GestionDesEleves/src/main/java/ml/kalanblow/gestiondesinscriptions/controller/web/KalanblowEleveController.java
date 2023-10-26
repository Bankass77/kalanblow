package ml.kalanblow.gestiondesinscriptions.controller.web;


import lombok.extern.slf4j.Slf4j;
import ml.kalanblow.gestiondesinscriptions.enums.EditMode;
import ml.kalanblow.gestiondesinscriptions.enums.Gender;
import ml.kalanblow.gestiondesinscriptions.enums.MaritalStatus;
import ml.kalanblow.gestiondesinscriptions.enums.UserRole;
import ml.kalanblow.gestiondesinscriptions.exception.KaladewnManagementException;
import ml.kalanblow.gestiondesinscriptions.model.Eleve;
import ml.kalanblow.gestiondesinscriptions.response.CreateEleveFormData;
import ml.kalanblow.gestiondesinscriptions.response.EditEleveFormData;
import ml.kalanblow.gestiondesinscriptions.service.EleveService;
import ml.kalanblow.gestiondesinscriptions.validation.CreateUserValidationGroupSequence;
import ml.kalanblow.gestiondesinscriptions.validation.EditUserValidationGroupSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping("/kalanden")
public class KalanblowEleveController {

    private final EleveService eleveService;

    @Autowired
    public KalanblowEleveController(EleveService eleveService) {
        this.eleveService = eleveService;
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
    public void crerUnNouvelEleve(@Validated(CreateUserValidationGroupSequence.class) @RequestBody CreateEleveFormData formData,
                                  BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new KaladewnManagementException.EntityNotFoundException("Invalid form data");
        }

        eleveService.ajouterUnEleve(formData.toEleveParameters());
    }

    @GetMapping("/eleve/{id}")
    public ModelAndView editEleveForm(@PathVariable("id") long id, ModelAndView modelAndView) {

        Optional<Eleve> eleve = eleveService.obtenirEleveParSonId(id);
        modelAndView = new ModelAndView("eleves/editerEleve");
        modelAndView.addObject("editEleveFormData", EditEleveFormData.fromUser(eleve.get()));
        modelAndView.addObject("genders", List.of(Gender.MALE, Gender.FEMALE));
        modelAndView.addObject("rolesPossibles", List.of(UserRole.STUDENT.values()));
        modelAndView.addObject("possiblesMaritalStatus", List.of(MaritalStatus.values()));
        modelAndView.addObject("editMode", EditMode.UPDATE);

        return modelAndView;
    }

    @PostMapping("/eleve/{id}")
    @Secured("ROLE_ADMIN")
    public ModelAndView aModifierEleve(@PathVariable("id") long id,
                                       @Validated(EditUserValidationGroupSequence.class) @ModelAttribute("editEleveFormData") EditEleveFormData formData,
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
}
