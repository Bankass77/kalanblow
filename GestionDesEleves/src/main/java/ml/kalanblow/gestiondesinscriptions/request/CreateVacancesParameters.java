package ml.kalanblow.gestiondesinscriptions.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class CreateVacancesParameters {

     @NotNull(message = "{notnull.message}")
    @NotBlank
    private LocalDate dateDebut;


     @NotNull(message = "{notnull.message}")
    @NotBlank
    private LocalDate dateFin;
}
