package ml.kalanblow.gestiondescours.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ml.kalanblow.gestiondescours.model.Cours;
import ml.kalanblow.gestiondescours.model.Salle;

@Repository
public interface SalleRepository extends JpaRepository<Salle, Long> {
    List<Salle> findAllSalleByCours(Cours cours);
}
