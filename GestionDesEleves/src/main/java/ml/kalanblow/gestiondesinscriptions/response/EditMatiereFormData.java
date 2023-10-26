package ml.kalanblow.gestiondesinscriptions.response;


import lombok.Data;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.Cours;
import ml.kalanblow.gestiondesinscriptions.model.Matiere;
import ml.kalanblow.gestiondesinscriptions.request.EditMatiereParameters;

import java.util.Set;

@NoArgsConstructor
@Data
public class EditMatiereFormData  {

    private Long id;
    private long version;
    private double note;
    private double coefficient;
    private double moyenne;
    private String nomMatiere;
    private String description;
    private Set<Cours> coursDEnseignements;

    public static EditMatiereFormData fromMatiere(Matiere matiere) {

        EditMatiereFormData editMatiereFormData = new EditMatiereFormData();
        editMatiereFormData.setNomMatiere(matiere.getNomMatiere());
        editMatiereFormData.setCoefficient(matiere.getCoefficient());
        editMatiereFormData.setCoursDEnseignements(matiere.getCoursDEnseignements());
        editMatiereFormData.setNote(matiere.getNote());
        editMatiereFormData.setMoyenne(matiere.getMoyenne());
        editMatiereFormData.setDescription(matiere.getDescription());
        editMatiereFormData.setVersion(matiere.getVersion());
        editMatiereFormData.setId(matiere.getId());
        return editMatiereFormData;
    }


    public EditMatiereParameters toParameters() {

        EditMatiereParameters editMatiereParameters = new EditMatiereParameters();
        editMatiereParameters.setCoefficient(getCoefficient());
        editMatiereParameters.setDescription(getDescription());
        editMatiereParameters.setNomMatiere(getNomMatiere());
        editMatiereParameters.setNote(getNote());
        editMatiereParameters.setCoursDEnseignements(getCoursDEnseignements());
        editMatiereParameters.setVersion(version);

        return editMatiereParameters;
    }
}
