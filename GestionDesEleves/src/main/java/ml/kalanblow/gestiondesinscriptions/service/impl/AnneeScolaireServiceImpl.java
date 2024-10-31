package ml.kalanblow.gestiondesinscriptions.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import ml.kalanblow.gestiondesinscriptions.exception.EntityType;
import ml.kalanblow.gestiondesinscriptions.exception.ExceptionType;
import ml.kalanblow.gestiondesinscriptions.exception.KaladewnManagementException;
import ml.kalanblow.gestiondesinscriptions.model.AnneeScolaire;
import ml.kalanblow.gestiondesinscriptions.repository.AnneeScolaireRepository;
import ml.kalanblow.gestiondesinscriptions.service.AnneeScolaireService;

@Service
@Transactional
public class AnneeScolaireServiceImpl implements AnneeScolaireService {

    private final AnneeScolaireRepository anneeScolaireRepository;

    private final KaladewnManagementException kaladewnManagementException;

    @Autowired
    public AnneeScolaireServiceImpl(final AnneeScolaireRepository anneeScolaireRepository,KaladewnManagementException kaladewnManagementException) {
        this.kaladewnManagementException= kaladewnManagementException;
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

        // Recherche l'année scolaire ou lance une exception si non trouvée
        AnneeScolaire anneeScolaireToUpdate = anneeScolaireRepository.findById(id).orElseThrow(() ->
                kaladewnManagementException.throwExceptionWithId(EntityType.ANNEESCOLAIRE, ExceptionType.ENTITY_NOT_FOUND,
                        "L'année scolaire avec l'ID " + id + " n'a pas été trouvée"));
        anneeScolaireToUpdate.setAnneeScolaireDebut(anneeScolaire.getAnneeScolaireDebut());
        anneeScolaireToUpdate.setAnneeScolaireFin(anneeScolaire.getAnneeScolaireFin());
        anneeScolaireToUpdate.setVersion(anneeScolaire.getVersion());
        anneeScolaireToUpdate.setClasses(anneeScolaire.getClasses());
        anneeScolaireToUpdate.setEleves(anneeScolaire.getEleves());

        // Sauvegarde et retourne l'entité mise à jour
        return Optional.of(anneeScolaireRepository.save(anneeScolaireToUpdate));
    }

}
