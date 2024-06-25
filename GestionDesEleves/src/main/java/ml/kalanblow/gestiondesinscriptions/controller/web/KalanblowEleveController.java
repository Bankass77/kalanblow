package ml.kalanblow.gestiondesinscriptions.controller.web;

import lombok.extern.slf4j.Slf4j;
import ml.kalanblow.gestiondesinscriptions.enums.EditMode;
import ml.kalanblow.gestiondesinscriptions.enums.Gender;
import ml.kalanblow.gestiondesinscriptions.enums.MaritalStatus;
import ml.kalanblow.gestiondesinscriptions.enums.UserRole;
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

    /**
     * Récupère et affiche la liste des élèves dans une vue.
     *
     * @param model
     *         Le modèle à utiliser pour transmettre les données à la vue.
     * @param pageable
     *         Les paramètres de pagination pour la liste des élèves, y compris le tri par prénom puis nom de famille.
     * @return Une chaîne représentant le nom de la vue à afficher.
     */
    @GetMapping
    public ModelAndView listDesEleves(Model model,
            @SortDefault.SortDefaults({ @SortDefault("userName.prenom"), @SortDefault("userName.nomDeFamille") }) Pageable pageable) {

        Page<Eleve> elevesPage = eleveService.obtenirListeElevePage(pageable);
        boolean isFirstPage = elevesPage.getNumber() == 0;

        ModelAndView modelAndView = new ModelAndView("eleves/listeDesEleves");
        modelAndView.addObject("eleves", elevesPage);
        modelAndView.addObject("isFirstPage", isFirstPage);

        return modelAndView;
    }

    /**
     * Affiche le formulaire d'ajout d'un nouvel élève.
     *
     * @param modelAndView
     *         Le modèle à utiliser pour transmettre les données à la vue.
     * @return Une chaîne représentant le nom de la vue du formulaire d'ajout d'un nouvel élève.
     */

    // tag::create-get[]
    //TODO il faudra modifier le role à ADMIN
    @GetMapping("/eleve/ajouter")
    @Secured("ROLE_STUDENT")
    public ModelAndView ajouterNouvelEleve(ModelAndView modelAndView) {

        modelAndView = new ModelAndView("eleves/editerEleve");
        modelAndView.addObject("eleve", new CreateEleveFormData());
        modelAndView.addObject("genders", List.of(Gender.MALE, Gender.FEMALE));
        modelAndView.addObject("rolesPossibles", List.of(UserRole.STUDENT.values()));
        modelAndView.addObject("possiblesMaritalStatus", List.of(MaritalStatus.values()));
        modelAndView.addObject("editMode", EditMode.CREATE);

        return modelAndView;
    }
    // end::create-get[]
    /**
     * Crée un nouvel élève en utilisant les données fournies dans le formulaire.
     *
     * @param formData
     *         Les données du nouvel élève à créer.
     * @param bindingResult
     *         Le résultat de la liaison des données et les erreurs éventuelles.
     * @param modelAndView
     *         Le modèle à utiliser pour transmettre les données à la vue.
     * @return Une chaîne représentant le nom de la vue après la création de l'élève.
     */
    // tag::create-post[]
    @PostMapping("/eleve/ajouter")
    @Secured("ROLE_STUDENT")
    public ModelAndView crerUnNouvelEleve(@Validated(CreateUserValidationGroupSequence.class) @ModelAttribute("eleve") CreateEleveFormData formData,
            BindingResult bindingResult, ModelAndView modelAndView) {

        modelAndView = new ModelAndView("redirect:/eleves/listeDesEleves");
        if(bindingResult.hasErrors()) {
            modelAndView.addObject("genders", List.of(Gender.MALE, Gender.FEMALE));
            modelAndView.addObject("rolesPossibles", List.of(UserRole.STUDENT.values()));
            modelAndView.addObject("possiblesMaritalStatus", List.of(MaritalStatus.values()));
            modelAndView.addObject("editMode", EditMode.CREATE);
            return new ModelAndView("redirect: /eleves/editerEleve");
        }

        eleveService.ajouterUnEleve(formData.toEleveParameters());

        return modelAndView;
    }

    // end::create-post[]
    // end::create-post[]

    /**
     * Affiche le formulaire de mise à jour des informations d'un élève existant.
     *
     * @param id
     *         L'identifiant de l'élève à mettre à jour.
     * @param modelAndView
     *         Le modèle à utiliser pour transmettre les données à la vue.
     * @return Une chaîne représentant le nom de la vue du formulaire de mise à jour d'un élève.
     */
    // tag::edit-get[]
    @GetMapping("/eleve/{id}")
    public ModelAndView editEleveForm(@PathVariable("id") long id, ModelAndView modelAndView) {

        modelAndView = new ModelAndView("eleves/editerEleve");
        Optional<Eleve> eleve = eleveService.obtenirEleveParSonId(id);
        modelAndView.addObject("eleve", EditEleveFormData.fromEleve(eleve.get()));
        modelAndView.addObject("genders", List.of(Gender.MALE, Gender.FEMALE));
        modelAndView.addObject("rolesPossibles", List.of(UserRole.STUDENT.values()));
        modelAndView.addObject("possiblesMaritalStatus", List.of(MaritalStatus.values()));
        modelAndView.addObject("editMode", EditMode.UPDATE);
        return modelAndView;
    }

    /**
     * Met à jour les informations d'un élève existant en utilisant les données fournies dans le formulaire.
     *
     * @param id
     *         L'identifiant de l'élève à mettre à jour.
     * @param formData
     *         Les données de l'élève à mettre à jour.
     * @param bindingResult
     *         Le résultat de la liaison des données et les erreurs éventuelles.
     * @param modelAndView
     *         Le modèle à utiliser pour transmettre les données à la vue.
     * @return Une chaîne représentant le nom de la vue après la mise à jour des informations de l'élève.
     */
    // tag::edit-post[]
    @PostMapping("/eleve/{id}")
    @Secured("ROLE_STUDENT")
    public ModelAndView aModifierElever(@PathVariable("id") long id,
            @Validated(EditUserValidationGroupSequence.class) @ModelAttribute("eleve") EditEleveFormData formData, BindingResult bindingResult,
            ModelAndView modelAndView) {
        modelAndView = new ModelAndView("eleves/editerEleve");
        if(bindingResult.hasErrors()) {

            modelAndView.addObject("genders", List.of(Gender.MALE, Gender.FEMALE));
            modelAndView.addObject("rolesPossibles", List.of(UserRole.STUDENT.values()));
            modelAndView.addObject("possiblesMaritalStatus", List.of(MaritalStatus.values()));
            modelAndView.addObject("editMode", EditMode.UPDATE);

            return modelAndView;
        }

        eleveService.mettreAjourUtilisateur(id, formData.toEleveParameters());

        return new ModelAndView("redirect:/eleves/listeDesEleves");
    }
    // end::edit-post[]

    /**
     * Supprime un élève existant en fonction de son identifiant.
     *
     * @param id
     *         L'identifiant de l'élève à supprimer.
     * @param redirectAttributes
     *         Les attributs de redirection pour transmettre des messages ou des informations après la suppression.
     * @return Une chaîne représentant l'URL ou la vue de redirection après la suppression de l'élève.
     */
    // tag::delete-post[]
    @PostMapping("/eleve/{id}/delete")
    @Secured("ROLE_STUDENT")
    public String aSupprimerEleve(@PathVariable("id") long id, RedirectAttributes redirectAttributes) {

        Optional<Eleve> eleve = eleveService.obtenirEleveParSonId(id);

        eleveService.supprimerEleveParSonId(eleve.get().getId());

        redirectAttributes.addFlashAttribute("deleteEleveUsername", eleve.get().getUserName().getFullName());

        return "redirect:/eleves/listDesEleves";
    }
    // end::delete-post[]

    // tag::model-attributes[]
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

    // end::model-attributes[]
}
