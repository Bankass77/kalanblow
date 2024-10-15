package ml.kalanblow.gestiondesinscriptions.service;

import java.util.List;
import java.util.Optional;

import ml.kalanblow.gestiondesinscriptions.model.AnneeScolaire;

public interface AnneeScolaireService {

    Optional<AnneeScolaire> findById(Long aLong);

    Optional<AnneeScolaire> findByAnneeScolaireDebutAndAnneeScolaireFin(int debut, int fin);

    List<AnneeScolaire> findAll();

    Optional<AnneeScolaire> createNewAnneeScolaire ( AnneeScolaire anneeScolaire);

    Optional<AnneeScolaire> mettreAJourAnneeScolaire(long id, AnneeScolaire anneeScolaire);

}
