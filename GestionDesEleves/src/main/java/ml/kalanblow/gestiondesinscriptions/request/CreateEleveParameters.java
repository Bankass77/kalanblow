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
    @NotNull
    private LocalDate dateDeNaissance;

    @NotBlank
    @NotNull
    private int age;

    @NotBlank
    @NotNull
    private String studentIneNumber;

    @NotBlank
    @NotNull
    private Parent pere;

    @NotBlank
    @NotNull
    private Parent mere;

    @NotBlank
    @NotNull
    private Etablissement etablissement;

    private List<Absence> absences;



}
