package ml.kalanblow.gestiondesinscriptions.service;

import ml.kalanblow.gestiondesinscriptions.enums.TypeDeClasse;
import ml.kalanblow.gestiondesinscriptions.model.Cours;
import ml.kalanblow.gestiondesinscriptions.model.Salle;

import java.util.Optional;

public interface SalleDeClasseService {

    Optional<Salle> findDistinctByCoursPlanifiesAndAndNomDeLaSalleOrTypeDeClasse(Cours coursDEnseignement, Salle salleDeClasse, TypeDeClasse typeDeClasse);

    Optional<Salle> countSalleDeClasseByCoursPlanifiesAndNomDeLaSalle(Cours coursDEnseignement, Salle salleDeClasse);

    Optional<Salle> getSalleDeClasseByCoursPlanifies(Cours coursDEnseignement);
    Optional<Salle> findById(long salleId);
}
