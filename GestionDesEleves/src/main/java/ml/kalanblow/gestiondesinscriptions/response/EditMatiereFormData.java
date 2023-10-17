package ml.kalanblow.gestiondesinscriptions.response;


import lombok.Data;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.Matiere;
import ml.kalanblow.gestiondesinscriptions.request.EditMatiereParameters;

@NoArgsConstructor
@Data
public class EditMatiereFormData extends CreateMatiereFormData {

    public static EditMatiereFormData fromMatiere(Matiere matiere) {


        EditMatiereFormData editMatiereFormData = new EditMatiereFormData();

        editMatiereFormData.setNomMatiere(matiere.getNomMatiere());
        editMatiereFormData.setCoefficient(matiere.getCoefficient());
        editMatiereFormData.setCoursDEnseignements(matiere.getCoursDEnseignements());
        editMatiereFormData.setNote(matiere.getNote());
        editMatiereFormData.setMoyenne(matiere.getMoyenne());
        editMatiereFormData.setDescription(matiere.getDescription());

        return editMatiereFormData;
    }


    public EditMatiereParameters toParameters() {

        EditMatiereParameters editMatiereParameters = new EditMatiereParameters();
        editMatiereParameters.setCoefficient(getCoefficient());
        editMatiereParameters.setDescription(getDescription());
        editMatiereParameters.setNomMatiere(getNomMatiere());
        editMatiereParameters.setNote(getNote());
        editMatiereParameters.setCoursDEnseignements(getCoursDEnseignements());

        return editMatiereParameters;
    }
}
