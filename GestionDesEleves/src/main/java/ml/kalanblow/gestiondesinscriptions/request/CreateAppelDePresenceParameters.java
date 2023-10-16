package ml.kalanblow.gestiondesinscriptions.request;

import lombok.*;
import ml.kalanblow.gestiondesinscriptions.model.AbsenceEleve;
import ml.kalanblow.gestiondesinscriptions.model.CoursDEnseignement;
import ml.kalanblow.gestiondesinscriptions.model.Eleve;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateAppelDePresenceParameters {
    private CoursDEnseignement cours;
    private Eleve eleve;

    private List<AbsenceEleve> absences;
}
