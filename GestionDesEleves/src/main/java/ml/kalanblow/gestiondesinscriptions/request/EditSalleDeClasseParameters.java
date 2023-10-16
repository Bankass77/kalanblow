package ml.kalanblow.gestiondesinscriptions.request;

import ml.kalanblow.gestiondesinscriptions.model.SalleDeClasse;

public class EditSalleDeClasseParameters extends  CreateSalleDeClasseParameters{

    public  void updateSalleDeClasse (SalleDeClasse salleDeClasse){

        salleDeClasse.setTypeDeClasse(getTypeDeClasse());
        salleDeClasse.setNomDeLaSalle(getNomDeLaSalle());
        salleDeClasse.setCoursPlanifies(getCoursPlanifies());
        salleDeClasse.setNombreDePlace(getNombreDePlace());
        salleDeClasse.setEtablissementScolaire(getEtablissementScolaire());

    }
}
