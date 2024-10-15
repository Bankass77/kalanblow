package ml.kalanblow.gestiondesinscriptions.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ml.kalanblow.gestiondesinscriptions.model.AnneeScolaire;

@Repository
public interface AnneeScolaireRepository  extends JpaRepository<AnneeScolaire, Long> {

    @Override
    Optional<AnneeScolaire> findById(Long aLong);

    @Override
    List<AnneeScolaire> findAll();

    Optional<AnneeScolaire> findByAnneeScolaireDebutAndAnneeScolaireFin(int debut, int fin);
}
