package ml.kalanblow.gestiondesinscriptions.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import ml.kalanblow.gestiondesinscriptions.model.AnneeScolaire;
import ml.kalanblow.gestiondesinscriptions.repository.AnneeScolaireRepository;
import ml.kalanblow.gestiondesinscriptions.service.AnneeScolaireService;

@Service
@Transactional
public class AnneeScolaireServiceImpl implements AnneeScolaireService {

    private final AnneeScolaireRepository anneeScolaireRepository;

    @Autowired
    public AnneeScolaireServiceImpl(final AnneeScolaireRepository anneeScolaireRepository) {
        this.anneeScolaireRepository = anneeScolaireRepository;
    }

    /**
     * @param aLong 
     * @return
     */
    @Override
    public Optional<AnneeScolaire> findById(final Long aLong) {
        return anneeScolaireRepository.findById(aLong);
    }

    /**
     * @param debut 
     * @param fin
     * @return
     */
    @Override
    public Optional<AnneeScolaire> findByAnneeScolaireDebutAndAnneeScolaireFin(final int debut, final int fin) {
        return anneeScolaireRepository.findByAnneeScolaireDebutAndAnneeScolaireFin(debut, fin);
    }

    /**
     * @return 
     */
    @Override
    public List<AnneeScolaire> findAll() {
        return anneeScolaireRepository.findAll();
    }

    /**
     * @param anneeScolaire 
     * @return
     */
    @Override
    public Optional<AnneeScolaire> createNewAnneeScolaire(final AnneeScolaire anneeScolaire) {
        return Optional.of(anneeScolaireRepository.save(anneeScolaire));
    }

    /**
     * @param id 
     * @param anneeScolaire
     * @return
     */
    @Override
    public Optional<AnneeScolaire> mettreAJourAnneeScolaire(final long id, final AnneeScolaire anneeScolaire) {

        Optional<AnneeScolaire> anneeScolaire1 = anneeScolaireRepository.findById(id);
        if(anneeScolaire1.isPresent()){

            AnneeScolaire anneeScolaireToUpdate=  anneeScolaire1.get();

            anneeScolaireToUpdate.setAnneeScolaireDebut(anneeScolaire.getAnneeScolaireDebut());
            anneeScolaireToUpdate.setVersion(anneeScolaire.getVersion());
            anneeScolaireToUpdate.setClasses(anneeScolaire.getClasses());
            anneeScolaireToUpdate.setEleves(anneeScolaire.getEleves());
            return Optional.of(anneeScolaireRepository.saveAndFlush(anneeScolaireToUpdate));
        }
        return  null;
    }
}
