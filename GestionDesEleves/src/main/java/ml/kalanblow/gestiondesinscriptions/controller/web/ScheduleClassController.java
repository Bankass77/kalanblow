package ml.kalanblow.gestiondesinscriptions.controller.web;

import ml.kalanblow.gestiondesinscriptions.model.ScheduleClass;
import ml.kalanblow.gestiondesinscriptions.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/scheduleclass")
public class ScheduleClassController {

    private final ScheduleClassService scheduleClassService;
    private final EnseignantService enseignantService;
    private final SalleService salleService;
    private final GroupService groupService;
    private final TimeslotService timeslotService;
    private final CoursService coursService;
    private final TimetableService timetableService;

    @Autowired
    public ScheduleClassController(
            ScheduleClassService scheduleClassService,
            EnseignantService enseignantService,
            SalleService salleService,
            GroupService groupService,
            TimeslotService timeslotService,
            CoursService coursService,
            TimetableService timetableService
    ) {
        this.scheduleClassService = scheduleClassService;
        this.enseignantService = enseignantService;
        this.salleService = salleService;
        this.groupService = groupService;
        this.timeslotService = timeslotService;
        this.coursService = coursService;
        this.timetableService = timetableService;
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("scheduleClass", new ScheduleClass());
        model.addAttribute("enseignants", enseignantService.getAllEnseignants());
        model.addAttribute("salles", salleService.getAllSalles());
        model.addAttribute("groups", groupService.getAllGroups());
        model.addAttribute("timeslots", timeslotService.getAllTimeslots());
        model.addAttribute("cours", coursService.getAllCours());
        model.addAttribute("timetables", timetableService.getAllTimetables());
        return "scheduleclass/create";
    }

    @PostMapping("/create")
    public String createScheduleClass(@ModelAttribute ScheduleClass scheduleClass) {
        scheduleClassService.saveScheduleClass(scheduleClass);
        return "redirect:/scheduleclass/all";
    }

    @GetMapping("/all")
    public String getAllScheduleClasses(Model model) {
        Set<ScheduleClass> scheduleClasses = scheduleClassService.getAllScheduleClasses();
        model.addAttribute("scheduleClasses", scheduleClasses);
        return "scheduleclass/all";
    }

    @GetMapping("/edit/{id}")
    public String editScheduleClass(@PathVariable Long id, Model model) {
        Optional<ScheduleClass> scheduleClassOpt = scheduleClassService.getScheduleClassById(id);
        if (scheduleClassOpt.isPresent()) {
            model.addAttribute("scheduleClass", scheduleClassOpt.get());
            model.addAttribute("enseignants", enseignantService.getAllEnseignants());
            model.addAttribute("salles", salleService.getAllSalles());
            model.addAttribute("groups", groupService.getAllGroups());
            model.addAttribute("timeslots", timeslotService.getAllTimeslots());
            model.addAttribute("cours", coursService.getAllCours());
            model.addAttribute("timetables", timetableService.getAllTimetables());
            return "scheduleclass/edit";
        } else {

            return "redirect:/scheduleclass/all";
        }
    }

    @PostMapping("/edit/{id}")
    public String doEditScheduleClass(@PathVariable Long id, @ModelAttribute ScheduleClass scheduleClass) {
        scheduleClass.setClassId(id);
        scheduleClassService.saveScheduleClass(scheduleClass);
        return "redirect:/scheduleclass/all";
    }

    @GetMapping("/delete/{id}")
    public String deleteScheduleClass(@PathVariable Long id) {
        scheduleClassService.deleteScheduleClass(id);
        return "redirect:/scheduleclass/all";
    }
}
