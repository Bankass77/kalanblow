package ml.kalanblow.gestiondesinscriptions.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum TypeDeVacances {

    ETE("Eté", Duration.ofDays(90)), PAQUES("Pâques", Duration.ofDays(14)), NOEL("Noël", Duration.ofDays(14));

    private String value;

    private Duration duree;

}
