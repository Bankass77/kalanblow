package ml.kalanblow.gestiondesinscriptions.request;


import lombok.Data;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.Vacances;

@NoArgsConstructor
@Data
public class EditVacancesParameters extends CreateVacancesParameters {
    private  long version;
    public void updatePeriodeDeVacances(Vacances periodeDeVacances){

        periodeDeVacances.setDateDebut(getDateDebut());
        periodeDeVacances.setDateFin(getDateFin());
    }
}
