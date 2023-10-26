package ml.kalanblow.gestiondesinscriptions.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.Absence;
import ml.kalanblow.gestiondesinscriptions.model.Parent;
import ml.kalanblow.gestiondesinscriptions.model.Etablissement;

import java.time.LocalDate;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public sealed class CreateEleveParameters extends CreateUserParameters permits EditEleveParameters {

    private LocalDate dateDeNaissance;

    private int age;

    private String studentIneNumber;

    private Parent pere;

    private Parent mere;

    private Etablissement etablissement;

    private List<Absence> absences;



}
