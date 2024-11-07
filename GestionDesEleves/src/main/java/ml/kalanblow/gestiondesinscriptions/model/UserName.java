package ml.kalanblow.gestiondesinscriptions.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import ml.kalanblow.gestiondesinscriptions.validation.ValidationGroupOne;

import java.io.Serializable;

@Data
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserName implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "{notnull.message}")
    @Size(min = 1, max = 200, groups = ValidationGroupOne.class)
    private String prenom;

    @NotNull(message = "{notnull.message}")
    @Size(min = 1, max = 200, groups = ValidationGroupOne.class)
    private String nomDeFamille;

    public String getFullName() {

        return String.format("%s %s", prenom, nomDeFamille);
    }
}
