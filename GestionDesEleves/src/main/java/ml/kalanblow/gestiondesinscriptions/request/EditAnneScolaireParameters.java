package ml.kalanblow.gestiondesinscriptions.request;

import ml.kalanblow.gestiondesinscriptions.model.AnneeScolaire;

public class EditAnneScolaireParameters extends CreateAnneScolaireParameters {

    public void updateAnneeScolaire(AnneeScolaire anneeScolaire) {

        anneeScolaire.setAnnee(getAnnee());
        anneeScolaire.setDuree(getDuree());
        anneeScolaire.setTypeDeVacances(getTypeDeVacances());
    }
}
