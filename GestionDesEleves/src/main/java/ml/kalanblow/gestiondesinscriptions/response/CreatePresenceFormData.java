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
import ml.kalanblow.gestiondesinscriptions.request.CreatePresenceParameters;
import ml.kalanblow.gestiondesinscriptions.validation.ValidationGroupOne;

import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CreatePresenceFormData {


    @NotBlank
    @NotNull(groups = ValidationGroupOne.class)
    private Cours cours;


    @NotBlank
    @NotNull(groups = ValidationGroupOne.class)
    private Eleve eleve;

    private List<Absence> absences;


    public CreatePresenceParameters toAppelDePresenceParameters() {

        CreatePresenceParameters createAppelDePresenceParameters = new CreatePresenceParameters();

        createAppelDePresenceParameters.setAbsences(getAbsences());
        createAppelDePresenceParameters.setEleve(getEleve());
        createAppelDePresenceParameters.setCours(getCours());

        return createAppelDePresenceParameters;
    }
}
