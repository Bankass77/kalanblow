package ml.kalanblow.gestiondesinscriptions.response;


import lombok.Data;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.AppelDePresence;
import ml.kalanblow.gestiondesinscriptions.request.EditAppelDePresenceParameters;

@Data
@NoArgsConstructor
public class EditAppleDePresenceFormData extends CreateAppleDePresenceFormData {

    public static EditAppleDePresenceFormData fromAppleDePresenceFormData(AppelDePresence appelDePresence) {

        EditAppleDePresenceFormData editAppleDePresenceFormData = new EditAppleDePresenceFormData();

        editAppleDePresenceFormData.setAbsences(appelDePresence.getAbsences());
        editAppleDePresenceFormData.setEleve(appelDePresence.getEleve());
        editAppleDePresenceFormData.setCours(appelDePresence.getCours());

        return editAppleDePresenceFormData;
    }


    public EditAppelDePresenceParameters toAppelDePresenceParameters() {

        EditAppelDePresenceParameters editAppelDePresenceParameters = new EditAppelDePresenceParameters();
        editAppelDePresenceParameters.setAbsences(getAbsences());
        editAppelDePresenceParameters.setCours(getCours());
        editAppelDePresenceParameters.setEleve(getEleve());

        return editAppelDePresenceParameters;
    }
}
