package ml.kalanblow.gestiondesinscriptions.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.Cours;

import java.util.Set;


@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class CreateMatiereParameters {

    private double note;

    private double coefficient;

    private double moyenne;

    private String nomMatiere;

    private String description;

    private Set<Cours> coursDEnseignements;
}
