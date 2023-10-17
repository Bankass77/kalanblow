package ml.kalanblow.gestiondesinscriptions.request;


import lombok.Data;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.Matiere;

@NoArgsConstructor
@Data
public class EditMatiereParameters extends CreateMatiereParameters {


    public void updateMatiere(Matiere matiere) {


        Matiere.MatiereBuilder builder = new Matiere.MatiereBuilder();

        builder.cours(getCoursDEnseignements());
        builder.note(getNote());
        builder.nomMatiere(getNomMatiere());
        builder.coefficient(getCoefficient());
        builder.moyenne(getMoyenne());
        builder.description(getDescription());
        matiere= builder.build();

    }
}
