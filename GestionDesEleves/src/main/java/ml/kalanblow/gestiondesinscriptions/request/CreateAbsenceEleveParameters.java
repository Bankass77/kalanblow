package ml.kalanblow.gestiondesinscriptions.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.AppelDePresence;
import ml.kalanblow.gestiondesinscriptions.model.CoursDEnseignement;
import ml.kalanblow.gestiondesinscriptions.model.Eleve;
import ml.kalanblow.gestiondesinscriptions.model.HoraireClasse;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAbsenceEleveParameters {

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
}
