package ml.kalanblow.gestiondesinscriptions.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.Salle;

@NoArgsConstructor
@Data
public class EditSalleDeClasseParameters extends  CreateSalleDeClasseParameters{
    private  long version;
    public  void updateSalleDeClasse (Salle salleDeClasse){

        salleDeClasse.setTypeDeClasse(getTypeDeClasse());
        salleDeClasse.setNomDeLaSalle(getNomDeLaSalle());
        salleDeClasse.setCoursPlanifies(getCoursPlanifies());
        salleDeClasse.setNombreDePlace(getNombreDePlace());
        salleDeClasse.setEtablissement(getEtablissement());

    }
}
