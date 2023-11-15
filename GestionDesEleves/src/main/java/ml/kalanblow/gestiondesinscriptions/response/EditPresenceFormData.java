package ml.kalanblow.gestiondesinscriptions.response;


import lombok.Data;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.Absence;
import ml.kalanblow.gestiondesinscriptions.model.Presence;
import ml.kalanblow.gestiondesinscriptions.model.Cours;
import ml.kalanblow.gestiondesinscriptions.model.Eleve;
import ml.kalanblow.gestiondesinscriptions.request.EditPresenceParameters;

import java.util.List;

@Data
@NoArgsConstructor
public class EditPresenceFormData  extends CreatePresenceFormData {
    
    private long version;
    private Cours cours;
    private Eleve eleve;
    private List<Absence> absences;

    public static EditPresenceFormData fromAppleDePresenceFormData(Presence presence) {

        EditPresenceFormData editAppleDePresenceFormData = new EditPresenceFormData();

        editAppleDePresenceFormData.setAbsences(presence.getAbsences());
        editAppleDePresenceFormData.setEleve(presence.getEleve());
        editAppleDePresenceFormData.setCours(presence.getCours());
        editAppleDePresenceFormData.setVersion(presence.getVersion());

        return editAppleDePresenceFormData;
    }


    public EditPresenceParameters toAppelDePresenceParameters() {

        EditPresenceParameters editAppelDePresenceParameters = new EditPresenceParameters();
        editAppelDePresenceParameters.setAbsences(getAbsences());
        editAppelDePresenceParameters.setCours(getCours());
        editAppelDePresenceParameters.setEleve(getEleve());
        editAppelDePresenceParameters.setVersion(version);

        return editAppelDePresenceParameters;
    }
}
