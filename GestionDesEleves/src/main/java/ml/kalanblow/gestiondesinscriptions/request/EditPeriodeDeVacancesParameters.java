package ml.kalanblow.gestiondesinscriptions.request;


import lombok.Data;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.Vacances;

@NoArgsConstructor
@Data
public class EditPeriodeDeVacancesParameters extends CreatePeriodeDeVacancesParameters {
    private  long version;
    public void updatePeriodeDeVacances(Vacances periodeDeVacances){

        periodeDeVacances.setDateDebut(getDateDebut());
        periodeDeVacances.setDateFin(getDateFin());
    }
}
