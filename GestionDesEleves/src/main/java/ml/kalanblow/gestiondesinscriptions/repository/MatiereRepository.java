package ml.kalanblow.gestiondesinscriptions.repository;

import ml.kalanblow.gestiondesinscriptions.model.CoursDEnseignement;
import ml.kalanblow.gestiondesinscriptions.model.Matiere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface MatiereRepository  extends JpaRepository<Matiere, Long> {

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
    Optional<List<Matiere>> searchAllByCoursDEnseignementsOrderByNomMatiere(CoursDEnseignement coursDEnseignement);


}
