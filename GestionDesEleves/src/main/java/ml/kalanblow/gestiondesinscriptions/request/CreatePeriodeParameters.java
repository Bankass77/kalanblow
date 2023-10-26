package ml.kalanblow.gestiondesinscriptions.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.enums.TypeDeVacances;

import java.time.Duration;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class CreatePeriodeParameters {

    private int annee;

    private Duration duree;

    private TypeDeVacances typeDeVacances;
}
