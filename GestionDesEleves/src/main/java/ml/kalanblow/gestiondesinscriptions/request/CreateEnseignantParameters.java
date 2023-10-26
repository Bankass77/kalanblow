package ml.kalanblow.gestiondesinscriptions.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ml.kalanblow.gestiondesinscriptions.model.Cours;
import ml.kalanblow.gestiondesinscriptions.model.Etablissement;
import ml.kalanblow.gestiondesinscriptions.model.Horaire;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public sealed class CreateEnseignantParameters  extends CreateUserParameters permits EditEnseignantParameters {

     
    private String leMatricule;

    private LocalDate dateDeNaissance;

    private int age;

    private List<DayOfWeek> joursDisponibles;

    private LocalTime heureDebutDisponibilite;

    private LocalTime heureFinDisponibilite;

    private Etablissement etablissement;

    private List<Cours> coursDEnseignements;

    private List<Horaire> horaireClasses;

}
