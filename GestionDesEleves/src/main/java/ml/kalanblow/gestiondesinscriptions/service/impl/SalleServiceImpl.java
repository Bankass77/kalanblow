package ml.kalanblow.gestiondesinscriptions.service.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import ml.kalanblow.gestiondesinscriptions.enums.TypeDeClasse;
import ml.kalanblow.gestiondesinscriptions.exception.KaladewnManagementException;
import ml.kalanblow.gestiondesinscriptions.model.Cours;
import ml.kalanblow.gestiondesinscriptions.model.Salle;
import ml.kalanblow.gestiondesinscriptions.repository.SalleRepository;
import ml.kalanblow.gestiondesinscriptions.request.CreateSalleParameters;
import ml.kalanblow.gestiondesinscriptions.request.EditSalleParameters;
import ml.kalanblow.gestiondesinscriptions.service.SalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class SalleServiceImpl implements SalleService {


    private final SalleRepository salleDeClasseRepository;

    private final ConcurrentHashMap<Long, Lock> salleLocks = new ConcurrentHashMap<>();

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

        TypeDeClasse typeDeClasse = createSalleParameters.getTypeDeClasse();
        LocalDateTime salleReservationDate = createSalleParameters.getSalleReservationDate();
        LocalDateTime salleLibreDate = createSalleParameters.getSalleLibreDate();


        Optional<Salle> salle = salleDeClasseRepository.findSallesByTypeDeClasse(typeDeClasse);

        if (salle.isPresent()) {

            Lock salleLock = salleLocks.computeIfPresent(salle.get().getId(), (id , existingLock)-> new ReentrantLock());
            salleLock.lock();

            try {
                if (salle.get().isAvailable()) {

                    salle.get().setAvailable(false);
                    salle.get().setNomDeLaSalle(createSalleParameters.getNomDeLaSalle());
                    salle.get().setEleves(createSalleParameters.getEtablissement().getEleves());
                    salle.get().setHoraires(createSalleParameters.getCoursPlanifies().stream().flatMap(cours -> cours.getHoraires().stream()).collect(Collectors.toSet()));
                    salle.get().setEtablissement(createSalleParameters.getEtablissement());
                    salle.get().setCoursPlanifies(createSalleParameters.getCoursPlanifies());
                    salle.get().setNombreDePlace(createSalleParameters.getNombreDePlace());
                    salle.get().setTypeDeClasse(createSalleParameters.getTypeDeClasse());
                    return Optional.of(salleDeClasseRepository.save(salle.get()));
                }
            } finally {

                salleLock.unlock();
            }
        }

        throw new KaladewnManagementException.EntityNotFoundException("La salle de classe ne peut être ajouter.");
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


        Optional<Salle> salle = salleDeClasseRepository.findById(id);

        if (editSalleParameters.getVersion() != salle.get().getVersion()) {

            throw new ObjectOptimisticLockingFailureException(Salle.class, salle.get().getId());
        }
        editSalleParameters.updateSalleDeClasse(salle.get());

        return salle;
    }

    @Override
    public Set<Salle> getAllSalles() {
        return new HashSet<>(salleDeClasseRepository.findAll());
    }
    @Override
    public void deleteSalleDeClasse(Long id) {
        Optional<Salle> salleOpt = salleDeClasseRepository.findById(id);

        salleOpt.ifPresent(salle -> {
            salleDeClasseRepository.deleteById(id);
            // Vous pouvez également effectuer d'autres opérations liées à la suppression si nécessaire
        });
    }

    @Override
    public Optional<Salle> getTimeslotById(Long id) {
        return  salleDeClasseRepository.findById(id);
    }


}
