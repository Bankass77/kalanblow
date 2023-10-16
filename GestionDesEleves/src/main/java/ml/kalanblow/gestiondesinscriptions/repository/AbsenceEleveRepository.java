package ml.kalanblow.gestiondesinscriptions.repository;

import ml.kalanblow.gestiondesinscriptions.model.AbsenceEleve;
import ml.kalanblow.gestiondesinscriptions.model.CoursDEnseignement;
import ml.kalanblow.gestiondesinscriptions.model.Eleve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AbsenceEleveRepository extends JpaRepository<AbsenceEleve, Long> {

    /**
     * Recherche une absence d'élève distincte en fonction de l'élève et du cours d'enseignement spécifiés.
     *
     * @param eleve L'élève pour lequel rechercher l'absence.
     * @param coursDEnseignement Le cours d'enseignement pour lequel rechercher l'absence.
     * @return Une instance facultative (Optional) de l'absence d'élève trouvée, ou une instance vide si aucune absence correspondante n'est trouvée.
     */
    Optional<AbsenceEleve> findDistinctByEleveAndAndCours(Eleve eleve, CoursDEnseignement coursDEnseignement);

    /**
     * Recherche une absence d'élève en fonction de la justifiée, de l'élève, du cours d'enseignement et du motif spécifiés.
     *
     * @param estJustifiee Indique si l'absence est justifiée.
     * @param eleve L'élève pour lequel rechercher l'absence.
     * @param coursDEnseignement Le cours d'enseignement pour lequel rechercher l'absence.
     * @param motif Le motif de l'absence.
     * @return Une instance facultative (Optional) de l'absence d'élève trouvée, ou une instance vide si aucune absence correspondante n'est trouvée.
     */
    Optional<AbsenceEleve> findAbsenceEleveByEstJustifieeAndAndEleveAndAndCoursAndMotif(boolean estJustifiee, Eleve eleve,
                                                                                        CoursDEnseignement coursDEnseignement, String motif);


}
