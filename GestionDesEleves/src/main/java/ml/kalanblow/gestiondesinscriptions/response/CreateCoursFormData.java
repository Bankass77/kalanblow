package ml.kalanblow.gestiondesinscriptions.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.*;
import ml.kalanblow.gestiondesinscriptions.request.CreateCoursParameters;
import ml.kalanblow.gestiondesinscriptions.validation.ValidationGroupOne;

import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCoursFormData {


    @NotBlank
    @NotNull(groups = ValidationGroupOne.class)
    private String nomDuCours;

    @NotBlank
    @NotNull(groups = ValidationGroupOne.class)
    private String niveau;


    @NotBlank
    @NotNull(groups = ValidationGroupOne.class)
    private Periode anneeScolaire;


    @NotBlank
    @NotNull(groups = ValidationGroupOne.class)
    private Matiere matiere;

    private Set<Absence> absenceEleves;

    @NotNull
    @NotBlank
    private Set<Horaire> horaireClasses;


    @NotBlank
    @NotNull(groups = ValidationGroupOne.class)
    private Enseignant enseignant;


    @NotBlank
    @NotNull(groups = ValidationGroupOne.class)
    private Salle salleDeClasse;


    public CreateCoursParameters toCoursDEnseignementParameters() {
        CreateCoursParameters createCoursDEnseignementParameters = new CreateCoursParameters();
        createCoursDEnseignementParameters.setEnseignant(getEnseignant());
        createCoursDEnseignementParameters.setNomDuCours(getNomDuCours());
        createCoursDEnseignementParameters.setMatiere(getMatiere());
        createCoursDEnseignementParameters.setNiveau(getNiveau());
        createCoursDEnseignementParameters.setAnneeScolaire(getAnneeScolaire());
        createCoursDEnseignementParameters.setAbsenceEleves(getAbsenceEleves());
        createCoursDEnseignementParameters.setEnseignant(getEnseignant());
        createCoursDEnseignementParameters.setHoraireClasses(getHoraireClasses());
        createCoursDEnseignementParameters.setSalleDeClasse(getSalleDeClasse());

        return createCoursDEnseignementParameters;
    }
}
