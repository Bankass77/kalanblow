package ml.kalanblow.gestiondesinscriptions.response;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.Presence;
import ml.kalanblow.gestiondesinscriptions.model.Cours;
import ml.kalanblow.gestiondesinscriptions.model.Eleve;
import ml.kalanblow.gestiondesinscriptions.model.Horaire;
import ml.kalanblow.gestiondesinscriptions.request.CreateAbsenceEleveParameters;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAbsenceEleveFormData {

    @NotNull
    @NotBlank
    private Horaire horaireClasse;
    @NotNull
    @NotBlank
    private String motif;
    @NotNull
    @NotBlank
    private boolean estJustifiee;
    @NotNull
    @NotBlank
    private Cours cours;
    @NotNull
    @NotBlank
    private Presence presence;
    @NotNull
    @NotBlank
    private Eleve eleve;


    public CreateAbsenceEleveParameters toAbsenceEleveParameters() {

        CreateAbsenceEleveParameters createAbsenceEleveParameters = new CreateAbsenceEleveParameters();

        createAbsenceEleveParameters.setMotif(getMotif());
        createAbsenceEleveParameters.setEleve(getEleve());
        createAbsenceEleveParameters.setCours(getCours());
        createAbsenceEleveParameters.setHoraireClasse(getHoraireClasse());
        createAbsenceEleveParameters.setPresence(getPresence());
        createAbsenceEleveParameters.setEstJustifiee(isEstJustifiee());

        return createAbsenceEleveParameters;
    }
}
