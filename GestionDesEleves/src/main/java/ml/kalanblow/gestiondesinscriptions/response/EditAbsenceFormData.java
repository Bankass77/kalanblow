package ml.kalanblow.gestiondesinscriptions.response;


import lombok.Data;
import lombok.EqualsAndHashCode;
import ml.kalanblow.gestiondesinscriptions.model.*;
import ml.kalanblow.gestiondesinscriptions.request.EditAbsenceParameters;

@Data
@EqualsAndHashCode(callSuper = false)
public class EditAbsenceFormData {

    private long version;

    private Horaire horaireClasse;

    private String motif;

    private boolean estJustifiee;

    private Cours cours;

    private Presence presence;

    private Eleve eleve;

    public static EditAbsenceFormData fromAbsenceEleve(Absence absenceEleve) {

        EditAbsenceFormData editAbsenceEleveFormData = new EditAbsenceFormData();
        editAbsenceEleveFormData.setPresence(absenceEleve.getPresence());
        editAbsenceEleveFormData.setEleve(absenceEleve.getEleve());
        editAbsenceEleveFormData.setMotif(absenceEleve.getMotif());
        editAbsenceEleveFormData.setCours(absenceEleve.getCours());
        editAbsenceEleveFormData.setEstJustifiee(absenceEleve.isEstJustifiee());
        editAbsenceEleveFormData.setVersion(absenceEleve.getVersion());

        return editAbsenceEleveFormData;
    }

    public EditAbsenceParameters toAbsenceParameters() {

        EditAbsenceParameters editEleveParameters = new EditAbsenceParameters();

        editEleveParameters.setPresence(getPresence());
        editEleveParameters.setCours(getCours());
        editEleveParameters.setEleve(getEleve());
        editEleveParameters.setHoraireClasse(getHoraireClasse());
        editEleveParameters.setMotif(getMotif());
        editEleveParameters.setEstJustifiee(isEstJustifiee());
        editEleveParameters.setVersion(version);
        return editEleveParameters;


    }
}
