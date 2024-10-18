package ml.kalanblow.gestiondescours.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ml.kalanblow.gestiondescours.model.Matiere;

@Repository
public interface MatiereRepository extends JpaRepository<Matiere, Long> {

    List<Matiere> findAllMatieresByEnseignantResponsableMatieres(Matiere matiere);
}
