package ml.kalanblow.gestiondesinscriptions.response;


import lombok.Data;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.PeriodeDeVacances;
import ml.kalanblow.gestiondesinscriptions.request.EditPeriodeDeVacancesParameters;

@NoArgsConstructor
@Data
public class EditPeriodeDeVacancesFormData extends CreatePeriodeDeVacancesFormData {
    public static EditPeriodeDeVacancesFormData fromPeriodeDeVacances(PeriodeDeVacances periodeDeVacances) {


        EditPeriodeDeVacancesFormData editPeriodeDeVacancesFormData = new EditPeriodeDeVacancesFormData();

        editPeriodeDeVacancesFormData.setDateDebut(periodeDeVacances.getDateDebut());
        editPeriodeDeVacancesFormData.setDateFin(periodeDeVacances.getDateFin());
        return editPeriodeDeVacancesFormData;
    }


    public EditPeriodeDeVacancesParameters toParameters() {

        EditPeriodeDeVacancesParameters editPeriodeDeVacancesParameters = new EditPeriodeDeVacancesParameters();
        editPeriodeDeVacancesParameters.setDateDebut(getDateDebut());
        editPeriodeDeVacancesParameters.setDateFin(getDateFin());

        return editPeriodeDeVacancesParameters;
    }
}
