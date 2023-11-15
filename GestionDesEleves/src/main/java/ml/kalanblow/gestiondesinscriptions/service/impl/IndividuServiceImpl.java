package ml.kalanblow.gestiondesinscriptions.service.impl;

import jakarta.transaction.Transactional;
import ml.kalanblow.gestiondesinscriptions.model.Cours;
import ml.kalanblow.gestiondesinscriptions.model.Gene;
import ml.kalanblow.gestiondesinscriptions.model.Individual;
import ml.kalanblow.gestiondesinscriptions.repository.IndividualRepository;
import ml.kalanblow.gestiondesinscriptions.service.IndividuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@Transactional
public class IndividuServiceImpl implements IndividuService {

    private final IndividualRepository individualRepository;

    @Autowired
    public IndividuServiceImpl(IndividualRepository individualRepository) {
        this.individualRepository = individualRepository;
    }

    // Méthodes de service, utilisant les méthodes du repository

    public List<Individual> findIndividualsAboveThreshold(double threshold) {
        return individualRepository.findIndividualsAboveThreshold(threshold);
    }

    public List<Individual> findByFitnessGreaterThan(double threshold) {
        return individualRepository.findByFitnessGreaterThan(threshold);
    }

    public List<Individual> findByEmploiDuTempsContains(Cours cours) {
        return individualRepository.findByEmploiDuTempsContains(cours);
    }

    public List<Individual> findByFitnessBetween(double minFitness, double maxFitness) {
        return individualRepository.findByFitnessBetween(minFitness, maxFitness);
    }

    @Override
    public void evoluerPopulation() {

        List<Individual> population = individualRepository.findAll();
        List<Individual> nouvellePopulation = faireEvoluer(population);
        individualRepository.saveAll(nouvellePopulation);
    }

    // Méthode utilitaire pour effectuer l'évolution de la population
    private List<Individual> faireEvoluer(List<Individual> population) {
        List<Individual> nouvellePopulation = new ArrayList<>();
        List<Individual> parents = selectionnerParents(population);
        List<Individual> enfants = croiserParents(parents);
        enfants = muterEnfants(enfants);
        nouvellePopulation.addAll(parents);
        nouvellePopulation.addAll(enfants);


        return nouvellePopulation;
    }

    private List<Individual> selectionnerParents(List<Individual> population) {
        List<Individual> parents = new ArrayList<>();

        int tailleTournoi = 3;

        for (int i = 0; i < population.size(); i++) {

            List<Individual> tournoiParticipants = choisirAleatoirement(population, tailleTournoi);
            Individual parent = trouverMeilleurIndividu(tournoiParticipants);

            parents.add(parent);
        }

        return parents;
    }

    private List<Individual> choisirAleatoirement(List<Individual> population, int nombreParticipants) {
        List<Individual> participants = new ArrayList<>();
        Collections.shuffle(population);
        participants.addAll(population.subList(0, nombreParticipants));

        return participants;
    }

    private Individual trouverMeilleurIndividu(List<Individual> individus) {
        return Collections.max(individus, Comparator.comparing(Individual::getFitness));
    }


    private List<Individual> croiserParents(List<Individual> parents) {
        List<Individual> enfants = new ArrayList<>();

        for (int i = 0; i < parents.size(); i += 2) {
            if (i + 1 < parents.size()) {
                Individual parent1 = parents.get(i);
                Individual parent2 = parents.get(i + 1);
                Individual enfant = croiser(parent1, parent2);
                enfants.add(enfant);
            }
        }

        return enfants;
    }

    private Individual croiser(Individual parent1, Individual parent2) {
        List<Gene> genesParent1 = new ArrayList<>(parent1.getGenes());

        List<Gene> genesParent2 = new ArrayList<>( parent2.getGenes());

        Random random = new Random();
        double crossoverProbability = 0.8; 

        int individualLength = 10;
        int crossoverPoint = random.nextDouble() < crossoverProbability ? random.nextInt(individualLength) : -1;

                List<Gene> genesEnfant = new ArrayList<>();
                genesEnfant.addAll(genesParent1.subList(0, crossoverPoint));
                genesEnfant.addAll(genesParent2.subList(crossoverPoint, genesParent2.size()));

        Individual individual = new Individual(genesEnfant);
        return individual;
    }

    private void muter(Individual individu) {

        individu.muter();
    }

    private List<Individual> muterEnfants(List<Individual> enfants) {
        for (Individual enfant : enfants) {
            muter(enfant);
        }

        return enfants;
    }


}
