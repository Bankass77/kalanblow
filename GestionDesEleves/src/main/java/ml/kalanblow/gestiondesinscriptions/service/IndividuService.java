package ml.kalanblow.gestiondesinscriptions.service;

import ml.kalanblow.gestiondesinscriptions.model.Cours;
import ml.kalanblow.gestiondesinscriptions.model.Individual;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IndividuService {

     /**
      * Recherche les individus dont la fitness est supérieure à un seuil.
      *
      * @param threshold Le seuil de fitness.
      * @return La liste des individus dont la fitness est supérieure au seuil.
      */
     @Query("SELECT i FROM Individual i WHERE i.fitness > :threshold")
     List<Individual> findIndividualsAboveThreshold(@Param("threshold") double threshold);


     /**
      * Recherche les individus dont la fitness est supérieure à un seuil.
      *
      * @param threshold Le seuil de fitness.
      * @return La liste des individus dont la fitness est supérieure au seuil.
      */
     List<Individual> findByFitnessGreaterThan(double threshold);


     /**
      * Recherche les individus dont l'emploi du temps contient un cours spécifique.
      *
      * @param cours Le cours recherché.
      * @return La liste des individus dont l'emploi du temps contient le cours spécifié.
      */
     List<Individual> findByEmploiDuTempsContains(Cours cours);


     /**
      * Recherche les individus dont la fitness est comprise entre deux valeurs.
      *
      * @param minFitness La valeur minimale de fitness.
      * @param maxFitness La valeur maximale de fitness.
      * @return La liste des individus dont la fitness est comprise entre les deux valeurs.
      */
     List<Individual> findByFitnessBetween(double minFitness, double maxFitness);

     /**
      * Effectue l'évolution de la population.
      */
     void evoluerPopulation();
}
