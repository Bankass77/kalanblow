package ml.kalanblow.gestiondesinscriptions.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.*;
import ml.kalanblow.gestiondesinscriptions.model.CoursDEnseignement;
import ml.kalanblow.gestiondesinscriptions.model.EtablissementScolaire;
import ml.kalanblow.gestiondesinscriptions.model.HoraireClasse;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public sealed class CreateEnseignantParameters  extends CreateUserParameters permits EditEnseignantParameters {


    @NotBlank
    @NotNull
    private String leMatricule;

    @NotBlank
    @NotNull
    private LocalDate dateDeNaissance;

    @NotBlank
    @NotNull
    private int age;

    private List<DayOfWeek> joursDisponibles;

    private LocalTime heureDebutDisponibilite;

    private LocalTime heureFinDisponibilite;

    @NotBlank
    @NotNull
    private EtablissementScolaire etablissementScolaire;


    private List<CoursDEnseignement> coursDEnseignements;

    private List<HoraireClasse> horaireClasses;

}
