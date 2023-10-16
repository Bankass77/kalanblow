package ml.kalanblow.gestiondesinscriptions.request;

import ml.kalanblow.gestiondesinscriptions.model.PeriodeDeVacances;

public class EditPeriodeDeVacancesParameters extends CreatePeriodeDeVacancesParameters {

    public void updatePeriodeDeVacances(PeriodeDeVacances periodeDeVacances){

        periodeDeVacances.setDateDebut(getDateDebut());
        periodeDeVacances.setDateFin(getDateFin());
    }
}
