package ml.kalanblow.gestiondesinscriptions.service.impl;

import ml.kalanblow.gestiondesinscriptions.model.Periode;
import ml.kalanblow.gestiondesinscriptions.repository.PeriodeRepository;
import ml.kalanblow.gestiondesinscriptions.request.CreatePeriodeParameters;
import ml.kalanblow.gestiondesinscriptions.request.EditPeriodeParameters;
import ml.kalanblow.gestiondesinscriptions.service.PeriodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class PeriodeServiceImpl implements PeriodeService {


    private final PeriodeRepository anneeScolaireRepository;


    @Autowired
    public PeriodeServiceImpl(PeriodeRepository anneeScolaireRepository) {

        this.anneeScolaireRepository = anneeScolaireRepository;
    }

    /**
     * Supprime une année scolaire en fonction de l'année ou de l'identifiant spécifié.
     *
     * @param anneeScolaire L'année scolaire à supprimer.
     * @param id            L'identifiant de l'année scolaire à supprimer.
     * @return Une instance facultative (Optional) de l'année scolaire supprimée, ou une instance vide si aucune année scolaire correspondante n'est trouvée et supprimée.
     */
    @Override
    public Optional<Periode> deleteAnneeScolaireByAnneeOrId(Periode anneeScolaire, int id) {
        return anneeScolaireRepository.deleteAnneeScolaireByAnneeOrId(anneeScolaire, id);
    }

    /**
     * Crée une nouvelle période en utilisant les paramètres spécifiés.
     *
     * @param createPeriodeParameters Les paramètres pour créer une période.
     * @return Une option contenant la nouvelle période si la création réussit, sinon Option vide.
     */
    @Override
    public Optional<Periode> createPeriode(CreatePeriodeParameters createPeriodeParameters) {

        Periode periode = new Periode();

        periode.setAnnee(createPeriodeParameters.getAnnee());
        periode.setDuree(createPeriodeParameters.getDuree());
        periode.setTypeDeVacances(createPeriodeParameters.getTypeDeVacances());
        anneeScolaireRepository.save(periode);
        return Optional.of(periode);
    }

    /**
     * Modifie une période existante identifiée par l'ID spécifié en utilisant les paramètres d'édition spécifiés.
     *
     * @param id                    L'identifiant de la période à éditer.
     * @param editPeriodeParameters Les paramètres pour effectuer la modification.
     * @return Une option contenant la période modifiée si l'édition réussit, sinon Option vide.
     */
    @Override
    public Optional<Periode> doEditPeriode(Long id, EditPeriodeParameters editPeriodeParameters) {

        Optional<Periode> periode= anneeScolaireRepository.findById(id);

        if (editPeriodeParameters.getVersion()!= periode.get().getVersion()){

            throw  new ObjectOptimisticLockingFailureException(Periode.class, periode.get().getId());
        }

         editPeriodeParameters.updateAnneeScolaire(periode.get());

        return  periode;
    }


    @Override
    public Optional<Periode> findAnneeScolairesByAnnee(int annee) {
        return anneeScolaireRepository.findAnneeScolairesByAnnee(annee);
    }
}
