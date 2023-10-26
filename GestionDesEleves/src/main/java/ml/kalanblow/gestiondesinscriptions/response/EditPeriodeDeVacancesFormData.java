package ml.kalanblow.gestiondesinscriptions.response;


import lombok.Data;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.Vacances;
import ml.kalanblow.gestiondesinscriptions.request.EditPeriodeDeVacancesParameters;

import java.time.LocalDate;

@NoArgsConstructor
@Data
public class EditPeriodeDeVacancesFormData  {

    private Long id;
    private long version;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    public static EditPeriodeDeVacancesFormData fromPeriodeDeVacances(Vacances periodeDeVacances) {


        EditPeriodeDeVacancesFormData editPeriodeDeVacancesFormData = new EditPeriodeDeVacancesFormData();

        editPeriodeDeVacancesFormData.setDateDebut(periodeDeVacances.getDateDebut());
        editPeriodeDeVacancesFormData.setDateFin(periodeDeVacances.getDateFin());
        editPeriodeDeVacancesFormData.setId(periodeDeVacances.getId());
        editPeriodeDeVacancesFormData.setVersion(periodeDeVacances.getVersion());
        return editPeriodeDeVacancesFormData;
    }


    public EditPeriodeDeVacancesParameters toParameters() {

        EditPeriodeDeVacancesParameters editPeriodeDeVacancesParameters = new EditPeriodeDeVacancesParameters();
        editPeriodeDeVacancesParameters.setDateDebut(getDateDebut());
        editPeriodeDeVacancesParameters.setDateFin(getDateFin());
        editPeriodeDeVacancesParameters.setVersion(version);

        return editPeriodeDeVacancesParameters;
    }
}
