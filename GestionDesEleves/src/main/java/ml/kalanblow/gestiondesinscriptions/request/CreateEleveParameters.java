package ml.kalanblow.gestiondesinscriptions.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.Absence;
import ml.kalanblow.gestiondesinscriptions.model.Etablissement;
import ml.kalanblow.gestiondesinscriptions.model.Parent;

import java.time.LocalDate;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public sealed class CreateEleveParameters extends CreateUserParameters permits EditEleveParameters {

    @NotBlank
     @NotNull(message = "{notnull.message}")
    private LocalDate dateDeNaissance;

    @NotBlank
     @NotNull(message = "{notnull.message}")
    private int age;

    @NotBlank
     @NotNull(message = "{notnull.message}")
    private String studentIneNumber;

    @NotBlank
     @NotNull(message = "{notnull.message}")
    private Parent pere;

    @NotBlank
     @NotNull(message = "{notnull.message}")
    private Parent mere;

    @NotBlank
     @NotNull(message = "{notnull.message}")
    private Etablissement etablissement;

    private List<Absence> absences;



}
