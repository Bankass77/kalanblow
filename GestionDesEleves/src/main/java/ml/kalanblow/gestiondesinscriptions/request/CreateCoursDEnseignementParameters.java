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
public class CreateCoursDEnseignementParameters {

    @NotNull
    @NotBlank
    private String nomDuCours;

    @NotNull
    @NotBlank
    private String niveau;

    @NotNull
    @NotBlank
    private AnneeScolaire anneeScolaire;

    @NotNull
    @NotBlank
    private Matiere matiere;


    private Set<AbsenceEleve> absenceEleves;

    @NotNull
    @NotBlank
    private Set<HoraireClasse> horaireClasses;

    @NotNull
    @NotBlank
    private Enseignant enseignant;

    @NotNull
    @NotBlank
    private SalleDeClasse salleDeClasse;
}
