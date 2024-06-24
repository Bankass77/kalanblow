package ml.kalanblow.gestiondesinscriptions.service;

import ml.kalanblow.gestiondesinscriptions.model.Periode;

import java.util.Optional;

public interface AnneeScolaireService {

    Optional<Periode> deleteAnneeScolaireByAnneeOrId(Periode anneeScolaire, int id);

    Optional<Periode> findAnneeScolairesByAnnee(int annee);
}
