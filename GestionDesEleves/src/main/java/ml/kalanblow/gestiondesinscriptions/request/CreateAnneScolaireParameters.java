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
public class CreateAnneScolaireParameters {

    @NotNull
    @NotBlank
    private int annee;

    @NotNull
    @NotBlank
    private Duration duree;

    @NotNull
    @NotBlank
    private TypeDeVacances typeDeVacances;
}
