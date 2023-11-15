package ml.kalanblow.gestiondesinscriptions.controller.web;

import ml.kalanblow.gestiondesinscriptions.model.Cours;
import ml.kalanblow.gestiondesinscriptions.request.CreateCoursParameters;
import ml.kalanblow.gestiondesinscriptions.response.CreateCoursFormData;
import ml.kalanblow.gestiondesinscriptions.response.EditCoursFormData;
import ml.kalanblow.gestiondesinscriptions.service.*;
import ml.kalanblow.gestiondesinscriptions.validation.CreateCoursValidationGroupSequence;
import ml.kalanblow.gestiondesinscriptions.validation.EditCoursValidationGroupSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/cours")
public class CoursController {

    private final CoursService coursService;
    private final EnseignantService enseignantService;
    private final SalleService salleService;
    private final GroupService groupService;
    private final TimeslotService timeslotService;

    @Autowired
    public CoursController(
            CoursService coursService,
            EnseignantService enseignantService,
            SalleService salleService,
            GroupService groupService,
            TimeslotService timeslotService
    ) {
        this.coursService = coursService;
        this.enseignantService = enseignantService;
        this.salleService = salleService;
        this.groupService = groupService;
        this.timeslotService = timeslotService;
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("cours", new CreateCoursParameters());
        model.addAttribute("enseignants", enseignantService.getAllEnseignants());
        model.addAttribute("salles", salleService.getAllSalles());
        model.addAttribute("groups", groupService.getAllGroups());
        model.addAttribute("timeslots", timeslotService.getAllTimeslots());
        return "cours/create";
    }

    @PostMapping("/create")
    public String createCours(@Validated(CreateCoursValidationGroupSequence.class) @ModelAttribute CreateCoursFormData createCoursFormData) {
        coursService.creerCoursDEnseignement(createCoursFormData.toCoursDEnseignementParameters());
        return "redirect:/cours/all";
    }

    @GetMapping("/all")
    public String getAllCours(Model model) {
        Set<Cours> cours = coursService.getAllCours();
        model.addAttribute("cours", cours);
        return "cours/all";
    }

    @GetMapping("/edit/{id}")
    public String editCours(@PathVariable Long id, Model model) {
        Optional<Cours> coursOpt = coursService.getCoursById(id);
        if (coursOpt.isPresent()) {
            model.addAttribute("cours", coursOpt.get());
            model.addAttribute("enseignants", enseignantService.getAllEnseignants());
            model.addAttribute("salles", salleService.getAllSalles());
            model.addAttribute("groups", groupService.getAllGroups());
            model.addAttribute("timeslots", timeslotService.getAllTimeslots());
            return "cours/edit";
        } else {
            // Gérer le cas où le cours n'est pas trouvé.
            return "redirect:/cours/all";
        }
    }

    @PostMapping("/edit/{id}")
    public String doEditCours(@PathVariable Long id,@Validated(EditCoursValidationGroupSequence.class) @ModelAttribute EditCoursFormData editCoursFormData) {

        Optional<Cours> cours= coursService.getCoursById(id);

        editCoursFormData.setNomDuCours(cours.get().getNomDuCours());
        editCoursFormData.setMatiere(cours.get().getMatiere());
        editCoursFormData.setNiveau(cours.get().getNiveau());
        editCoursFormData.setEnseignant(cours.get().getEnseignant());
        editCoursFormData.setAnneeScolaire(cours.get().getAnneeScolaire());
        editCoursFormData.setAbsenceEleves(cours.get().getAbsenceEleves());
        editCoursFormData.setHoraireClasses(cours.get().getHoraires());
        editCoursFormData.setSalleDeClasse(cours.get().getSalle());
        coursService.creerCoursDEnseignement(editCoursFormData.toCoursDEnseignement());
        return "redirect:/cours/all";
    }

    @GetMapping("/delete/{id}")
    public String deleteCours(@PathVariable Long id) {
        coursService.deleteCours(id);
        return "redirect:/cours/all";
    }
}
