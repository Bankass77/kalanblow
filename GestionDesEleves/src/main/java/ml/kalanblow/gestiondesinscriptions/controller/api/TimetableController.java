package ml.kalanblow.gestiondesinscriptions.controller.api;

import ml.kalanblow.gestiondesinscriptions.model.Timetable;
import ml.kalanblow.gestiondesinscriptions.service.TimetableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/timetable")
public class TimetableController {

    private final TimetableService timetableService;

    @Autowired
    public TimetableController(TimetableService timetableService) {
        this.timetableService = timetableService;
    }

    @PostMapping("/generate")
    public ResponseEntity<String> generateTimetable() {
        try {
            Timetable timetable = timetableService.initializeTimetable();
            return ResponseEntity.ok("Timetable generated successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating timetable: " + e.getMessage());
        }
    }
}
