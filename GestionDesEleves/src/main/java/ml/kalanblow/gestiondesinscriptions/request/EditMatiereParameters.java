package ml.kalanblow.gestiondesinscriptions.request;

import ml.kalanblow.gestiondesinscriptions.model.Matiere;

public class EditMatiereParameters extends  CreateMatiereParameters{


    public void updateMatiere(Matiere matiere){

        matiere.setNomMatiere(getNomMatiere());
        matiere.setCoefficient(getCoefficient());
        matiere.setMoyenne(getMoyenne());
        matiere.setNote(getNote());
        matiere.setDescription(getDescription());
        matiere.setCoursDEnseignements(getCoursDEnseignements());

    }
}
