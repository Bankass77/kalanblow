package ml.kalanblow.gestiondescours.controlleur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ml.kalanblow.gestiondescours.service.MatiereService;

@RestController
@RequestMapping("/api/matieres")
public class MatiereController {

    private final MatiereService matiereService;

    @Autowired
    public MatiereController(final MatiereService matiereService) {
        this.matiereService = matiereService;
    }
}
