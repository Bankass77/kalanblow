package ml.kalanblow.gestiondesinscriptions.service;

import ml.kalanblow.gestiondesinscriptions.model.Population;

import java.util.List;

public interface PopulationService {
    List<Population> findByPopulationFitnessGreaterThan(double threshold);
}
