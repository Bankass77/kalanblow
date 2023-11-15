package ml.kalanblow.gestiondesinscriptions.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ml.kalanblow.gestiondesinscriptions.enums.TypeDeVacances;

import java.time.Duration;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class CreatePeriodeParameters {

     @NotNull(message = "{notnull.message}")
    @NotBlank
    private int annee;

     @NotNull(message = "{notnull.message}")
    @NotBlank
    private Duration duree;

     @NotNull(message = "{notnull.message}")
    @NotBlank
    private TypeDeVacances typeDeVacances;
}
