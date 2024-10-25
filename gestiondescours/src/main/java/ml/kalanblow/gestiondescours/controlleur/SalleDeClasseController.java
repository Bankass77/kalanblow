package ml.kalanblow.gestiondescours.controlleur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ml.kalanblow.gestiondescours.service.SalleService;

@RestController
@RequestMapping("/api/salles")
public class SalleDeClasseController {
    private final SalleService salleService;

    @Autowired
    public SalleDeClasseController(final SalleService salleService) {
        this.salleService = salleService;
    }
}
