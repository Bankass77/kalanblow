package ml.kalanblow.gestiondesinscriptions.service.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import ml.kalanblow.gestiondesinscriptions.enums.TypeDeClasse;
import ml.kalanblow.gestiondesinscriptions.model.Cours;
import ml.kalanblow.gestiondesinscriptions.model.Salle;
import ml.kalanblow.gestiondesinscriptions.repository.SalleRepository;
import ml.kalanblow.gestiondesinscriptions.service.SalleDeClasseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@Slf4j
public class SalleDeClasseServiceImpl implements SalleDeClasseService {


    private final SalleRepository salleDeClasseRepository;

    @Autowired
    public SalleDeClasseServiceImpl(SalleRepository salleDeClasseRepository) {
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
        return  salleDeClasseRepository.findDistinctByCoursPlanifiesAndAndNomDeLaSalleOrTypeDeClasse(coursDEnseignement,salleDeClasse,typeDeClasse);
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
        return salleDeClasseRepository.countSalleDeClasseByCoursPlanifiesAndNomDeLaSalle(coursDEnseignement,salleDeClasse);
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
}
