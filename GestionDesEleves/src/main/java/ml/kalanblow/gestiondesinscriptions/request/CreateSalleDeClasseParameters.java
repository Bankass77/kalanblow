package ml.kalanblow.gestiondesinscriptions.request;

import lombok.*;
import ml.kalanblow.gestiondesinscriptions.enums.TypeDeClasse;
import ml.kalanblow.gestiondesinscriptions.model.CoursDEnseignement;
import ml.kalanblow.gestiondesinscriptions.model.EtablissementScolaire;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateSalleDeClasseParameters {

    private String nomDeLaSalle;
    private int nombreDePlace;
    private TypeDeClasse typeDeClasse;
    private EtablissementScolaire etablissementScolaire;
    private Set<CoursDEnseignement> coursPlanifies;
}
