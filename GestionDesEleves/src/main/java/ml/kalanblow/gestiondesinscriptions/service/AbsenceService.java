package ml.kalanblow.gestiondesinscriptions.service;

import ml.kalanblow.gestiondesinscriptions.model.Absence;
import ml.kalanblow.gestiondesinscriptions.model.Cours;
import ml.kalanblow.gestiondesinscriptions.model.Eleve;

import java.util.Optional;

public interface AbsenceService {

    /**
     * Recherche une absence d'élève distincte en fonction de l'élève et du cours d'enseignement spécifiés.
     *
     * @param eleve L'élève pour lequel rechercher l'absence.
     * @param coursDEnseignement Le cours d'enseignement pour lequel rechercher l'absence.
     * @return Une instance facultative (Optional) de l'absence d'élève trouvée, ou une instance vide si aucune absence correspondante n'est trouvée.
     */
    Optional<Absence> findDistinctByEleveAndAndCours(Eleve eleve, Cours coursDEnseignement);

    /**
     * Recherche une absence d'élève en fonction de la justifiée, de l'élève, du cours d'enseignement et du motif spécifiés.
     *
     * @param estJustifiee Indique si l'absence est justifiée.
     * @param eleve L'élève pour lequel rechercher l'absence.
     * @param coursDEnseignement Le cours d'enseignement pour lequel rechercher l'absence.
     * @param motif Le motif de l'absence.
     * @return Une instance facultative (Optional) de l'absence d'élève trouvée, ou une instance vide si aucune absence correspondante n'est trouvée.
     */
    Optional<Absence> findAbsenceEleveByEstJustifieeAndAndEleveAndAndCoursAndMotif(boolean estJustifiee, Eleve eleve,
                                                                                   Cours coursDEnseignement, String motif);

}
