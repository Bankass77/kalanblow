package ml.kalanblow.gestiondesinscriptions.request;

import lombok.*;
import ml.kalanblow.gestiondesinscriptions.enums.TypeDeVacances;

import java.time.Duration;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateAnneScolaireParameters {
    private int annee;
    private Duration duree;
    private TypeDeVacances typeDeVacances;
}
