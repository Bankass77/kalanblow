package ml.kalanblow.gestiondesinscriptions.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.SalleDeClasse;

@NoArgsConstructor
@Data
public class EditSalleDeClasseParameters extends  CreateSalleDeClasseParameters{

    public  void updateSalleDeClasse (SalleDeClasse salleDeClasse){

        salleDeClasse.setTypeDeClasse(getTypeDeClasse());
        salleDeClasse.setNomDeLaSalle(getNomDeLaSalle());
        salleDeClasse.setCoursPlanifies(getCoursPlanifies());
        salleDeClasse.setNombreDePlace(getNombreDePlace());
        salleDeClasse.setEtablissementScolaire(getEtablissementScolaire());

    }
}
