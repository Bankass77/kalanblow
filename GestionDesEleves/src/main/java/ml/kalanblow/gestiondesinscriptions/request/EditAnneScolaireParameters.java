package ml.kalanblow.gestiondesinscriptions.request;


import lombok.Data;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.AnneeScolaire;


@NoArgsConstructor

@Data
public class EditAnneScolaireParameters extends CreateAnneScolaireParameters {

    public void updateAnneeScolaire(AnneeScolaire anneeScolaire) {

        AnneeScolaire.AnneeScolaireBuilder builder = new AnneeScolaire.AnneeScolaireBuilder();
        builder.dureeAnneeScolaire(getDuree());
        builder.typeDeVacancesBuilder(getTypeDeVacances());
        builder.anneeScolaireBuilder(getAnnee());
        anneeScolaire = builder.build();


    }
}
