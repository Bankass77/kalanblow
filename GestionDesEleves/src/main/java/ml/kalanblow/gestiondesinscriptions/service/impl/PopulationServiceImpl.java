package ml.kalanblow.gestiondesinscriptions.service.impl;

import jakarta.transaction.Transactional;
import ml.kalanblow.gestiondesinscriptions.model.Population;
import ml.kalanblow.gestiondesinscriptions.repository.PopulationRepository;
import ml.kalanblow.gestiondesinscriptions.service.PopulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Transactional
public class PopulationServiceImpl  implements PopulationService {

    private final PopulationRepository populationRepository;

    @Autowired
    public PopulationServiceImpl(PopulationRepository populationRepository) {
        this.populationRepository = populationRepository;
    }

    @Override
    public List<Population> findByPopulationFitnessGreaterThan(double threshold) {
        return populationRepository.findByPopulationFitnessGreaterThan(threshold);
    }
}
