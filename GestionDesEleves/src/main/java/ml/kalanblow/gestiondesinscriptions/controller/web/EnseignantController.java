package ml.kalanblow.gestiondesinscriptions.controller.web;

import ml.kalanblow.gestiondesinscriptions.model.Cours;
import ml.kalanblow.gestiondesinscriptions.model.Email;
import ml.kalanblow.gestiondesinscriptions.model.Enseignant;
import ml.kalanblow.gestiondesinscriptions.response.CreateEnseignantFormData;
import ml.kalanblow.gestiondesinscriptions.response.EditEnseignantFormData;
import ml.kalanblow.gestiondesinscriptions.service.CoursService;
import ml.kalanblow.gestiondesinscriptions.service.EnseignantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/enseignant")
public class EnseignantController {

    private final EnseignantService enseignantService;
    private final CoursService coursService;

    @Autowired
    public EnseignantController(EnseignantService enseignantService, CoursService coursService) {
        this.enseignantService = enseignantService;
        this.coursService = coursService;
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("enseignant", new CreateEnseignantFormData());
        model.addAttribute("coursDEnseignements", coursService.getAllCours());
        return "enseignant/create";
    }

    @PostMapping("/create")
    public String createEnseignant(@ModelAttribute  CreateEnseignantFormData createEnseignantFormData) {
        enseignantService.creerEnseignant(createEnseignantFormData.toEnseignantParameters());
        return "redirect:/enseignant/all";
    }

    @GetMapping("/all")
    public String getAllEnseignants(Model model) {
        Set<Enseignant> enseignants = enseignantService.getAllEnseignants();
        model.addAttribute("enseignants", enseignants);
        return "enseignant/all";
    }

    @GetMapping("/edit/{id}")
    public String editEnseignant(@PathVariable Long id, Model model) {
        Optional<Enseignant> enseignantOpt = enseignantService.findById(id);
        if (enseignantOpt.isPresent()) {
            model.addAttribute("enseignant", enseignantOpt.get());
            model.addAttribute("coursDEnseignements", coursService.getAllCours());
            return "enseignant/edit";
        } else {

            return "redirect:/enseignant/all";
        }
    }

    @PostMapping("/edit/{id}")
    public String doEditEnseignant(@PathVariable Long id, @ModelAttribute EditEnseignantFormData  editEnseignantFormData) {

        Optional<Enseignant> enseignant= enseignantService.findById(id);

        editEnseignantFormData.setPasswordRepeated(enseignant.get().getPassword());

        editEnseignantFormData.setPassword(enseignant.get().getPassword());
        editEnseignantFormData.setMaritalStatus(enseignant.get().getMaritalStatus());
        editEnseignantFormData.setJoursDisponibles(enseignant.get().getJoursDisponibles());
        editEnseignantFormData.setLeMatricule(enseignant.get().getLeMatricule());
        editEnseignantFormData.setHeureFinDisponibilite(enseignant.get().getHeureFinDisponibilite());
        editEnseignantFormData.setHeureDebutDisponibilite(enseignant.get().getHeureDebutDisponibilite());
        editEnseignantFormData.setUserRole(enseignant.get().getRoles().stream().iterator().next());
        editEnseignantFormData.setPrenom(enseignant.get().getUserName().getPrenom());
        editEnseignantFormData.setPhoneNumber(enseignant.get().getPhoneNumber().asString());
        editEnseignantFormData.setNomDeFamille(enseignant.get().getUserName().getNomDeFamille());
        editEnseignantFormData.setModifyDate(enseignant.get().getCreatedDate());
        editEnseignantFormData.setHoraireClasses(enseignant.get().getDisponibilites());
        editEnseignantFormData.setEtablissement(enseignant.get().getEtablissement());
        editEnseignantFormData.setCoursDEnseignements(enseignant.get().getCoursDEnseignements());
        editEnseignantFormData.setDateDeNaissance(enseignant.get().getDateDeNaissance());
        editEnseignantFormData.setAddress(enseignant.get().getAddress());
        editEnseignantFormData.setCreatedDate(enseignant.get().getCreatedDate());
        editEnseignantFormData.setEmail(enseignant.get().getEmail().asString());
        editEnseignantFormData.setGender(enseignant.get().getGender());
        enseignantService.editerEnseignant(id, editEnseignantFormData.toEnseignantParameters());
        return "redirect:/enseignant/all";
    }

    @GetMapping("/delete/{id}")
    public String deleteEnseignant(@PathVariable Long id) {
        enseignantService.deleteById(id);
        return "redirect:/enseignant/all";
    }

    @GetMapping("/search")
    public String searchEnseignant(@RequestParam Long coursId,
                                   @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime heureDebut,
                                   @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime heureFin,
                                   Model model) {
        Optional<Cours> coursOpt = coursService.getCoursById(coursId);

        if (coursOpt.isPresent()) {
            Cours cours = coursOpt.get();
            Optional<Enseignant> enseignantOpt = enseignantService.getEnseignantByCoursDEnseignementsAndHeureDebutDisponibiliteAndAndHeureFinDisponibilite(cours.getEnseignant(), heureDebut, heureFin);

            if (enseignantOpt.isPresent()) {
                model.addAttribute("enseignant", enseignantOpt.get());
                return "enseignant/search-result";
            }
        }

        return "enseignant/search-result-not-found";
    }

    @GetMapping("/searchByJoursDisponibles")
    public String searchEnseignantByJoursDisponibles(@RequestParam Long coursId,
                                                     @RequestParam Long enseignantId,
                                                     Model model) {
        Optional<Cours> coursOpt = coursService.getCoursById(coursId);
        Optional<Enseignant> enseignantOpt = enseignantService.findById(enseignantId);

        if (coursOpt.isPresent() && enseignantOpt.isPresent()) {
            Cours cours = coursOpt.get();
            Enseignant enseignant = enseignantOpt.get();

            Optional<Enseignant> result = enseignantService.getEnseignantByCoursDEnseignementsAndJoursDisponibles(cours, enseignant);

            if (result.isPresent()) {
                model.addAttribute("enseignant", result.get());
                return "enseignant/search-result";
            }
        }

        // Gérer le cas où le cours ou l'enseignant n'est pas trouvé.
        return "enseignant/search-result-not-found";
    }

    @GetMapping("/searchByDateCreated")
    public String searchEnseignantByDateCreated(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
                                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin,
                                                Model model) {
        Optional<Enseignant> result = enseignantService.getEnseignantByCreatedDateIsBetween(debut, fin);

        if (result.isPresent()) {
            model.addAttribute("enseignant", result.get());
            return "enseignant/search-result";
        }

        return "enseignant/search-result-not-found";
    }
    @GetMapping("/getEnseignantByCours")
    public String getEnseignantByCours(@RequestParam Long coursId, Model model) {
        Optional<Cours> coursOpt = coursService.getCoursById(coursId);

        if (coursOpt.isPresent()) {
            Cours cours = coursOpt.get();

            Optional<Enseignant> result = enseignantService.getEnseignantByCoursDEnseignements(cours);

            if (result.isPresent()) {
                model.addAttribute("enseignant", result.get());
                return "enseignant/search-result";
            }
        }

        return "enseignant/search-result-not-found";
    }

    @GetMapping("/countEnseignantsByCours")
    public String countEnseignantsByCours(@RequestParam Long coursId, Model model) {
        Optional<Cours> coursOpt = coursService.getCoursById(coursId);

        if (coursOpt.isPresent()) {
            Cours cours = coursOpt.get();

            Optional<Long> result = enseignantService.countDistinctByCoursDEnseignements(cours);

            if (result.isPresent()) {
                model.addAttribute("count", result.get());
                return "enseignant/count-result";
            }
        }

        return "enseignant/count-result-not-found";
    }

    @GetMapping("/searchEnseignantByEmail")
    public String searchEnseignantByEmail(@RequestParam String email, Model model) {
        Email searchEmail = new Email(email);

        Optional<Enseignant> result = enseignantService.searchAllByEmailIsLike(searchEmail);

        if (result.isPresent()) {
            model.addAttribute("enseignant", result.get());
            return "enseignant/search-result";
        }

        return "enseignant/search-result-not-found";
    }

}
