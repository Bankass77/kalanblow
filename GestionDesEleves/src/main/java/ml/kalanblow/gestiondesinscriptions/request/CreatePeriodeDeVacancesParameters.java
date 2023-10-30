package ml.kalanblow.gestiondesinscriptions.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class CreatePeriodeDeVacancesParameters {

    @NotNull
    @NotBlank
    private LocalDate dateDebut;


    @NotNull
    @NotBlank
    private LocalDate dateFin;
}
