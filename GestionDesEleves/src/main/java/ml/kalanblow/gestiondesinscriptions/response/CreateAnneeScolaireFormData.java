package ml.kalanblow.gestiondesinscriptions.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.enums.TypeDeVacances;
import ml.kalanblow.gestiondesinscriptions.request.CreateAbsenceEleveParameters;
import ml.kalanblow.gestiondesinscriptions.request.CreateAnneScolaireParameters;

import java.time.Duration;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAnneeScolaireFormData {

    @NotNull
    @NotBlank
    private int annee;
    @NotNull
    @NotBlank
    private Duration duree;
    @NotNull
    @NotBlank
    private TypeDeVacances typeDeVacances;


    public CreateAnneScolaireParameters  toAnneScolaireParameters() {

        CreateAnneScolaireParameters createAnneScolaireParameters = new CreateAnneScolaireParameters();

        createAnneScolaireParameters.setAnnee(getAnnee());
        createAnneScolaireParameters.setDuree(getDuree());
        createAnneScolaireParameters.setTypeDeVacances(getTypeDeVacances());


        return createAnneScolaireParameters;
    }
}
