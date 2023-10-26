package ml.kalanblow.gestiondesinscriptions.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.enums.TypeDeClasse;
import ml.kalanblow.gestiondesinscriptions.model.Cours;
import ml.kalanblow.gestiondesinscriptions.model.Etablissement;
import ml.kalanblow.gestiondesinscriptions.request.CreateSalleDeClasseParameters;

import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateSalleDeClasseFormData {
    @NotNull
    @NotBlank
    private String nomDeLaSalle;

    @NotNull
    @NotBlank
    private int nombreDePlace;

    @NotNull
    @NotBlank
    private TypeDeClasse typeDeClasse;

    @NotNull
    @NotBlank
    private Etablissement etablissement;

    private Set<Cours> coursPlanifies;


    public CreateSalleDeClasseParameters toSalleDeClasseParameters (){

        CreateSalleDeClasseParameters createSalleDeClasseParameters = new CreateSalleDeClasseParameters();
        createSalleDeClasseParameters.setNomDeLaSalle(getNomDeLaSalle());
        createSalleDeClasseParameters.setTypeDeClasse(getTypeDeClasse());
        createSalleDeClasseParameters.setCoursPlanifies(getCoursPlanifies());
        createSalleDeClasseParameters.setNombreDePlace(getNombreDePlace());
        createSalleDeClasseParameters.setEtablissement(getEtablissement());

        return  createSalleDeClasseParameters;
    }
}