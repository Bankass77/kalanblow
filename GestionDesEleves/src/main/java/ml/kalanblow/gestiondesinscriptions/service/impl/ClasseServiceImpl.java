package ml.kalanblow.gestiondesinscriptions.service.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import ml.kalanblow.gestiondesinscriptions.exception.AnnneeScolaireAlreadyExistsException;
import ml.kalanblow.gestiondesinscriptions.exception.ClasseAlreadyExistsException;
import ml.kalanblow.gestiondesinscriptions.exception.EtablissementScolaireAlreadyExistsException;

import ml.kalanblow.gestiondesinscriptions.exception.KaladewnManagementException;
import ml.kalanblow.gestiondesinscriptions.model.AnneeScolaire;
import ml.kalanblow.gestiondesinscriptions.model.Classe;
import ml.kalanblow.gestiondesinscriptions.model.Etablissement;
import ml.kalanblow.gestiondesinscriptions.repository.AnneeScolaireRepository;
import ml.kalanblow.gestiondesinscriptions.repository.ClasseRepository;
import ml.kalanblow.gestiondesinscriptions.repository.EtablissementRepository;
import ml.kalanblow.gestiondesinscriptions.service.ClasseService;
import ml.kalanblow.gestiondesinscriptions.util.ErrorMessages;

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
            if (anneeScolaireExistant.isEmpty()) {
                throw  new KaladewnManagementException(ErrorMessages.ERROR_AnnneeScolaire_ALREADY_FOUND);
            }
        }

        Etablissement etablissementExistant = etablissementRepository.findByEtablisementScolaireId(classe.getEtablissement()
                .getEtablisementScolaireId());
        if (etablissementExistant != null && etablissementExistant.getEtablisementScolaireId() != null) {
            classe.setEtablissement(etablissementExistant);
        } else {
            throw new EtablissementScolaireAlreadyExistsException(ErrorMessages.ERROR_Admin_ALREADY_FOUND + etablissementExistant.getEtablisementScolaireId());
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
        Optional<Classe> classe1 = Optional.ofNullable(classeRepository.findById(classeId)
                .orElseThrow(() ->  new ClasseAlreadyExistsException(ErrorMessages.ERROR_Classe_ALREADY_FOUND + classeId)));

        if (classe1.isPresent()) {
            Classe classeToUpdate = classe1.get();

            // Vérifier l'existence de l'année scolaire avant de l'associer
            AnneeScolaire anneeScolaire = classe.getAnneeScolaire();
            if (anneeScolaire != null && anneeScolaire.getAnneeScolaireId() != null) {
                Optional<AnneeScolaire> anneeScolaireExistant = anneeScolaireRepository.findById(anneeScolaire.getAnneeScolaireId());
                if (!anneeScolaireExistant.isPresent()) {
                    throw  new AnnneeScolaireAlreadyExistsException(ErrorMessages.ERROR_Classe_ALREADY_FOUND +
                            "L'année scolaire n'existe pas avec cet id : " + anneeScolaire.getAnneeScolaireId());
                }
            }

            classeToUpdate.setEtablissement(classe.getEtablissement());
            classeToUpdate.setNom(classe.getNom());
            classeToUpdate.setAnneeScolaire(classe.getAnneeScolaire());
            modelMapper.map(classe, classeToUpdate);
            return Optional.of(classeRepository.save(classeToUpdate));
        }
        throw new ClasseAlreadyExistsException(ErrorMessages.ERROR_Classe_ALREADY_FOUND+
                "La classe n'est pas trouvée avec cet id : " + classeId);
    }

    /**
     * @param classeId à supprimer
     */
    @Override
    public void deleteClasse(final Long classeId) {

        if (classeRepository.existsById(classeId)) {
            classeRepository.deleteById(classeId);
        } else {

            throw new KaladewnManagementException(ErrorMessages.ERROR_Classe_NOT_FOUND+
                    "La classe n'a pas pu être supprimé avec cet id : " + classeId);
        }

    }

    /**
     * @param classeId de la classe
     * @return une Classe
     */
    @Override
    public Optional<Classe> findByClasseById( long classeId) {
        return Optional.ofNullable(classeRepository.findById(classeId)
                .orElseThrow(() ->  new  KaladewnManagementException(ErrorMessages.ERROR_Classe_NOT_FOUND+
                        "Classe non trouvée pour l'ID : " + classeId
                )));
    }

    /**
     * @param nom de la classe
     * @return une classe
     */
    @Override
    public List<Classe> findByNom(final String nom) {
        try {
            return classeRepository.findByNom(nom);
        } catch (Exception e) {

            throw  new
                    KaladewnManagementException(ErrorMessages.ERROR_Classe_NOT_FOUND+
                    "findByNom: ");
        }
    }

    /**
     * @param etablissement donnée
     * @return une classe
     */
    @Override
    public List<Classe> findByEtablissement(final Etablissement etablissement) {
        try {
            return classeRepository.findByNom(etablissement.getNomEtablissement());
        } catch (Exception e) {
            throw new KaladewnManagementException(ErrorMessages.ERROR_Classe_NOT_FOUND+
                    "findByEtablissement: ");
        }
    }

    /**
     * @param anneeScolaire année scolaire en cours
     * @return une classe
     */
    @Override
    public List<Classe> findByAnneeScolaire(final AnneeScolaire anneeScolaire) {
        try {
            return classeRepository.findByAnneeScolaire(anneeScolaire);
        } catch (Exception e) {
            throw new KaladewnManagementException(ErrorMessages.ERROR_Classe_NOT_FOUND+
                    "findByAnneeScolaire: ");
        }
    }

    /**
     * @param classeId      identifiant de la classe
     * @param etablissement données
     * @return établissement
     */
    @Override
    public Optional<Classe> findByClasseIdAndEtablissement(final Long classeId, final Etablissement etablissement) {

        try {
            return classeRepository.findByClasseIdAndEtablissement(classeId, etablissement);
        } catch (Exception e) {

            throw new KaladewnManagementException(ErrorMessages.ERROR_Classe_NOT_FOUND +
                    "findByClasseIdAndEtablissement: ");
        }
    }

    /**
     * @param etablissement données
     * @return le nombre d'établissement
     */
    @Override
    public Long countByEtablissement(final Etablissement etablissement) {
        try {
            return classeRepository.countByEtablissement(etablissement);
        } catch (Exception e) {
            throw new KaladewnManagementException( ErrorMessages.ERROR_Etablissement_NOT_FOUND +
                    "countByEtablissement: ");
        }
    }

    /**
     * @param nom de la classe
     * @return une classe
     */
    @Override
    public Optional<Classe> findByClasseName(final String nom) {
        try {
            return classeRepository.findByNom(nom).stream().findFirst();
        } catch (Exception e) {
            throw new KaladewnManagementException(ErrorMessages.ERROR_Classe_NOT_FOUND +
                    "findByClasseName: ");
        }
    }
}
