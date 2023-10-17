package ml.kalanblow.gestiondesinscriptions.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.CoursDEnseignement;
import ml.kalanblow.gestiondesinscriptions.request.CreateMatiereParameters;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMatiereFormData {

    private double note;

    @NotBlank
    @NotNull
    private double coefficient;

    private double moyenne;

    @NotBlank
    @NotNull
    private String nomMatiere;

    @NotBlank
    @NotNull
    private String description;

    private Set<CoursDEnseignement> coursDEnseignements;

    public CreateMatiereParameters toMatiereParameters() {

        CreateMatiereParameters createMatiereParameters = new CreateMatiereParameters();
        createMatiereParameters.setNomMatiere(getNomMatiere());
        createMatiereParameters.setNote(getNote());
        createMatiereParameters.setMoyenne(getMoyenne());
        createMatiereParameters.setDescription(getDescription());
        createMatiereParameters.setCoursDEnseignements(getCoursDEnseignements());
        createMatiereParameters.setCoefficient(getCoefficient());

        return createMatiereParameters;

    }
}
