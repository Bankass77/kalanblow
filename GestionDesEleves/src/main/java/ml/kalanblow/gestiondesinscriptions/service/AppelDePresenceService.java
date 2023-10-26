package ml.kalanblow.gestiondesinscriptions.service;

import ml.kalanblow.gestiondesinscriptions.model.Presence;
import ml.kalanblow.gestiondesinscriptions.model.Cours;
import ml.kalanblow.gestiondesinscriptions.model.Eleve;
import ml.kalanblow.gestiondesinscriptions.model.Horaire;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AppelDePresenceService {
    /**
     * Recherche un appel de présence distinct en fonction du cours d'enseignement et de l'élève, trié par le nom de l'élève.
     *
     * @param coursDEnseignement Le cours d'enseignement pour lequel rechercher l'appel de présence.
     * @param eleve L'élève pour lequel rechercher l'appel de présence.
     * @return Une instance facultative (Optional) de l'appel de présence trouvé, ou une instance vide si aucun appel de présence correspondant n'est trouvé.
     */
    Optional<Presence> findDistinctByCoursAndEleveUserNameOrderByEleve(Cours coursDEnseignement, Eleve eleve);

    /**
     * Recherche un appel de présence en fonction du cours d'enseignement et de l'enseignant spécifiés.
     *
     * @param coursDEnseignement Le cours d'enseignement pour lequel rechercher l'appel de présence.
     *
     * @return Une instance facultative (Optional) de l'appel de présence trouvé, ou une instance vide si aucun appel de présence correspondant n'est trouvé.
     */
    Optional<Presence> findAppelDePresenceByCours_Enseignant(Cours coursDEnseignement);

    /**
     * Recherche un appel de présence en fonction du cours d'enseignement et de l'horaire de classe spécifiés.
     *
     * @param horaireClasse L'horaire de classe pour lequel rechercher l'appel de présence.
     * @return Une instance facultative (Optional) de l'appel de présence trouvé, ou une instance vide si aucun appel de présence correspondant n'est trouvé.
     */
    Optional<Presence> findAppelDePresenceByCours_HoraireClasses(Horaire horaireClasse);


    /**
     * Effectue l'appel des élèves pour une date donnée.
     *
     * @param dateActuelle La date pour laquelle l'appel des élèves doit être effectué.
     */
    public void effectuerAppelDesEleves(LocalDate dateActuelle);

    /**
     * Enregistre la présence de l'élève pour une date spécifiée.
     *
     * @param eleve L'élève dont la présence doit être enregistrée.
     * @param date La date pour laquelle la présence de l'élève est enregistrée.
     */
    public void enregistrerPresenceEleve(Eleve eleve, LocalDate date);

    List<Presence> findByDateAppel(LocalDate dateAppel);
}
