package ml.kalanblow.gestiondesinscriptions.service;

import ml.kalanblow.gestiondesinscriptions.model.*;
import ml.kalanblow.gestiondesinscriptions.request.CreateCoursParameters;
import ml.kalanblow.gestiondesinscriptions.request.EditCoursParameters;

import java.util.Optional;

public interface CoursDEnseignementService {

    Optional<Cours> findDistinctByEnseignantAndAnneeScolaire(Enseignant enseignant, Periode anneeScolaire);

    Optional<Cours> findDistinctByHoraireClassesAndAndSalleDeClasseAndMatiere(Horaire horaireClasse, Salle salleDeClasse, Matiere matiere);

    Optional<Cours> findDistinctByEnseignantOrSalleDeClasseAndAndHoraireClasses(Enseignant enseignant, Salle salleDeClasse, Horaire horaireClasse);

    Optional<Cours> creerCoursDEnseignement(EditCoursParameters nouveauCoursDEnseignement);

    Optional<Cours> creerCoursDEnseignement(CreateCoursParameters nouveauCoursDEnseignement);

    Optional<Cours> editerCoursDEnseignement(Long id, EditCoursParameters editCoursDEnseignementParameters);
}
