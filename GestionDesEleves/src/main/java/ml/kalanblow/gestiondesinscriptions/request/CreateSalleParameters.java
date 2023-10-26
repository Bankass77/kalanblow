package ml.kalanblow.gestiondesinscriptions.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ml.kalanblow.gestiondesinscriptions.enums.TypeDeClasse;
import ml.kalanblow.gestiondesinscriptions.model.Cours;
import ml.kalanblow.gestiondesinscriptions.model.Etablissement;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class CreateSalleParameters {

    @NotBlank
    @NotNull
    private String nomDeLaSalle;

    @NotBlank
    @NotNull
    private int nombreDePlace;

    @NotBlank
    @NotNull
    private TypeDeClasse typeDeClasse;

    @NotBlank
    @NotNull
    private Etablissement etablissement;


    private Set<Cours> coursPlanifies;
}
