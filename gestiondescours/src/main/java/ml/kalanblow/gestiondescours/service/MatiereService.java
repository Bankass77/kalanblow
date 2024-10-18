package ml.kalanblow.gestiondescours.service;

import java.util.List;
import java.util.Optional;

import ml.kalanblow.gestiondescours.model.Matiere;

public interface MatiereService {
    List<Matiere> findAllMatieresByEnseignantResponsableMatieres(Matiere matiere);
    Optional<Matiere> findMatiereById(long id);
    Matiere create (Matiere matiere);

    Matiere updateMatiere(long id, Matiere matiere);
}
