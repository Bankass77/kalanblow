package ml.kalanblow.gestiondesinscriptions.service.impl;

import jakarta.transaction.Transactional;
import ml.kalanblow.gestiondesinscriptions.model.Vacances;
import ml.kalanblow.gestiondesinscriptions.repository.VacancesRepository;
import ml.kalanblow.gestiondesinscriptions.request.CreateVacancesParameters;
import ml.kalanblow.gestiondesinscriptions.request.EditVacancesParameters;
import ml.kalanblow.gestiondesinscriptions.service.VacancesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class VacancesServiceImpl implements VacancesService {

    private final VacancesRepository periodeDeVacancesRepository;


    @Autowired
    public VacancesServiceImpl(VacancesRepository periodeDeVacancesRepository) {

        this.periodeDeVacancesRepository = periodeDeVacancesRepository;
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

    /**
     * Crée une nouvelle période de vacances en utilisant les paramètres spécifiés.
     *
     * @param createVacancesParameters Les paramètres pour créer une période de vacances.
     * @return Une option contenant la nouvelle période de vacances si la création réussit, sinon Option vide.
     */
    @Override
    public Optional<Vacances> createVacances(CreateVacancesParameters createVacancesParameters) {

        Vacances vacances = new Vacances();

        vacances.setDateDebut(createVacancesParameters.getDateDebut());
        vacances.setDateFin(createVacancesParameters.getDateFin());

        periodeDeVacancesRepository.save(vacances);
        return Optional.of(vacances);
    }

    /**
     * Modifie une période de vacances existante identifiée par l'ID spécifié en utilisant les paramètres d'édition spécifiés.
     *
     * @param id                     L'identifiant de la période de vacances à éditer.
     * @param editVacancesParameters Les paramètres pour effectuer la modification.
     * @return Une option contenant la période de vacances modifiée si l'édition réussit, sinon Option vide.
     */
    @Override
    public Optional<Vacances> doEditVacances(Long id, EditVacancesParameters editVacancesParameters) {

        Optional<Vacances> vacances = periodeDeVacancesRepository.findById(id);

        if (editVacancesParameters.getVersion() != vacances.get().getVersion()) {

            throw new ObjectOptimisticLockingFailureException(Vacances.class, vacances.get().getId());
        }

        editVacancesParameters.updatePeriodeDeVacances(vacances.get());
        return vacances;
    }

    @Override
    public Set<Vacances> getAllVacances() {
        return new HashSet<>(periodeDeVacancesRepository.findAll());
    }

    @Override
    public Optional<Vacances> getVacancesById(Long id) {
        return periodeDeVacancesRepository.findById(id);
    }

    @Override
    public void deleteVacances(Long id) {
        periodeDeVacancesRepository.deleteById(id);
    }
}
