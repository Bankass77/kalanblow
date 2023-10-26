package ml.kalanblow.gestiondesinscriptions.service.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import ml.kalanblow.gestiondesinscriptions.enums.TypeDeClasse;
import ml.kalanblow.gestiondesinscriptions.model.Cours;
import ml.kalanblow.gestiondesinscriptions.model.Salle;
import ml.kalanblow.gestiondesinscriptions.repository.SalleRepository;
import ml.kalanblow.gestiondesinscriptions.request.CreateSalleParameters;
import ml.kalanblow.gestiondesinscriptions.request.EditSalleParameters;
import ml.kalanblow.gestiondesinscriptions.service.SalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class SalleServiceImpl implements SalleService {


    private final SalleRepository salleDeClasseRepository;

    @Autowired
    public SalleServiceImpl(SalleRepository salleDeClasseRepository) {
        this.salleDeClasseRepository = salleDeClasseRepository;
    }

    /**
     * Recherche une salle de classe distincte en fonction du cours d'enseignement et du nom de la salle ou du type de classe spécifiés.
     *
     * @param coursDEnseignement Le cours d'enseignement pour lequel rechercher la salle de classe.
     * @param salleDeClasse      La salle de classe ou le type de classe pour lequel rechercher.
     * @param typeDeClasse
     * @return Une instance facultative (Optional) de la salle de classe trouvée, ou une instance vide si aucune salle de classe correspondante n'est trouvée.
     */
    @Override
    public Optional<Salle> findDistinctByCoursPlanifiesAndAndNomDeLaSalleOrTypeDeClasse(Cours coursDEnseignement, Salle salleDeClasse, TypeDeClasse typeDeClasse) {
        return salleDeClasseRepository.findDistinctByCoursPlanifiesAndAndNomDeLaSalleOrTypeDeClasse(coursDEnseignement, salleDeClasse, typeDeClasse);
    }

    /**
     * Compte le nombre de salles de classe en fonction du cours d'enseignement et du nom de la salle spécifiés.
     *
     * @param coursDEnseignement Le cours d'enseignement pour lequel compter les salles de classe.
     * @param salleDeClasse      Le nom de la salle de classe à rechercher.
     * @return Une instance facultative (Optional) contenant le nombre de salles de classe correspondantes, ou une instance vide si aucune salle de classe ne correspond.
     */
    @Override
    public Optional<Salle> countSalleDeClasseByCoursPlanifiesAndNomDeLaSalle(Cours coursDEnseignement, Salle salleDeClasse) {
        return salleDeClasseRepository.countSalleDeClasseByCoursPlanifiesAndNomDeLaSalle(coursDEnseignement, salleDeClasse);
    }

    /**
     * Obtient une salle de classe en fonction du cours d'enseignement spécifié.
     *
     * @param coursDEnseignement Le cours d'enseignement pour lequel rechercher la salle de classe.
     * @return Une instance facultative (Optional) de la salle de classe trouvée, ou une instance vide si aucune salle de classe correspondante n'est trouvée.
     */
    @Override
    public Optional<Salle> getSalleDeClasseByCoursPlanifies(Cours coursDEnseignement) {
        return salleDeClasseRepository.getSalleDeClasseByCoursPlanifies(coursDEnseignement);
    }

    /**
     * Crée une nouvelle salle de classe en utilisant les paramètres spécifiés.
     *
     * @param createSalleParameters Les paramètres pour créer une salle de classe.
     * @return Une option contenant la nouvelle salle de classe si la création réussit, sinon Option vide.
     */
    @Override
    public Optional<Salle> createSalleDeClasse(CreateSalleParameters createSalleParameters) {


        Salle salle = new Salle();

        salle.setNomDeLaSalle(createSalleParameters.getNomDeLaSalle());
        salle.setEleves(createSalleParameters.getEtablissement().getEleves());
        salle.setHoraires(createSalleParameters.getCoursPlanifies().stream().flatMap(cours -> cours.getHoraires().stream()).collect(Collectors.toSet()));
        salle.setEtablissement(createSalleParameters.getEtablissement());
        salle.setCoursPlanifies(createSalleParameters.getCoursPlanifies());
        salle.setNombreDePlace(createSalleParameters.getNombreDePlace());
        salle.setTypeDeClasse(createSalleParameters.getTypeDeClasse());

      return Optional.of(salleDeClasseRepository.save(salle));

    }

    /**
     * Modifie une salle de classe existante identifiée par l'ID spécifié en utilisant les paramètres d'édition spécifiés.
     *
     * @param id                  L'identifiant de la salle de classe à éditer.
     * @param editSalleParameters Les paramètres pour effectuer la modification.
     * @return Une option contenant la salle de classe modifiée si l'édition réussit, sinon Option vide.
     */
    @Override
    public Optional<Salle> doEditSalleDeClasse(Long id, EditSalleParameters editSalleParameters) {


        Optional<Salle> salle= salleDeClasseRepository.findById(id);

        if (editSalleParameters.getVersion() != salle.get().getVersion()){

            throw  new ObjectOptimisticLockingFailureException( Salle.class, salle.get().getId());
        }
          editSalleParameters.updateSalleDeClasse(salle.get());

        return  salle;
    }
}
