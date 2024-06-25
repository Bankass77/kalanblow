package ml.kalanblow.gestiondesinscriptions.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.Cours;
import ml.kalanblow.gestiondesinscriptions.model.Etablissement;
import ml.kalanblow.gestiondesinscriptions.model.Horaire;
import ml.kalanblow.gestiondesinscriptions.model.UserName;
import ml.kalanblow.gestiondesinscriptions.request.CreateEnseignantParameters;
import ml.kalanblow.gestiondesinscriptions.util.CalculateUserAge;
import ml.kalanblow.gestiondesinscriptions.validation.ValidationGroupOne;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public sealed class CreateEnseignantFormData extends  AbstractUserFormData permits EditEnseignantFormData {

    @NotBlank
    @NotNull(groups = ValidationGroupOne.class)
    private String leMatricule;

    @NotBlank
    @NotNull(groups = ValidationGroupOne.class)
    private LocalDate dateDeNaissance;

    @NotBlank

    @NotNull(groups = ValidationGroupOne.class)
    private Etablissement etablissement;

    @NotBlank
    @NotNull
    private int age;

    @NotBlank
    @NotNull
    private List<Cours> coursDEnseignements;

    @NotNull(groups = ValidationGroupOne.class)
    private LocalTime heureDebutDisponibilite;


    private List<DayOfWeek> joursDisponibles;

    private List<Horaire> horaireClasses;

    @NotNull(groups = ValidationGroupOne.class)
    private LocalTime heureFinDisponibilite;


    public CreateEnseignantParameters toEnseignantParameters (){

        CreateEnseignantParameters createEnseignantParameters= new CreateEnseignantParameters();

        createEnseignantParameters.setCoursDEnseignements(getCoursDEnseignements());

        createEnseignantParameters.setAge(CalculateUserAge.calculateAge(getDateDeNaissance()));

        createEnseignantParameters.setEtablissement(getEtablissement());
        createEnseignantParameters.setDateDeNaissance(getDateDeNaissance());
        createEnseignantParameters.setHeureDebutDisponibilite(getHeureDebutDisponibilite());
        createEnseignantParameters.setHeureFinDisponibilite(getHeureFinDisponibilite());

        return createEnseignantParameters;
    }
}
