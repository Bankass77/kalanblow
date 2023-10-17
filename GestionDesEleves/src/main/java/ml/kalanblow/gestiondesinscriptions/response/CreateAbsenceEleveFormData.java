package ml.kalanblow.gestiondesinscriptions.response;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.AppelDePresence;
import ml.kalanblow.gestiondesinscriptions.model.CoursDEnseignement;
import ml.kalanblow.gestiondesinscriptions.model.Eleve;
import ml.kalanblow.gestiondesinscriptions.model.HoraireClasse;
import ml.kalanblow.gestiondesinscriptions.request.CreateAbsenceEleveParameters;
import ml.kalanblow.gestiondesinscriptions.request.CreateAnneScolaireParameters;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAbsenceEleveFormData {

    @NotNull
    @NotBlank
    private HoraireClasse horaireClasse;
    @NotNull
    @NotBlank
    private String motif;
    @NotNull
    @NotBlank
    private boolean estJustifiee;
    @NotNull
    @NotBlank
    private CoursDEnseignement cours;
    @NotNull
    @NotBlank
    private AppelDePresence appelDePresence;
    @NotNull
    @NotBlank
    private Eleve eleve;


    public CreateAbsenceEleveParameters toAbsenceEleveParameters() {

        CreateAbsenceEleveParameters createAbsenceEleveParameters = new CreateAbsenceEleveParameters();

        createAbsenceEleveParameters.setMotif(getMotif());
        createAbsenceEleveParameters.setEleve(getEleve());
        createAbsenceEleveParameters.setCours(getCours());
        createAbsenceEleveParameters.setHoraireClasse(getHoraireClasse());
        createAbsenceEleveParameters.setAppelDePresence(getAppelDePresence());
        createAbsenceEleveParameters.setEstJustifiee(isEstJustifiee());

        return createAbsenceEleveParameters;
    }
}
