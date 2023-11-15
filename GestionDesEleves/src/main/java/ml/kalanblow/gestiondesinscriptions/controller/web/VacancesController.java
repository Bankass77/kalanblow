package ml.kalanblow.gestiondesinscriptions.controller.web;

import ml.kalanblow.gestiondesinscriptions.model.Vacances;
import ml.kalanblow.gestiondesinscriptions.request.CreateVacancesParameters;
import ml.kalanblow.gestiondesinscriptions.request.EditVacancesParameters;
import ml.kalanblow.gestiondesinscriptions.service.VacancesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/vacances")
public class VacancesController {

    private final VacancesService vacancesService;

    @Autowired
    public VacancesController(VacancesService vacancesService) {
        this.vacancesService = vacancesService;
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("createVacancesParameters", new CreateVacancesParameters());
        return "vacances/create";

    }

    @PostMapping("/create")
    public String createVacances(@ModelAttribute CreateVacancesParameters createVacancesParameters) {
        vacancesService.createVacances(createVacancesParameters);
        return "redirect:/vacances/all";

    }

    @GetMapping("/all")
    public String getAllVacances(Model model) {
        Set<Vacances> vacances = vacancesService.getAllVacances();
        model.addAttribute("vacances", vacances);
        return "vacances/all";

    }

    @GetMapping("/edit/{id}")
    public String editVacances(@PathVariable Long id, Model model) {
        Optional<Vacances> vacancesOpt = vacancesService.getVacancesById(id);
        vacancesOpt.ifPresent(vacances -> model.addAttribute("vacances", vacances));
        return "vacances/edit";

    }

    @PostMapping("/edit/{id}")
    public String doEditVacances(@PathVariable Long id, @ModelAttribute EditVacancesParameters editVacancesParameters) {
        vacancesService.doEditVacances(id, editVacancesParameters);
        return "redirect:/vacances/all";

    }

    @GetMapping("/delete/{id}")
    public String deleteVacances(@PathVariable Long id) {
        vacancesService.deleteVacances(id);
        return "redirect:/vacances/all";

    }
}
