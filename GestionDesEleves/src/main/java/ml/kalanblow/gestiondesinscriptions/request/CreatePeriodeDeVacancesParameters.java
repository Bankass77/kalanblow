package ml.kalanblow.gestiondesinscriptions.request;

import lombok.*;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreatePeriodeDeVacancesParameters {

    private LocalDate dateDebut;

    private LocalDate dateFin;
}
