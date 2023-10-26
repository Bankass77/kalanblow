package ml.kalanblow.gestiondesinscriptions.service.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import ml.kalanblow.gestiondesinscriptions.model.Cours;
import ml.kalanblow.gestiondesinscriptions.model.Matiere;
import ml.kalanblow.gestiondesinscriptions.repository.MatiereRepository;
import ml.kalanblow.gestiondesinscriptions.request.CreateMatiereParameters;
import ml.kalanblow.gestiondesinscriptions.request.EditMatiereParameters;
import ml.kalanblow.gestiondesinscriptions.service.MatiereService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
@Slf4j
public class MatiereSerciceImpl  implements MatiereService {


    private final MatiereRepository matiereRepository;

    @Autowired
    public MatiereSerciceImpl(MatiereRepository matiereRepository){

        this.matiereRepository=matiereRepository;
    }


    /**
     * Recherche une matière dont le nom contient la chaîne spécifiée (insensible à la casse).
     *
     * @param nomMatiere La chaîne de caractères à rechercher dans le nom de la matière.
     * @return Une instance facultative (Optional) de la matière trouvée, ou une instance vide si aucune matière correspondante n'est trouvée.
     */
    @Override
    public Optional<Matiere> getMatieresByNomMatiereContainsIgnoreCase(String nomMatiere) {

        log.info("Une matière a été trouvée: {}", nomMatiere);
        return  matiereRepository.getMatieresByNomMatiereContainsIgnoreCase(nomMatiere);
    }

    /**
     * Recherche toutes les matières associées à un cours d'enseignement spécifique, triées par le nom de la matière.
     *
     * @param coursDEnseignement Le cours d'enseignement pour lequel rechercher les matières.
     * @return Une instance facultative (Optional) des matières trouvées, triées par le nom de la matière, ou une instance vide si aucune matière n'est associée au cours d'enseignement.
     */
    @Override
    public Optional<List<Matiere>> searchAllByCoursDEnseignementsOrderByNomMatiere(Cours coursDEnseignement) {
        log.info("Une matière  a été trouvée pour ce cours: {}", coursDEnseignement);
        return matiereRepository.searchAllByCoursDEnseignementsOrderByNomMatiere(coursDEnseignement);
    }

    /**
     * Crée une nouvelle matière avec les paramètres spécifiés.
     *
     * @param matiereParameters Les paramètres pour la création de la matière.
     * @return Une option contenant la matière nouvellement créée.
     */
    @Override
    public Optional<Matiere> creerMatieres(CreateMatiereParameters matiereParameters) {

        Matiere.MatiereBuilder matiereBuilder = new Matiere.MatiereBuilder();

        matiereBuilder.note(matiereParameters.getNote());
        matiereBuilder.description(matiereParameters.getDescription());
        matiereBuilder.moyenne(matiereParameters.getMoyenne());
        matiereBuilder.nomMatiere(matiereParameters.getNomMatiere());
        matiereBuilder.cours(matiereParameters.getCoursDEnseignements());
        matiereBuilder.coefficient(matiereParameters.getCoefficient());

        Matiere nouvelleMatiere= Matiere.MatiereBuilder.creerMatiere(matiereBuilder);

        log.info("Une matière  a été crée: {}", nouvelleMatiere);
        return Optional.of(matiereRepository.save(nouvelleMatiere));
    }

    /**
     * Édite une matière existante avec les paramètres spécifiés.
     *
     * @param editMatiereParameters Les paramètres pour l'édition de la matière.
     * @return Une option contenant la matière éditée.
     */
    @Override
    public Optional<Matiere> editerMatiere(Long id, EditMatiereParameters editMatiereParameters) {

        Optional<Matiere> matiere= matiereRepository.findDistinctByIdOrAndAndNomMatiere(id, editMatiereParameters.getNomMatiere());

        if ( editMatiereParameters.getVersion() != matiere.get().getVersion()) {

            throw  new ObjectOptimisticLockingFailureException( Matiere.class, matiere.get().getId());
        }

        if (matiere.isPresent()){

            matiere.get().setNomMatiere(editMatiereParameters.getNomMatiere());

            matiere.get().setNote(editMatiereParameters.getNote());
            matiere.get().setCoursDEnseignements(editMatiereParameters.getCoursDEnseignements());
            matiere.get().setDescription(editMatiereParameters.getDescription());
            matiere.get().setMoyenne(editMatiereParameters.getMoyenne());
            matiere.get().setCoefficient(editMatiereParameters.getCoefficient());

              editMatiereParameters.updateMatiere(matiere.get());
            log.info("La matière  a été modifiéé: {}", matiere.get());
        }
        return matiere;
    }
}
