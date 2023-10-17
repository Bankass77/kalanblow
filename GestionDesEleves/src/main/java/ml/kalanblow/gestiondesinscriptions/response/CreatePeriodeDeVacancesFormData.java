package ml.kalanblow.gestiondesinscriptions.response;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.request.CreatePeriodeDeVacancesParameters;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePeriodeDeVacancesFormData {


    @NotNull
    @NotBlank
    private LocalDate dateDebut;


    @NotNull
    @NotBlank
    private LocalDate dateFin;

    public CreatePeriodeDeVacancesParameters toPeriodeDeVacancesParameters() {

        CreatePeriodeDeVacancesParameters createPeriodeDeVacancesParameters = new CreatePeriodeDeVacancesParameters();
        createPeriodeDeVacancesParameters.setDateDebut(getDateDebut());
        createPeriodeDeVacancesParameters.setDateFin(getDateFin());
        return createPeriodeDeVacancesParameters;
    }
}
