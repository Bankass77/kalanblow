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

    private String nomDuCours;

    private String niveau;

    private Periode anneeScolaire;

    private Matiere matiere;

    private Set<Absence> absenceEleves;

    private Set<Horaire> horaireClasses;

    private Enseignant enseignant;

    private Salle salleDeClasse;
}
