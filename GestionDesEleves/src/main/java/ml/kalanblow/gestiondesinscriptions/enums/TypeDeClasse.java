package ml.kalanblow.gestiondesinscriptions.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum TypeDeClasse {
    SALLEDECOURS ("Salle de cours"), LABORATOIRE("Laboratoire");

    private String value;
}
