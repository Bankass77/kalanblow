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

     @NotNull(message = "{notnull.message}")
    private Horaire horaireClasse;

     @NotNull(message = "{notnull.message}")
    @NotBlank
    private String motif;

     @NotNull(message = "{notnull.message}")
    @NotBlank
    private boolean estJustifiee;

     @NotNull(message = "{notnull.message}")
    @NotBlank
    private Cours cours;

     @NotNull(message = "{notnull.message}")
    @NotBlank
    private Presence presence;

     @NotNull(message = "{notnull.message}")
    @NotBlank
    private Eleve eleve;
}
