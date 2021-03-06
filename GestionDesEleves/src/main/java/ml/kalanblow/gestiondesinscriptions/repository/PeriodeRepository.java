package ml.kalanblow.gestiondesinscriptions.repository;

import ml.kalanblow.gestiondesinscriptions.model.Periode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeriodeRepository extends JpaRepository<Periode, Long> {

    /**
     * Supprime une année scolaire en fonction de l'année ou de l'identifiant spécifié.
     *
     * @param anneeScolaire L'année scolaire à supprimer.
     * @param id L'identifiant de l'année scolaire à supprimer.
     * @return Une instance facultative (Optional) de l'année scolaire supprimée, ou une instance vide si aucune année scolaire correspondante n'est trouvée et supprimée.
     */
    Optional<Periode> deleteAnneeScolaireByAnneeOrId(Periode anneeScolaire, int id);

    Optional<Periode>findAnneeScolairesByAnnee(int annee);



}
