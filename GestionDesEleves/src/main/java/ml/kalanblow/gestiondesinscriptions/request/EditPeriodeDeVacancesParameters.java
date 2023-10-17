package ml.kalanblow.gestiondesinscriptions.request;


import lombok.Data;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.PeriodeDeVacances;

@NoArgsConstructor
@Data
public class EditPeriodeDeVacancesParameters extends CreatePeriodeDeVacancesParameters {

    public void updatePeriodeDeVacances(PeriodeDeVacances periodeDeVacances){

        periodeDeVacances.setDateDebut(getDateDebut());
        periodeDeVacances.setDateFin(getDateFin());
    }
}
