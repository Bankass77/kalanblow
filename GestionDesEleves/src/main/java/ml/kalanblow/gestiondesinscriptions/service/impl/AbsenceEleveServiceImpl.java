package ml.kalanblow.gestiondesinscriptions.service.impl;

import ml.kalanblow.gestiondesinscriptions.model.Absence;
import ml.kalanblow.gestiondesinscriptions.model.Cours;
import ml.kalanblow.gestiondesinscriptions.model.Eleve;
import ml.kalanblow.gestiondesinscriptions.repository.AbsenceRepository;
import ml.kalanblow.gestiondesinscriptions.service.AbsenceEleveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AbsenceEleveServiceImpl implements AbsenceEleveService {

    private final AbsenceRepository absenceEleveRepository;

    @Autowired
    public AbsenceEleveServiceImpl (AbsenceRepository absenceEleveRepository){

        this.absenceEleveRepository=absenceEleveRepository;
    }

    /**
     * Recherche une absence d'élève distincte en fonction de l'élève et du cours d'enseignement spécifiés.
     *
     * @param eleve              L'élève pour lequel rechercher l'absence.
     * @param coursDEnseignement Le cours d'enseignement pour lequel rechercher l'absence.
     * @return Une instance facultative (Optional) de l'absence d'élève trouvée, ou une instance vide si aucune absence correspondante n'est trouvée.
     */
    @Override
    public Optional<Absence> findDistinctByEleveAndAndCours(Eleve eleve, Cours coursDEnseignement) {
        return absenceEleveRepository.findDistinctByEleveAndAndCours(eleve, coursDEnseignement);
    }

    /**
     * Recherche une absence d'élève en fonction de la justifiée, de l'élève, du cours d'enseignement et du motif spécifiés.
     *
     * @param estJustifiee       Indique si l'absence est justifiée.
     * @param eleve              L'élève pour lequel rechercher l'absence.
     * @param coursDEnseignement Le cours d'enseignement pour lequel rechercher l'absence.
     * @param motif              Le motif de l'absence.
     * @return Une instance facultative (Optional) de l'absence d'élève trouvée, ou une instance vide si aucune absence correspondante n'est trouvée.
     */
    @Override
    public Optional<Absence> findAbsenceEleveByEstJustifieeAndAndEleveAndAndCoursAndMotif(boolean estJustifiee, Eleve eleve, Cours coursDEnseignement, String motif) {
        return absenceEleveRepository.findAbsenceEleveByEstJustifieeAndAndEleveAndAndCoursAndMotif(estJustifiee,eleve,coursDEnseignement,motif);
    }
}
