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

    @NotBlank
     @NotNull(message = "{notnull.message}")
    private double note;

    @NotBlank
     @NotNull(message = "{notnull.message}")
    private double coefficient;

    private double moyenne;

    @NotBlank
     @NotNull(message = "{notnull.message}")
    private String nomMatiere;

    @NotBlank
     @NotNull(message = "{notnull.message}")
    private String description;

    private Set<Cours> coursDEnseignements;
}
