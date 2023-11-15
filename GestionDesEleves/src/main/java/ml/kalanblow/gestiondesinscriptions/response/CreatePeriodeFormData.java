package ml.kalanblow.gestiondesinscriptions.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.enums.TypeDeVacances;
import ml.kalanblow.gestiondesinscriptions.request.CreatePeriodeParameters;
import ml.kalanblow.gestiondesinscriptions.validation.ValidationGroupOne;

import java.time.Duration;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePeriodeFormData {


    @NotBlank
     @NotNull(message = "{notnull.message}", groups = ValidationGroupOne.class)
    private int annee;


    @NotBlank
     @NotNull(message = "{notnull.message}", groups = ValidationGroupOne.class)
    private Duration duree;


    @NotBlank
     @NotNull(message = "{notnull.message}", groups = ValidationGroupOne.class)
    private TypeDeVacances typeDeVacances;


    public CreatePeriodeParameters toAnneScolaireParameters() {

        CreatePeriodeParameters createAnneScolaireParameters = new CreatePeriodeParameters();

        createAnneScolaireParameters.setAnnee(getAnnee());
        createAnneScolaireParameters.setDuree(getDuree());
        createAnneScolaireParameters.setTypeDeVacances(getTypeDeVacances());


        return createAnneScolaireParameters;
    }
}
