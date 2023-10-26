package ml.kalanblow.gestiondesinscriptions.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.enums.TypeDeClasse;
import ml.kalanblow.gestiondesinscriptions.model.Cours;
import ml.kalanblow.gestiondesinscriptions.model.Etablissement;
import ml.kalanblow.gestiondesinscriptions.request.CreateSalleParameters;
import ml.kalanblow.gestiondesinscriptions.validation.ValidationGroupOne;

import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateSalleFormData {

    @NotBlank
    @NotNull(groups = ValidationGroupOne.class)
    private String nomDeLaSalle;

    @NotNull(groups = ValidationGroupOne.class)
    @NotBlank
    private int nombreDePlace;

    @NotNull(groups = ValidationGroupOne.class)
    @NotBlank
    private TypeDeClasse typeDeClasse;


    @NotBlank
    @NotNull(groups = ValidationGroupOne.class)
    private Etablissement etablissement;

    private Set<Cours> coursPlanifies;


    public CreateSalleParameters toSalleDeClasseParameters (){

        CreateSalleParameters createSalleDeClasseParameters = new CreateSalleParameters();
        createSalleDeClasseParameters.setNomDeLaSalle(getNomDeLaSalle());
        createSalleDeClasseParameters.setTypeDeClasse(getTypeDeClasse());
        createSalleDeClasseParameters.setCoursPlanifies(getCoursPlanifies());
        createSalleDeClasseParameters.setNombreDePlace(getNombreDePlace());
        createSalleDeClasseParameters.setEtablissement(getEtablissement());

        return  createSalleDeClasseParameters;
    }
}
