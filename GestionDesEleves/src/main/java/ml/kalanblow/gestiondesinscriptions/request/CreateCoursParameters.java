package ml.kalanblow.gestiondesinscriptions.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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
