package ml.kalanblow.gestiondesinscriptions.repository;

import ml.kalanblow.gestiondesinscriptions.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppelDePresenceRepository extends JpaRepository<AppelDePresence, Long> {

    /**
     * Recherche un appel de présence distinct en fonction du cours d'enseignement et de l'élève, trié par le nom de l'élève.
     *
     * @param coursDEnseignement Le cours d'enseignement pour lequel rechercher l'appel de présence.
     * @param eleve L'élève pour lequel rechercher l'appel de présence.
     * @return Une instance facultative (Optional) de l'appel de présence trouvé, ou une instance vide si aucun appel de présence correspondant n'est trouvé.
     */
    Optional<AppelDePresence> findDistinctByCoursAndEleveUserNameOrderByEleve(CoursDEnseignement coursDEnseignement, Eleve eleve);

    /**
     * Recherche un appel de présence en fonction du cours d'enseignement et de l'enseignant spécifiés.
     *
     * @param coursDEnseignement Le cours d'enseignement pour lequel rechercher l'appel de présence.
     *
     * @return Une instance facultative (Optional) de l'appel de présence trouvé, ou une instance vide si aucun appel de présence correspondant n'est trouvé.
     */
    Optional<AppelDePresence> findAppelDePresenceByCours_Enseignant(CoursDEnseignement coursDEnseignement);

    /**
     * Recherche un appel de présence en fonction du cours d'enseignement et de l'horaire de classe spécifiés.
     *
     * @param horaireClasse L'horaire de classe pour lequel rechercher l'appel de présence.
     * @return Une instance facultative (Optional) de l'appel de présence trouvé, ou une instance vide si aucun appel de présence correspondant n'est trouvé.
     */
    Optional<AppelDePresence> findAppelDePresenceByCours_HoraireClasses( HoraireClasse horaireClasse);

}
