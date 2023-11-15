package ml.kalanblow.gestiondesinscriptions.controller.web;

import ml.kalanblow.gestiondesinscriptions.model.Timeslot;
import ml.kalanblow.gestiondesinscriptions.service.TimeslotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/timeslot")
public class TimeslotController {

    private final TimeslotService timeslotService;

    @Autowired
    public TimeslotController(TimeslotService timeslotService) {
        this.timeslotService = timeslotService;
    }

    @GetMapping("/list")
    public ModelAndView showTimeslots() {
        ModelAndView modelAndView = new ModelAndView();
        List<Timeslot> timeslots = new ArrayList<>(timeslotService.getAllTimeslots());
        modelAndView.addObject("timeslots", timeslots);
        modelAndView.setViewName("timeslotList");
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editTimeslot(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            Timeslot timeslot = timeslotService.getTimeslotById(id).orElseThrow(() -> new NotFoundException("Timeslot not found"));
            modelAndView.addObject("timeslot", timeslot);
            modelAndView.setViewName("editTimeslot");
        } catch (Exception e) {
            modelAndView.setViewName("error");
            modelAndView.addObject("error", "Error editing timeslot: " + e.getMessage());
        }
        return modelAndView;
    }

    @PostMapping("/edit/{id}")
    public ModelAndView updateTimeslot(@PathVariable("id") Long id, @ModelAttribute("timeslot") Timeslot updatedTimeslot) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            Timeslot existingTimeslot = timeslotService.getTimeslotById(id).orElseThrow(() -> new NotFoundException("Timeslot not found"));

            existingTimeslot.getHoraire().setHeureDebut(updatedTimeslot.getHoraire().getHeureDebut());
            existingTimeslot.getHoraire().setHeureFin(updatedTimeslot.getHoraire().getHeureFin());

            timeslotService.saveTimeslot(existingTimeslot);
            modelAndView.setViewName("redirect:/timeslot/list");
        } catch (Exception e) {
            modelAndView.setViewName("error");
            modelAndView.addObject("error", "Error updating timeslot: " + e.getMessage());
        }
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteTimeslot(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            timeslotService.deleteTimeslot(id);
            modelAndView.setViewName("redirect:/timeslot/list");
        } catch (Exception e) {
            modelAndView.setViewName("error");
            modelAndView.addObject("error", "Error deleting timeslot: " + e.getMessage());
        }
        return modelAndView;
    }
}
