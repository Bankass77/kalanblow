package ml.kalanblow.gestiondescours.controlleur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ml.kalanblow.gestiondescours.service.CoursService;

@RestController
@RequestMapping("/api/cours")
public class CoursController {

    private final CoursService coursService;

    @Autowired
    public CoursController(final CoursService coursService) {
        this.coursService = coursService;
    }
}
