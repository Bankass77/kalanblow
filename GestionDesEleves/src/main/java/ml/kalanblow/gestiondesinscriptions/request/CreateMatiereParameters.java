package ml.kalanblow.gestiondesinscriptions.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
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
