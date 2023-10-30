package ml.kalanblow.gestiondesinscriptions.service;

import ml.kalanblow.gestiondesinscriptions.model.Cours;
import ml.kalanblow.gestiondesinscriptions.model.Matiere;
import ml.kalanblow.gestiondesinscriptions.request.CreateMatiereParameters;
import ml.kalanblow.gestiondesinscriptions.request.EditMatiereParameters;

import java.util.List;
import java.util.Optional;

public interface MatiereService {
    /**
     * Recherche une matière dont le nom contient la chaîne spécifiée (insensible à la casse).
     *
     * @param nomMatiere La chaîne de caractères à rechercher dans le nom de la matière.
     * @return Une instance facultative (Optional) de la matière trouvée, ou une instance vide si aucune matière correspondante n'est trouvée.
     */
    Optional<Matiere> getMatieresByNomMatiereContainsIgnoreCase(String nomMatiere);

    /**
     * Recherche toutes les matières associées à un cours d'enseignement spécifique, triées par le nom de la matière.
     *
     * @param coursDEnseignement Le cours d'enseignement pour lequel rechercher les matières.
     * @return Une instance facultative (Optional) des matières trouvées, triées par le nom de la matière, ou une instance vide si aucune matière n'est associée au cours d'enseignement.
     */
    Optional<List<Matiere>> searchAllByCoursDEnseignementsOrderByNomMatiere(Cours coursDEnseignement);

    /**
     * Crée une nouvelle matière avec les paramètres spécifiés.
     *
     * @param matiereParameters Les paramètres pour la création de la matière.
     * @return Une option contenant la matière nouvellement créée.
     */
    Optional<Matiere> creerMatieres(CreateMatiereParameters matiereParameters);

    /**
     * Édite une matière existante avec les paramètres spécifiés.
     *
     * @param matiereParameters Les paramètres pour l'édition de la matière.
     * @return Une option contenant la matière éditée.
     */
    Optional<Matiere> editerMatiere(Long id, EditMatiereParameters matiereParameters);
}
