package ml.kalanblow.gestiondesinscriptions.request;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.Presence;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
public class EditPresenceParameters extends CreatePresenceParameters {
    private  long version;
    public void upDateAppelDePresenceEleve(Presence presence) {

        presence.setAbsences(getAbsences());
        presence.setEleve(getEleve());
        presence.setCours(getCours());
    }
}
