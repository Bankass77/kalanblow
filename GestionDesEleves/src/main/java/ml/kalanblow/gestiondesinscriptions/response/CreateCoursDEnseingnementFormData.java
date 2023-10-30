package ml.kalanblow.gestiondesinscriptions.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.*;
import ml.kalanblow.gestiondesinscriptions.request.CreateCoursDEnseignementParameters;

import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCoursDEnseingnementFormData {

    @NotNull
    @NotBlank
    private String nomDuCours;

    @NotNull
    @NotBlank
    private String niveau;

    @NotNull
    @NotBlank
    private Periode anneeScolaire;

    @NotNull
    @NotBlank
    private Matiere matiere;

    private Set<Absence> absenceEleves;

    @NotNull
    @NotBlank
    private Set<Horaire> horaireClasses;

    @NotNull
    @NotBlank
    private Enseignant enseignant;

    @NotNull
    @NotBlank
    private Salle salleDeClasse;


    public CreateCoursDEnseignementParameters toCoursDEnseignementParameters() {
        CreateCoursDEnseignementParameters createCoursDEnseignementParameters = new CreateCoursDEnseignementParameters();
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
