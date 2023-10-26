package ml.kalanblow.gestiondesinscriptions.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.Absence;
import ml.kalanblow.gestiondesinscriptions.model.Cours;
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
    private Cours cours;

    @NotNull
    @NotBlank
    private Eleve eleve;

    private List<Absence> absences;


    public CreateAppelDePresenceParameters toAppelDePresenceParameters() {

        CreateAppelDePresenceParameters createAppelDePresenceParameters = new CreateAppelDePresenceParameters();

        createAppelDePresenceParameters.setAbsences(getAbsences());
        createAppelDePresenceParameters.setEleve(getEleve());
        createAppelDePresenceParameters.setCours(getCours());

        return createAppelDePresenceParameters;
    }
}
