package ml.kalanblow.gestiondesinscriptions.controller.web;

import ml.kalanblow.gestiondesinscriptions.model.Timetable;
import ml.kalanblow.gestiondesinscriptions.service.TimetableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/kalanblow/timetable")
public class KalanblowTimetableController {
    private final TimetableService timetableService;

    @Autowired
    public KalanblowTimetableController(TimetableService timetableService) {
        this.timetableService = timetableService;
    }

    @PostMapping("/generate")
    public ModelAndView generateTimetable() {
        ModelAndView modelAndView = new ModelAndView();

        try {
            Timetable timetable = timetableService.initializeTimetable();
            modelAndView.addObject("timetable", timetable);
            modelAndView.setViewName("timetableView");

        } catch (Exception e) {
            modelAndView.setViewName("error");
            modelAndView.addObject("error", "Error generating timetable: " + e.getMessage());
        }

        return modelAndView;
    }


    @GetMapping("/list")
    public ModelAndView showTimetables() {
        ModelAndView modelAndView = new ModelAndView();
        List<Timetable> timetables = timetableService.getAllTimetables();
        modelAndView.addObject("timetables", timetables);
        modelAndView.setViewName("timetableList");
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editTimetable(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            Timetable timetable = timetableService.getTimetableById(id);
            modelAndView.addObject("timetable", timetable);
            modelAndView.setViewName("editTimetable");
        } catch (Exception e) {
            modelAndView.setViewName("error");
            modelAndView.addObject("error", "Error editing timetable: " + e.getMessage());
        }
        return modelAndView;
    }

    @PostMapping("/edit/{id}")
    public ModelAndView updateTimetable(@PathVariable("id") Long id, @ModelAttribute("timetable") Timetable updatedTimetable) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            Timetable existingTimetable = timetableService.getTimetableById(id);
            // Mettez à jour les propriétés de existingTimetable avec celles de updatedTimetable
            existingTimetable.setEnseignants(updatedTimetable.getEnseignants());
            existingTimetable.setSalles(updatedTimetable.getSalles());
            // Mettez à jour d'autres propriétés...

            timetableService.saveTimetable(existingTimetable);
            modelAndView.setViewName("redirect:/timetable/list");
        } catch (Exception e) {
            modelAndView.setViewName("error");
            modelAndView.addObject("error", "Error updating timetable: " + e.getMessage());
        }
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteTimetable(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            timetableService.deleteTimetableById(id);
            modelAndView.setViewName("redirect:/timetable/list");
        } catch (Exception e) {
            modelAndView.setViewName("error");
            modelAndView.addObject("error", "Error deleting timetable: " + e.getMessage());
        }
        return modelAndView;
    }
}
