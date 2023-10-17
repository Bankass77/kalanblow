package ml.kalanblow.gestiondesinscriptions.response;


import lombok.Data;
import lombok.EqualsAndHashCode;
import ml.kalanblow.gestiondesinscriptions.model.AbsenceEleve;
import ml.kalanblow.gestiondesinscriptions.request.CreateAbsenceEleveParameters;
import ml.kalanblow.gestiondesinscriptions.request.EditAbsenceEleveParameters;
import ml.kalanblow.gestiondesinscriptions.request.EditEleveParameters;

@Data
@EqualsAndHashCode(callSuper = false)
public class EditAbsenceEleveFormData extends CreateAbsenceEleveParameters {

    public static EditAbsenceEleveFormData fromAbsenceEleve(AbsenceEleve absenceEleve) {

        EditAbsenceEleveFormData editAbsenceEleveFormData = new EditAbsenceEleveFormData();
        editAbsenceEleveFormData.setAppelDePresence(absenceEleve.getAppelDePresence());
        editAbsenceEleveFormData.setEleve(absenceEleve.getEleve());
        editAbsenceEleveFormData.setMotif(absenceEleve.getMotif());
        editAbsenceEleveFormData.setCours(absenceEleve.getCours());
        editAbsenceEleveFormData.setEstJustifiee(absenceEleve.isEstJustifiee());

        return editAbsenceEleveFormData;
    }

    public EditAbsenceEleveParameters toAbsenceParameters() {

        EditAbsenceEleveParameters editEleveParameters = new EditAbsenceEleveParameters();

        editEleveParameters.setAppelDePresence(getAppelDePresence());
        editEleveParameters.setCours(getCours());
        editEleveParameters.setEleve(getEleve());
        editEleveParameters.setHoraireClasse(getHoraireClasse());
        editEleveParameters.setMotif(getMotif());
        editEleveParameters.setEstJustifiee(isEstJustifiee());
        return editEleveParameters;


    }
}
