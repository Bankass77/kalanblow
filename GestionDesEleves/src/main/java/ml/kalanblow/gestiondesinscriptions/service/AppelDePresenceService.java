package ml.kalanblow.gestiondesinscriptions.service;

import ml.kalanblow.gestiondesinscriptions.model.AppelDePresence;
import ml.kalanblow.gestiondesinscriptions.model.CoursDEnseignement;
import ml.kalanblow.gestiondesinscriptions.model.Eleve;
import ml.kalanblow.gestiondesinscriptions.model.HoraireClasse;

import java.util.Optional;

public interface AppelDePresenceService {
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
