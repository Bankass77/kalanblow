package ml.kalanblow.gestiondesinscriptions.response;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.Presence;
import ml.kalanblow.gestiondesinscriptions.model.Cours;
import ml.kalanblow.gestiondesinscriptions.model.Eleve;
import ml.kalanblow.gestiondesinscriptions.model.Horaire;
import ml.kalanblow.gestiondesinscriptions.request.CreateAbsenceParameters;
import ml.kalanblow.gestiondesinscriptions.validation.ValidationGroupOne;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAbsenceFormData {

    @NotNull
    @NotBlank
    private Horaire horaireClasse;

    @NotNull
    @NotBlank
    @NotBlank
    @Size(min = 1, max = 200, groups = ValidationGroupOne.class)
    private String motif;

    @NotNull
    @NotBlank
    @NotNull(groups = ValidationGroupOne.class)
    private boolean estJustifiee;

    @NotNull
    @NotBlank
    @NotNull(groups = ValidationGroupOne.class)
    private Cours cours;

    @NotNull
    @NotBlank
    @NotNull(groups = ValidationGroupOne.class)
    private Presence presence;

    @NotNull
    @NotBlank
    @NotNull(groups = ValidationGroupOne.class)
    private Eleve eleve;


    public CreateAbsenceParameters toAbsenceEleveParameters() {

        CreateAbsenceParameters createAbsenceEleveParameters = new CreateAbsenceParameters();

        createAbsenceEleveParameters.setMotif(getMotif());
        createAbsenceEleveParameters.setEleve(getEleve());
        createAbsenceEleveParameters.setCours(getCours());
        createAbsenceEleveParameters.setHoraireClasse(getHoraireClasse());
        createAbsenceEleveParameters.setPresence(getPresence());
        createAbsenceEleveParameters.setEstJustifiee(isEstJustifiee());

        return createAbsenceEleveParameters;
    }
}
