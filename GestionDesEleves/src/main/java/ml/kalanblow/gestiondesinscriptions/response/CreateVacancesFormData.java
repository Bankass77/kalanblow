package ml.kalanblow.gestiondesinscriptions.response;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.request.CreateVacancesParameters;
import ml.kalanblow.gestiondesinscriptions.validation.ValidationGroupOne;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateVacancesFormData {

     @NotNull(message = "{notnull.message}", groups = ValidationGroupOne.class)
    @NotBlank
    private LocalDate dateDebut;


     @NotNull(message = "{notnull.message}", groups = ValidationGroupOne.class)
    @NotBlank
    private LocalDate dateFin;

    public CreateVacancesParameters toPeriodeDeVacancesParameters() {

        CreateVacancesParameters createPeriodeDeVacancesParameters = new CreateVacancesParameters();
        createPeriodeDeVacancesParameters.setDateDebut(getDateDebut());
        createPeriodeDeVacancesParameters.setDateFin(getDateFin());
        return createPeriodeDeVacancesParameters;
    }
}
