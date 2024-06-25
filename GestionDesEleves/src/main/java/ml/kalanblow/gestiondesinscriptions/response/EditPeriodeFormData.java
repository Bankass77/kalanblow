package ml.kalanblow.gestiondesinscriptions.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.enums.TypeDeVacances;
import ml.kalanblow.gestiondesinscriptions.model.Periode;
import ml.kalanblow.gestiondesinscriptions.request.CreatePeriodeParameters;
import ml.kalanblow.gestiondesinscriptions.request.EditPeriodeParameters;

import java.time.Duration;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class EditPeriodeFormData extends CreatePeriodeParameters {

    private long version;

    private int annee;

    private Duration duree;

    private TypeDeVacances typeDeVacances;

    public static EditPeriodeFormData fromAnneeScolaireParameters(Periode anneeScolaire) {

        EditPeriodeFormData editAnneeScolaireFormData = new EditPeriodeFormData();

        editAnneeScolaireFormData.setAnnee(anneeScolaire.getAnnee());
        editAnneeScolaireFormData.setDuree(anneeScolaire.getDuree());
        editAnneeScolaireFormData.setTypeDeVacances(anneeScolaire.getTypeDeVacances());
        editAnneeScolaireFormData.setVersion(anneeScolaire.getVersion());
        return editAnneeScolaireFormData;

    }

    public EditPeriodeParameters toParameters(){

        EditPeriodeParameters editAnneScolaireParameters= new EditPeriodeParameters();
        editAnneScolaireParameters.setAnnee(getAnnee());
        editAnneScolaireParameters.setVersion(version);
        editAnneScolaireParameters.setDuree(getDuree());
        editAnneScolaireParameters.setTypeDeVacances(getTypeDeVacances());
        return  editAnneScolaireParameters;
    }



}
