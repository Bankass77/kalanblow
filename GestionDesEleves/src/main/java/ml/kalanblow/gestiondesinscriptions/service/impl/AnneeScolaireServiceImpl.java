package ml.kalanblow.gestiondesinscriptions.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import ml.kalanblow.gestiondesinscriptions.exception.EntityNotFoundException;
import ml.kalanblow.gestiondesinscriptions.exception.KaladewnManagementException;
import ml.kalanblow.gestiondesinscriptions.model.AnneeScolaire;
import ml.kalanblow.gestiondesinscriptions.repository.AnneeScolaireRepository;
import ml.kalanblow.gestiondesinscriptions.service.AnneeScolaireService;

@Slf4j
@Service
@Transactional
public class AnneeScolaireServiceImpl implements AnneeScolaireService {

    private final AnneeScolaireRepository anneeScolaireRepository;

    @Autowired
    public AnneeScolaireServiceImpl(AnneeScolaireRepository anneeScolaireRepository) {
        this.anneeScolaireRepository = anneeScolaireRepository;
    }

    /**
     * @param aLong identifaint de l'anneeScoalier
     * @return annneeScoliare
     */
    @Override
    public Optional<AnneeScolaire> findById(final Long aLong) {

        return anneeScolaireRepository.findById(aLong);
    }

    /**
     * @param debut de l'annneeSclaire
     * @param fin   de l'annneeSclaire
     * @return une annneeSclaire
     */
    @Override
    public Optional<AnneeScolaire> findByAnneeScolaireDebutAndAnneeScolaireFin(final int debut, final int fin) {

            return anneeScolaireRepository.findByAnneeScolaireDebutAndAnneeScolaireFin(debut, fin);
    }

    /**
     * @return une liste d'anneeScolaire
     */
    @Override
    public List<AnneeScolaire> findAll() {
            return anneeScolaireRepository.findAll();

    }

    /**
     * @param anneeScolaire à créer
     * @return une anneeSclaire
     */
    @Override
    public Optional<AnneeScolaire> createNewAnneeScolaire(final AnneeScolaire anneeScolaire) {
            AnneeScolaire anneeScolaireSaved = anneeScolaireRepository.save(anneeScolaire);
            return Optional.of(anneeScolaireSaved);
    }


    /**
     * @param id de l'AnneeScolaire à mettre à jour
     * @param anneeScolaire à mettre à jour
     * @return une anneeSclaire qui a été mise à jour
     */
    @Override
    public Optional<AnneeScolaire> mettreAJourAnneeScolaire(final long id, final AnneeScolaire anneeScolaire) {
        for (int attempts = 0; attempts < 3; attempts++) {
            try {
                // Recherche l'année scolaire ou lance une exception si non trouvée
                AnneeScolaire anneeScolaireToUpdate = anneeScolaireRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(id, AnneeScolaire.class));

                // Update fields
                anneeScolaireToUpdate.setAnneeScolaireDebut(anneeScolaire.getAnneeScolaireDebut());
                anneeScolaireToUpdate.setAnneeScolaireFin(anneeScolaire.getAnneeScolaireFin());
                anneeScolaireToUpdate.setClasses(anneeScolaire.getClasses());
                anneeScolaireToUpdate.setEleves(anneeScolaire.getEleves());

                return Optional.of(anneeScolaireRepository.save(anneeScolaireToUpdate));
            } catch (ObjectOptimisticLockingFailureException e) {

                log.info(e.getMessage());
            }
        }
        throw new RuntimeException("Could not update AnneeScolaire after multiple attempts.");
    }


}
