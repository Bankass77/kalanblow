package ml.kalanblow.gestiondesinscriptions.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.AbsenceEleve;
import ml.kalanblow.gestiondesinscriptions.model.CoursDEnseignement;
import ml.kalanblow.gestiondesinscriptions.model.Eleve;
import ml.kalanblow.gestiondesinscriptions.request.CreateAppelDePresenceParameters;

import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CreateAppleDePresenceFormData {

    @NotNull
    @NotBlank
    private CoursDEnseignement cours;

    @NotNull
    @NotBlank
    private Eleve eleve;

    private List<AbsenceEleve> absences;


    public CreateAppelDePresenceParameters toAppelDePresenceParameters() {

        CreateAppelDePresenceParameters createAppelDePresenceParameters = new CreateAppelDePresenceParameters();

        createAppelDePresenceParameters.setAbsences(getAbsences());
        createAppelDePresenceParameters.setEleve(getEleve());
        createAppelDePresenceParameters.setCours(getCours());

        return createAppelDePresenceParameters;
    }
}
