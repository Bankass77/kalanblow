package ml.kalanblow.gestiondesinscriptions.request;


import lombok.Data;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.Periode;


@NoArgsConstructor

@Data
public class EditAnneScolaireParameters extends CreateAnneScolaireParameters {

    private  long version;
    public void updateAnneeScolaire(Periode anneeScolaire) {

        Periode.PeriodeBuilder builder = new Periode.PeriodeBuilder();
        builder.dureeAnneeScolaire(getDuree());
        builder.typeDeVacancesBuilder(getTypeDeVacances());
        builder.periodeBuilder(getAnnee());
        anneeScolaire = builder.build();


    }
}
