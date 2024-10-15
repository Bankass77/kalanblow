package ml.kalanblow.gestiondesinscriptions.repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ml.kalanblow.gestiondesinscriptions.model.Enseignant;
import ml.kalanblow.gestiondesinscriptions.model.Etablissement;

@Repository
public interface EnseignantRepository extends JpaRepository<Enseignant, Long> {
    // Trouver par matricule
    Optional<Enseignant> findByLeMatricule(String leMatricule);

    // Trouver par établissement
    List<Enseignant> findByEtablissement(Etablissement etablissement);

    Optional<Enseignant> findEnseignantByUserEmailEmail(String email);

    List<Enseignant> getEnseignantByUserCreatedDateIsBetween(final LocalDate debut, final LocalDate fin);

    List<DayOfWeek> getEnseignantByDisponibilites(Enseignant enseignant);
}
