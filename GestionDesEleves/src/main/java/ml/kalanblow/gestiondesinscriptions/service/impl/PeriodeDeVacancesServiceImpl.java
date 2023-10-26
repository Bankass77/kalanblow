package ml.kalanblow.gestiondesinscriptions.service.impl;

import jakarta.transaction.Transactional;
import ml.kalanblow.gestiondesinscriptions.model.Vacances;
import ml.kalanblow.gestiondesinscriptions.repository.VacancesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
public class PeriodeDeVacancesServiceImpl implements ml.kalanblow.gestiondesinscriptions.service.PeriodeDeVacancesService {

    private final VacancesRepository periodeDeVacancesRepository;


    @Autowired
    public PeriodeDeVacancesServiceImpl (VacancesRepository periodeDeVacancesRepository){

        this.periodeDeVacancesRepository=periodeDeVacancesRepository;
    }
    /**
     * Recherche une période de vacances distincte par sa date de début et de fin.
     *
     * @param dateDebut La date de début de la période de vacances à rechercher.
     * @param dateFin   La date de fin de la période de vacances à rechercher.
     * @return Une instance optionnelle contenant la période de vacances correspondant aux dates spécifiées, si elle existe.
     */
    @Override
    public Optional<Vacances> findDistinctByDateDebutAndDateFin(LocalDate dateDebut, LocalDate dateFin) {
        return periodeDeVacancesRepository.findDistinctByDateDebutAndDateFin(dateDebut, dateFin);
    }
}
