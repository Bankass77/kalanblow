package ml.kalanblow.gestiondesinscriptions.request;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.Vacances;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class EditVacancesParameters extends CreateVacancesParameters {
    private  long version;
    public void updatePeriodeDeVacances(Vacances periodeDeVacances){

        periodeDeVacances.setDateDebut(getDateDebut());
        periodeDeVacances.setDateFin(getDateFin());
    }
}
