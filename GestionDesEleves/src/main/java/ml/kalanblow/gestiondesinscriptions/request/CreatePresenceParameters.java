package ml.kalanblow.gestiondesinscriptions.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ml.kalanblow.gestiondesinscriptions.model.Absence;
import ml.kalanblow.gestiondesinscriptions.model.Cours;
import ml.kalanblow.gestiondesinscriptions.model.Eleve;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class CreatePresenceParameters {

    @NotNull
    @NotBlank
    private Cours cours;

    @NotNull
    @NotBlank
    private Eleve eleve;

    private List<Absence> absences;
}
