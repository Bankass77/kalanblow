package ml.kalanblow.gestiondesinscriptions.response;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.Vacances;
import ml.kalanblow.gestiondesinscriptions.request.EditVacancesParameters;

import java.time.LocalDate;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class EditVacancesFormData extends CreateVacancesFormData{

    private Long id;
    private long version;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    public static EditVacancesFormData fromPeriodeDeVacances(Vacances periodeDeVacances) {


        EditVacancesFormData editPeriodeDeVacancesFormData = new EditVacancesFormData();

        editPeriodeDeVacancesFormData.setDateDebut(periodeDeVacances.getDateDebut());
        editPeriodeDeVacancesFormData.setDateFin(periodeDeVacances.getDateFin());
        editPeriodeDeVacancesFormData.setId(periodeDeVacances.getId());
        editPeriodeDeVacancesFormData.setVersion(periodeDeVacances.getVersion());
        return editPeriodeDeVacancesFormData;
    }


    public EditVacancesParameters toParameters() {

        EditVacancesParameters editPeriodeDeVacancesParameters = new EditVacancesParameters();
        editPeriodeDeVacancesParameters.setDateDebut(getDateDebut());
        editPeriodeDeVacancesParameters.setDateFin(getDateFin());
        editPeriodeDeVacancesParameters.setVersion(version);

        return editPeriodeDeVacancesParameters;
    }
}
