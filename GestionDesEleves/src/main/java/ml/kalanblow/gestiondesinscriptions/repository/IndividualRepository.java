package ml.kalanblow.gestiondesinscriptions.repository;

import ml.kalanblow.gestiondesinscriptions.model.Cours;
import ml.kalanblow.gestiondesinscriptions.model.Individual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IndividualRepository extends JpaRepository<Individual, Long> {
    @Query("SELECT i FROM Individual i WHERE i.fitness > :threshold")
    List<Individual> findIndividualsAboveThreshold(@Param("threshold") double threshold);

    List<Individual> findByFitnessGreaterThan(double threshold);

    List<Individual> findByEmploiDuTempsContains(Cours cours);

    List<Individual> findByFitnessBetween(double minFitness, double maxFitness);

}
