package ml.kalanblow.gestiondesinscriptions.service.impl;

import ml.kalanblow.gestiondesinscriptions.model.Periode;
import ml.kalanblow.gestiondesinscriptions.repository.PeriodeRepository;
import ml.kalanblow.gestiondesinscriptions.service.AnneeScolaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AnneeScolaireServiceImpl implements AnneeScolaireService {


    private final PeriodeRepository anneeScolaireRepository;


    @Autowired
    public AnneeScolaireServiceImpl (PeriodeRepository anneeScolaireRepository){

        this.anneeScolaireRepository=anneeScolaireRepository;
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
        return  anneeScolaireRepository.deleteAnneeScolaireByAnneeOrId( anneeScolaire, id);
    }

    @Override
    public Optional<Periode> findAnneeScolairesByAnnee(int annee) {
        return anneeScolaireRepository.findAnneeScolairesByAnnee(annee);
    }
}
