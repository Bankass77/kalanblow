package ml.kalanblow.gestiondesinscriptions.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.AnneeScolaire;
import ml.kalanblow.gestiondesinscriptions.request.CreateAnneScolaireParameters;


@Data
@NoArgsConstructor
public class EditAnneeScolaireFormData extends CreateAnneScolaireParameters {


    public static EditAnneeScolaireFormData fromAnneeScolaireParameters(AnneeScolaire anneeScolaire) {

        EditAnneeScolaireFormData editAnneeScolaireFormData = new EditAnneeScolaireFormData();

        editAnneeScolaireFormData.setAnnee(anneeScolaire.getAnnee());
        editAnneeScolaireFormData.setDuree(anneeScolaire.getDuree());
        editAnneeScolaireFormData.setTypeDeVacances(anneeScolaire.getTypeDeVacances());
        return editAnneeScolaireFormData;

    }



}
