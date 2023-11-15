package ml.kalanblow.gestiondesinscriptions.controller.web;

import ml.kalanblow.gestiondesinscriptions.model.Salle;
import ml.kalanblow.gestiondesinscriptions.request.EditSalleParameters;
import ml.kalanblow.gestiondesinscriptions.service.SalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/salles")
public class SalleController {

    private final SalleService salleService;

    @Autowired
    public SalleController(SalleService salleService) {
        this.salleService = salleService;
    }

    @GetMapping("/all")
    public String getAllSalles(Model model) {
        Set<Salle> salles = salleService.getAllSalles();
        model.addAttribute("salles", salles);
        return "salles/all";

    }

    @GetMapping("/edit/{id}")
    public String editSalleDeClasse(@PathVariable Long id, Model model) {
        Optional<Salle> salleOpt = salleService.getTimeslotById(id);
        salleOpt.ifPresent(salle -> model.addAttribute("salle", salle));
        return "salles/edit";

    }

    @PostMapping("/edit/{id}")
    public String doEditSalleDeClasse(@PathVariable Long id, @ModelAttribute EditSalleParameters editSalleParameters) {
        salleService.doEditSalleDeClasse(id, editSalleParameters);
        return "redirect:/salles/all";

    }

    @GetMapping("/delete/{id}")
    public String deleteSalleDeClasse(@PathVariable Long id) {
        salleService.deleteSalleDeClasse(id);
        return "redirect:/salles/all";

    }
}
