package ml.kalanblow.gestiondesinscriptions.request;

import ml.kalanblow.gestiondesinscriptions.model.AppelDePresence;

public class EditAppelDePresenceParameters extends CreateAppelDePresenceParameters {

    public void upDateAppelDePresenceEleve(AppelDePresence appelDePresence) {

        appelDePresence.setAbsences(getAbsences());
        appelDePresence.setEleve(getEleve());
        appelDePresence.setCours(getCours());
    }
}
