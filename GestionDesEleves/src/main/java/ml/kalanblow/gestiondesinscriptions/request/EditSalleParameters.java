package ml.kalanblow.gestiondesinscriptions.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.Salle;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
public class EditSalleParameters extends CreateSalleParameters {
    private  long version;
    public  void updateSalleDeClasse (Salle salleDeClasse){

        salleDeClasse.setTypeDeClasse(getTypeDeClasse());
        salleDeClasse.setNomDeLaSalle(getNomDeLaSalle());
        salleDeClasse.setCoursPlanifies(getCoursPlanifies());
        salleDeClasse.setNombreDePlace(getNombreDePlace());
        salleDeClasse.setEtablissement(getEtablissement());

    }
}
