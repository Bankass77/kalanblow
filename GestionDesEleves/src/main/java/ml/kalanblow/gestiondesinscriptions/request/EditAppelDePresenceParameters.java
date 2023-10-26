package ml.kalanblow.gestiondesinscriptions.request;


import lombok.Data;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.Presence;

@NoArgsConstructor
@Data
public class EditAppelDePresenceParameters extends CreateAppelDePresenceParameters {
    private  long version;
    public void upDateAppelDePresenceEleve(Presence presence) {

        presence.setAbsences(getAbsences());
        presence.setEleve(getEleve());
        presence.setCours(getCours());
    }
}
