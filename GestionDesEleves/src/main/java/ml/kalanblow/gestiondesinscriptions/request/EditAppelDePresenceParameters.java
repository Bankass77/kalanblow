package ml.kalanblow.gestiondesinscriptions.request;


import lombok.Data;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.AppelDePresence;

@NoArgsConstructor
@Data
public class EditAppelDePresenceParameters extends CreateAppelDePresenceParameters {

    public void upDateAppelDePresenceEleve(AppelDePresence appelDePresence) {

        appelDePresence.setAbsences(getAbsences());
        appelDePresence.setEleve(getEleve());
        appelDePresence.setCours(getCours());
    }
}
