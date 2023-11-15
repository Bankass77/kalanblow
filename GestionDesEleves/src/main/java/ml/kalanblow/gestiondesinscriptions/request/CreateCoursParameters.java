package ml.kalanblow.gestiondesinscriptions.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ml.kalanblow.gestiondesinscriptions.model.*;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class CreateCoursParameters {

     @NotNull(message = "{notnull.message}")
    @NotBlank
    private String nomDuCours;

     @NotNull(message = "{notnull.message}")
    @NotBlank
    private String niveau;

     @NotNull(message = "{notnull.message}")
    @NotBlank
    private Periode anneeScolaire;

     @NotNull(message = "{notnull.message}")
    @NotBlank
    private Matiere matiere;


    private Set<Absence> absenceEleves;

     @NotNull(message = "{notnull.message}")
    @NotBlank
    private Set<Horaire> horaireClasses;

     @NotNull(message = "{notnull.message}")
    @NotBlank
    private Enseignant enseignant;

     @NotNull(message = "{notnull.message}")
    @NotBlank
    private Salle salleDeClasse;
}
