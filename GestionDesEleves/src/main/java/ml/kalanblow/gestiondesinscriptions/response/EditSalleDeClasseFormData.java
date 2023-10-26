package ml.kalanblow.gestiondesinscriptions.response;


import lombok.Data;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.enums.TypeDeClasse;
import ml.kalanblow.gestiondesinscriptions.model.Cours;
import ml.kalanblow.gestiondesinscriptions.model.Etablissement;
import ml.kalanblow.gestiondesinscriptions.model.Salle;
import ml.kalanblow.gestiondesinscriptions.request.EditSalleDeClasseParameters;

import java.util.Set;

@NoArgsConstructor
@Data
public class EditSalleDeClasseFormData {

    private Long id;
    private long version;
    private String nomDeLaSalle;
    private int nombreDePlace;
    private TypeDeClasse typeDeClasse;
    private Etablissement etablissement;
    private Set<Cours> coursPlanifies;

    public static EditSalleDeClasseFormData fromSalleDeClasse(Salle salleDeClasse) {

        EditSalleDeClasseFormData editSalleDeClasseFormData = new EditSalleDeClasseFormData();
        editSalleDeClasseFormData.setId(salleDeClasse.getId());
        editSalleDeClasseFormData.setNomDeLaSalle(salleDeClasse.getNomDeLaSalle());
        editSalleDeClasseFormData.setTypeDeClasse(salleDeClasse.getTypeDeClasse());
        editSalleDeClasseFormData.setEtablissement(salleDeClasse.getEtablissement());
        editSalleDeClasseFormData.setCoursPlanifies(salleDeClasse.getCoursPlanifies());
        editSalleDeClasseFormData.setVersion(salleDeClasse.getVersion());
        editSalleDeClasseFormData.setNombreDePlace(salleDeClasse.getNombreDePlace());

        return editSalleDeClasseFormData;
    }

    public EditSalleDeClasseParameters toParameters() {

        EditSalleDeClasseParameters editSalleDeClasseParameters= new EditSalleDeClasseParameters();
        editSalleDeClasseParameters.setVersion(version);
        editSalleDeClasseParameters.setTypeDeClasse(getTypeDeClasse());
        editSalleDeClasseParameters.setNomDeLaSalle(getNomDeLaSalle());
        editSalleDeClasseParameters.setCoursPlanifies(getCoursPlanifies());
        editSalleDeClasseParameters.setEtablissement(getEtablissement());
        editSalleDeClasseParameters.setNombreDePlace(getNombreDePlace());
        return new EditSalleDeClasseParameters();
    }
}
