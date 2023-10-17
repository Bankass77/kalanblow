package ml.kalanblow.gestiondesinscriptions.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ml.kalanblow.gestiondesinscriptions.enums.TypeDeClasse;
import ml.kalanblow.gestiondesinscriptions.model.CoursDEnseignement;
import ml.kalanblow.gestiondesinscriptions.model.EtablissementScolaire;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class CreateSalleDeClasseParameters {

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
    private EtablissementScolaire etablissementScolaire;


    private Set<CoursDEnseignement> coursPlanifies;
}
