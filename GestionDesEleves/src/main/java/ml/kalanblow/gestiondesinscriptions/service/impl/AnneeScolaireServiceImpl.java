package ml.kalanblow.gestiondesinscriptions.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import ml.kalanblow.gestiondesinscriptions.exception.AdministrateurAlreadyExistsException;
import ml.kalanblow.gestiondesinscriptions.exception.AnnneeScolaireAlreadyExistsException;
import ml.kalanblow.gestiondesinscriptions.exception.KaladewnManagementException;
import ml.kalanblow.gestiondesinscriptions.model.AnneeScolaire;
import ml.kalanblow.gestiondesinscriptions.repository.AnneeScolaireRepository;
import ml.kalanblow.gestiondesinscriptions.service.AnneeScolaireService;
import ml.kalanblow.gestiondesinscriptions.util.ErrorMessages;

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

        try {
            return anneeScolaireRepository.findById(aLong);
        } catch (Exception e){

            throw  new AnnneeScolaireAlreadyExistsException(ErrorMessages.ERROR_AnnneeScolaire_NOT_FOUND + aLong);
        }
    }

    /**
     * @param debut de l'annneeSclaire
     * @param fin de l'annneeSclaire
     * @return une annneeSclaire
     */
    @Override
    public Optional<AnneeScolaire> findByAnneeScolaireDebutAndAnneeScolaireFin(final int debut, final int fin) {
       try {
           return anneeScolaireRepository.findByAnneeScolaireDebutAndAnneeScolaireFin(debut, fin);
       }catch (Exception e){

           throw  new KaladewnManagementException(e.getMessage());
       }
    }

    /**
     * @return une liste d'anneeScolaire
     */
    @Override
    public List<AnneeScolaire> findAll() {
       try {
           return anneeScolaireRepository.findAll();
       } catch (Exception e) {
           throw new KaladewnManagementException(e.getMessage());
       }
    }

    /**
     * @param anneeScolaire à créer
     * @return une anneeSclaire
     */
    @Override
    public Optional<AnneeScolaire> createNewAnneeScolaire(final AnneeScolaire anneeScolaire) {

        try {
            AnneeScolaire anneeScolaireSaved = anneeScolaireRepository.save(anneeScolaire);
            return Optional.of(anneeScolaireSaved);
        }catch (Exception e){

            throw new KaladewnManagementException(e.getMessage());
        }
    }


    /**
     * @param id de l'AnneeScolaire à mettre à jour
     * @param anneeScolaire à mettre à jour
     * @return une anneeSclaire qui a été mise à jour
     */
    @Override
    public Optional<AnneeScolaire> mettreAJourAnneeScolaire(final long id, final AnneeScolaire anneeScolaire) {

       try {
           // Recherche l'année scolaire ou lance une exception si non trouvée
           AnneeScolaire anneeScolaireToUpdate = anneeScolaireRepository.findById(id).orElseThrow(() ->
                   new AdministrateurAlreadyExistsException(ErrorMessages.ERROR_AnnneeScolaire_NOT_FOUND + id));
           anneeScolaireToUpdate.setAnneeScolaireDebut(anneeScolaire.getAnneeScolaireDebut());
           anneeScolaireToUpdate.setAnneeScolaireFin(anneeScolaire.getAnneeScolaireFin());
           anneeScolaireToUpdate.setClasses(anneeScolaire.getClasses());
           anneeScolaireToUpdate.setEleves(anneeScolaire.getEleves());
           return Optional.of(anneeScolaireRepository.save(anneeScolaireToUpdate));
       } catch (Exception e) {
           throw new AnnneeScolaireAlreadyExistsException(e.getMessage());
       }
    }

}
