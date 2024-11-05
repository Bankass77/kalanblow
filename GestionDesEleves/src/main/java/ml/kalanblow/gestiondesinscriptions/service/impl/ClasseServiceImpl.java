package ml.kalanblow.gestiondesinscriptions.service.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import ml.kalanblow.gestiondesinscriptions.exception.EntityNotFoundException;
import ml.kalanblow.gestiondesinscriptions.exception.KaladewnManagementException;
import ml.kalanblow.gestiondesinscriptions.model.AnneeScolaire;
import ml.kalanblow.gestiondesinscriptions.model.Classe;
import ml.kalanblow.gestiondesinscriptions.model.Etablissement;
import ml.kalanblow.gestiondesinscriptions.repository.AnneeScolaireRepository;
import ml.kalanblow.gestiondesinscriptions.repository.ClasseRepository;
import ml.kalanblow.gestiondesinscriptions.repository.EtablissementRepository;
import ml.kalanblow.gestiondesinscriptions.service.ClasseService;

@Service
@Slf4j
@Transactional
public class ClasseServiceImpl implements ClasseService {

    private final ClasseRepository classeRepository;
    private final AnneeScolaireRepository anneeScolaireRepository;
    private final EtablissementRepository etablissementRepository;
    private final ModelMapper modelMapper;


    @Autowired
    public ClasseServiceImpl(ClasseRepository classeRepository, AnneeScolaireRepository anneeScolaireRepository,
                             EtablissementRepository etablissementRepository, ModelMapper modelMapper) {
        this.classeRepository = classeRepository;
        this.anneeScolaireRepository = anneeScolaireRepository;
        this.etablissementRepository = etablissementRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * @param classe
     * @return une classe
     */
    @Override
    public Classe createClasse(final Classe classe) {

        AnneeScolaire anneeScolaire = classe.getAnneeScolaire();
        if (anneeScolaire != null) {
            Optional<AnneeScolaire> anneeScolaireExistant = anneeScolaireRepository.findById(anneeScolaire.getAnneeScolaireId());
            classe.setAnneeScolaire(anneeScolaireExistant.get());
        }

        Optional<Etablissement> etablissementExistant = etablissementRepository.findByEtablisementScolaireId(classe.getEtablissement()
                .getEtablisementScolaireId());
        if (etablissementExistant.isPresent() && etablissementExistant.get().getEtablisementScolaireId() != null) {
            classe.setEtablissement(etablissementExistant.get());
        }
        return classeRepository.save(classe);
    }

    /**
     * @param classeId identifiant de la classe
     * @param classe   de l'élève
     * @return une classe mise à jour.
     */
    @Override
    public Optional<Classe> updateClasse(final Long classeId, final Classe classe) {
        for (int attempts = 0; attempts < 3; attempts++) {
            try {
                Optional<Classe> classe1 = Optional.ofNullable(classeRepository.findById(classeId)
                        .orElseThrow(() -> new EntityNotFoundException(classeId, Classe.class)));

                Classe classeToUpdate = classe1.get();
                // Vérifier l'existence de l'année scolaire avant de l'associer
                AnneeScolaire anneeScolaire = classe.getAnneeScolaire();
                if (anneeScolaire != null && anneeScolaire.getAnneeScolaireId() != null) {
                    Optional<AnneeScolaire> anneeScolaireExistant = anneeScolaireRepository.findById(anneeScolaire.getAnneeScolaireId());
                }

                classeToUpdate.setEtablissement(classe.getEtablissement());
                classeToUpdate.setNom(classe.getNom());
                classeToUpdate.setAnneeScolaire(classe.getAnneeScolaire());
                modelMapper.map(classe, classeToUpdate);

                return Optional.of(classeRepository.save(classeToUpdate));
            } catch (ObjectOptimisticLockingFailureException e) {

                log.info(e.getMessage());
            }
        }
        throw new RuntimeException("Could not update Classe after multiple attempts.");
    }

    /**
     * @param classeId à supprimer
     */
    @Override
    public void deleteClasse(final Long classeId) {

        if (classeRepository.existsById(classeId)) {
            classeRepository.deleteById(classeId);
        }

    }

    /**
     * @param classeId de la classe
     * @return une Classe
     */
    @Override
    public Optional<Classe> findByClasseById(long classeId) {
        return Optional.ofNullable(classeRepository.findById(classeId)
                .orElseThrow(() -> new EntityNotFoundException(classeId, Classe.class)));
    }

    /**
     * @param nom de la classe
     * @return une classe
     */
    @Override
    public List<Classe> findByNom(final String nom) {
        return classeRepository.findByNom(nom);

    }

    /**
     * @param etablissement donnée
     * @return une classe
     */
    @Override
    public List<Classe> findByEtablissement(final Etablissement etablissement) {
        return classeRepository.findByNom(etablissement.getNomEtablissement());
    }

    /**
     * @param anneeScolaire année scolaire en cours
     * @return une classe
     */
    @Override
    public List<Classe> findByAnneeScolaire(final AnneeScolaire anneeScolaire) {
        return classeRepository.findByAnneeScolaire(anneeScolaire);
    }

    /**
     * @param classeId      identifiant de la classe
     * @param etablissement données
     * @return établissement
     */
    @Override
    public Optional<Classe> findByClasseIdAndEtablissement(final Long classeId, final Etablissement etablissement) {
        return classeRepository.findByClasseIdAndEtablissement(classeId, etablissement);

    }

    /**
     * @param etablissement données
     * @return le nombre d'établissement
     */
    @Override
    public Long countByEtablissement(final Etablissement etablissement) {
        return classeRepository.countByEtablissement(etablissement);
    }

    /**
     * @param nom de la classe
     * @return une classe
     */
    @Override
    public Optional<Classe> findByClasseName(final String nom) {
        return classeRepository.findByNom(nom).stream().findFirst();

    }
}
