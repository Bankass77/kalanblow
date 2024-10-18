package ml.kalanblow.gestiondescours.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import ml.kalanblow.gestiondescours.exception.EntityType;
import ml.kalanblow.gestiondescours.exception.ExceptionType;
import ml.kalanblow.gestiondescours.exception.KaladewnManagementException;
import ml.kalanblow.gestiondescours.model.Matiere;
import ml.kalanblow.gestiondescours.service.MatiereService;

@Slf4j
@Service
public class MatiereServiceImpl implements MatiereService {

    private final MatiereService matiereService;

    @Autowired
    public MatiereServiceImpl(@Lazy final MatiereService matiereService) {
        this.matiereService = matiereService;
    }

    /**
     * @param matiere 
     * @return
     */
    @Override
    public List<Matiere> findAllMatieresByEnseignantResponsableMatieres(final Matiere matiere) {

        try {
            return matiereService.findAllMatieresByEnseignantResponsableMatieres(matiere);
        }catch (Exception e){

            throw KaladewnManagementException.throwExceptionWithId(EntityType.MATIERE, ExceptionType.ENTITY_NOT_FOUND, e.getMessage());
        }
    }

    /**
     * @param id 
     * @return
     */
    @Override
    public Optional<Matiere> findMatiereById(final long id) {
       return  matiereService.findMatiereById(id);
    }

    /**
     * @param matiere 
     * @return
     */
    @Override
    public Matiere create(final Matiere matiere) {
        return matiereService.create(matiere);
    }

    /**
     * @param id 
     * @param matiere
     * @return
     */
    @Override
    public Matiere updateMatiere(final long id, final Matiere matiere) {

        Optional<Matiere> matiereOptional = findMatiereById(id);
        if (matiereOptional.isPresent()){

            matiereOptional.get().setEnseignantResponsableMatieres(matiere.getEnseignantResponsableMatieres());
            matiereOptional.get().setNom(matiere.getNom());
            matiereOptional.get().setCoefficient(matiere.getCoefficient());
            matiereOptional.get().setNombreHeures(matiere.getNombreHeures());
            matiereOptional.get().setDescription(matiere.getDescription());
        }
        return matiereOptional.get();
    }
}
