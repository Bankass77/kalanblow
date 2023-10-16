package ml.kalanblow.gestiondesinscriptions.request;

import lombok.*;
import ml.kalanblow.gestiondesinscriptions.model.*;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateCoursDEnseignementParameters {
    private String nomDuCours;
    private String niveau;
    private AnneeScolaire anneeScolaire;
    private Matiere matiere;
    private Set<AbsenceEleve> absenceEleves;
    private Set<HoraireClasse> horaireClasses;
    private Enseignant enseignant;
    private SalleDeClasse salleDeClasse;
}
