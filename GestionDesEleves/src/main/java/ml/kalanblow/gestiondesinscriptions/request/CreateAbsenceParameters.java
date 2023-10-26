package ml.kalanblow.gestiondesinscriptions.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.Presence;
import ml.kalanblow.gestiondesinscriptions.model.Cours;
import ml.kalanblow.gestiondesinscriptions.model.Eleve;
import ml.kalanblow.gestiondesinscriptions.model.Horaire;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAbsenceParameters {

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
}
