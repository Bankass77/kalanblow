package ml.kalanblow.gestiondesinscriptions.request;

import lombok.*;
import ml.kalanblow.gestiondesinscriptions.model.CoursDEnseignement;

import java.util.Set;


@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateMatiereParameters {
    private double note;
    private double coefficient;
    private double moyenne;
    private String nomMatiere;

    private String description;
    private Set<CoursDEnseignement> coursDEnseignements;
}
