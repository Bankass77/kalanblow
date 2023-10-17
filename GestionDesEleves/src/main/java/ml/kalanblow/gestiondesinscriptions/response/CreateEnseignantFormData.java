package ml.kalanblow.gestiondesinscriptions.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.CoursDEnseignement;
import ml.kalanblow.gestiondesinscriptions.model.EtablissementScolaire;
import ml.kalanblow.gestiondesinscriptions.model.HoraireClasse;
import ml.kalanblow.gestiondesinscriptions.model.UserName;
import ml.kalanblow.gestiondesinscriptions.request.CreateEnseignantParameters;
import ml.kalanblow.gestiondesinscriptions.util.CalculateUserAge;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
public class CreateEnseignantFormData extends  CreateUserFormData{
    @NotBlank
    @NotNull
    private String leMatricule;
    @NotBlank
    @NotNull
    private LocalDate dateDeNaissance;
    @NotBlank
    @NotNull
    private EtablissementScolaire etablissementScolaire;

    @NotBlank
    @NotNull
    private int age;

    @NotBlank
    @NotNull
    private List<CoursDEnseignement> coursDEnseignements;

    private LocalTime heureDebutDisponibilite;

    private List<DayOfWeek> joursDisponibles;

    private List<HoraireClasse> horaireClasses;

    private LocalTime heureFinDisponibilite;


    public CreateEnseignantParameters toEnseignantParameters (){

        CreateEnseignantParameters createEnseignantParameters= new CreateEnseignantParameters();

        createEnseignantParameters.setCoursDEnseignements(getCoursDEnseignements());
        createEnseignantParameters.setUserName(new UserName(getPrenom(), getNomDeFamille()));
        createEnseignantParameters.setAge(CalculateUserAge.calculateAge(getDateDeNaissance()));
        createEnseignantParameters.setCreatedDate(getCreatedDate());
        createEnseignantParameters.setEtablissementScolaire(getEtablissementScolaire());
        createEnseignantParameters.setDateDeNaissance(getDateDeNaissance());
        createEnseignantParameters.setHeureDebutDisponibilite(getHeureDebutDisponibilite());
        createEnseignantParameters.setHeureFinDisponibilite(getHeureFinDisponibilite());

        return createEnseignantParameters;
    }
}
