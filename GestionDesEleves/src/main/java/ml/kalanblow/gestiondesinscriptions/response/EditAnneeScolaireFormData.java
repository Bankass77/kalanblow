package ml.kalanblow.gestiondesinscriptions.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.enums.TypeDeVacances;
import ml.kalanblow.gestiondesinscriptions.model.Periode;
import ml.kalanblow.gestiondesinscriptions.request.EditAnneScolaireParameters;

import java.time.Duration;


@Data
@NoArgsConstructor
public class EditAnneeScolaireFormData {

    private long version;

    private int annee;

    private Duration duree;

    private TypeDeVacances typeDeVacances;

    public static EditAnneeScolaireFormData fromAnneeScolaireParameters(Periode anneeScolaire) {

        EditAnneeScolaireFormData editAnneeScolaireFormData = new EditAnneeScolaireFormData();

        editAnneeScolaireFormData.setAnnee(anneeScolaire.getAnnee());
        editAnneeScolaireFormData.setDuree(anneeScolaire.getDuree());
        editAnneeScolaireFormData.setTypeDeVacances(anneeScolaire.getTypeDeVacances());
        editAnneeScolaireFormData.setVersion(anneeScolaire.getVersion());
        return editAnneeScolaireFormData;

    }

    public EditAnneScolaireParameters toParameters(){

        EditAnneScolaireParameters editAnneScolaireParameters= new EditAnneScolaireParameters();
        editAnneScolaireParameters.setAnnee(getAnnee());
        editAnneScolaireParameters.setVersion(version);
        editAnneScolaireParameters.setDuree(getDuree());
        editAnneScolaireParameters.setTypeDeVacances(getTypeDeVacances());
        return  editAnneScolaireParameters;
    }



}
