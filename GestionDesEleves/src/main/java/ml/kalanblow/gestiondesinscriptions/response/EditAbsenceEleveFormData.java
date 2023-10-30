package ml.kalanblow.gestiondesinscriptions.response;


import lombok.Data;
import lombok.EqualsAndHashCode;
import ml.kalanblow.gestiondesinscriptions.model.*;
import ml.kalanblow.gestiondesinscriptions.request.EditAbsenceEleveParameters;

@Data
@EqualsAndHashCode(callSuper = false)
public class EditAbsenceEleveFormData  {

    private long version;

    private Horaire horaireClasse;

    private String motif;

    private boolean estJustifiee;

    private Cours cours;

    private Presence presence;

    private Eleve eleve;

    public static EditAbsenceEleveFormData fromAbsenceEleve(Absence absenceEleve) {

        EditAbsenceEleveFormData editAbsenceEleveFormData = new EditAbsenceEleveFormData();
        editAbsenceEleveFormData.setPresence(absenceEleve.getPresence());
        editAbsenceEleveFormData.setEleve(absenceEleve.getEleve());
        editAbsenceEleveFormData.setMotif(absenceEleve.getMotif());
        editAbsenceEleveFormData.setCours(absenceEleve.getCours());
        editAbsenceEleveFormData.setEstJustifiee(absenceEleve.isEstJustifiee());
        editAbsenceEleveFormData.setVersion(absenceEleve.getVersion());

        return editAbsenceEleveFormData;
    }

    public EditAbsenceEleveParameters toAbsenceParameters() {

        EditAbsenceEleveParameters editEleveParameters = new EditAbsenceEleveParameters();

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
