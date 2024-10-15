package ml.kalanblow.gestiondesinscriptions.service.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import ml.kalanblow.gestiondesinscriptions.exception.EntityType;
import ml.kalanblow.gestiondesinscriptions.exception.ExceptionType;
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
    public ClasseServiceImpl(final ClasseRepository classeRepository, AnneeScolaireRepository anneeScolaireRepository,
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
            if (!anneeScolaireExistant.isPresent()) {
                throw new KaladewnManagementException().throwException(
                        EntityType.CLASSE, ExceptionType.ENTITY_EXCEPTION,
                        "L'année scolaire n'existe pas avec cet id : " + anneeScolaire.getAnneeScolaireId());
            }
        } else {
            // Si l'année scolaire n'existe pas, ou est null, lever une exception ou gérer la création
            throw new KaladewnManagementException().throwException(
                    EntityType.CLASSE, ExceptionType.ENTITY_EXCEPTION,
                    "L'année scolaire doit être spécifiée.");
        }

        Etablissement etablissementExistant = etablissementRepository.findByEtablisementScolaireId(classe.getEtablissement()
                .getEtablisementScolaireId());
        if (etablissementExistant != null && etablissementExistant.getEtablisementScolaireId() != null) {
            classe.setEtablissement(etablissementExistant);
        } else {
            throw new KaladewnManagementException().throwException(
                    EntityType.CLASSE, ExceptionType.ENTITY_EXCEPTION,
                    "L'Etablissement scolaire n'existe pas avec cet id  pour cette classe: " + etablissementExistant.getEtablisementScolaireId());
        }

        return classeRepository.save(classe);
    }

    /**
     * @param classeId identifiant de la classe
     * @param classe   de l'élève
     * @return une classe mise à jour.
     */
    @Override
    public Classe updateClasse(final Long classeId, final Classe classe) {
        Optional<Classe> classe1 = Optional.ofNullable(classeRepository.findById(classeId)
                .orElseThrow(() -> new KaladewnManagementException().throwException(EntityType.CLASSE, ExceptionType.ENTITY_EXCEPTION,
                        "La classe n'est pas trouvée avec cet id : " + classeId)));

        if (classe1.isPresent()) {
            Classe classeToUpdate = classe1.get();

            // Vérifier l'existence de l'année scolaire avant de l'associer
            AnneeScolaire anneeScolaire = classe.getAnneeScolaire();
            if (anneeScolaire != null && anneeScolaire.getAnneeScolaireId() != null) {
                Optional<AnneeScolaire> anneeScolaireExistant = anneeScolaireRepository.findById(anneeScolaire.getAnneeScolaireId());
                if (!anneeScolaireExistant.isPresent()) {
                    throw new KaladewnManagementException().throwException(
                            EntityType.CLASSE, ExceptionType.ENTITY_EXCEPTION,
                            "L'année scolaire n'existe pas avec cet id : " + anneeScolaire.getAnneeScolaireId());
                }
            } else {
                throw new KaladewnManagementException().throwException(
                        EntityType.CLASSE, ExceptionType.ENTITY_EXCEPTION,
                        "L'année scolaire doit être spécifiée.");
            }

            classeToUpdate.setEtablissement(classe.getEtablissement());
            classeToUpdate.setNom(classe.getNom());
            classeToUpdate.setVersion(classe.getVersion());
            classeToUpdate.setAnneeScolaire(classe.getAnneeScolaire());
            modelMapper.map(classe, classeToUpdate);
            return classeRepository.save(classeToUpdate);
        }
        throw new KaladewnManagementException().throwException(EntityType.SALLEDECLASSE, ExceptionType.ENTITY_EXCEPTION,
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

            throw new KaladewnManagementException().throwException(EntityType.SALLEDECLASSE, ExceptionType.ENTITY_EXCEPTION,
                    "La classe n'a pas pu être supprimé avec cet id : " + classeId);
        }

    }

    /**
     * @param id de la classe
     * @return une Classe
     */
    @Override
    public Optional<Classe> findByClasseId(final long id) {
       try {
           return  classeRepository.findByClasseId(id);
       }catch (Exception e){
           throw new KaladewnManagementException().throwException(EntityType.SALLEDECLASSE, ExceptionType.ENTITY_EXCEPTION,
                   "findByClasseId : " + id);
       }
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

            throw KaladewnManagementException.throwExceptionWithTemplate(EntityType.CLASSE, ExceptionType.ENTITY_NOT_FOUND,
                    "findByNom: ", e.getMessage());
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
            throw KaladewnManagementException.throwExceptionWithTemplate(EntityType.CLASSE, ExceptionType.ENTITY_NOT_FOUND,
                    "findByEtablissement: ", e.getMessage());
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
            throw KaladewnManagementException.throwExceptionWithTemplate(EntityType.CLASSE, ExceptionType.ENTITY_NOT_FOUND,
                    "findByAnneeScolaire: ", e.getMessage());
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

            throw KaladewnManagementException.throwExceptionWithTemplate(EntityType.CLASSE, ExceptionType.ENTITY_NOT_FOUND,
                    "findByClasseIdAndEtablissement: ", e.getMessage());
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
            throw KaladewnManagementException.throwExceptionWithTemplate(EntityType.CLASSE, ExceptionType.ENTITY_NOT_FOUND,
                    "countByEtablissement: ", e.getMessage());
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
            throw KaladewnManagementException.throwExceptionWithTemplate(EntityType.CLASSE, ExceptionType.ENTITY_NOT_FOUND,
                    "findByClasseName: ", e.getMessage());
        }
    }
}
