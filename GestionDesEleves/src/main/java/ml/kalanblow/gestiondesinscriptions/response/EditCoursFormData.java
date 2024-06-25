package ml.kalanblow.gestiondesinscriptions.response;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.*;
import ml.kalanblow.gestiondesinscriptions.request.EditCoursParameters;

import java.util.Set;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class EditCoursFormData   extends  CreateCoursFormData{
    
    private long version;
    private String nomDuCours;
    private String niveau;
    private Periode anneeScolaire;
    private Matiere matiere;
    private Set<Absence> absenceEleves;
    private Set<Horaire> horaireClasses;
    private Enseignant enseignant;
    private Salle salleDeClasse;

    public static EditCoursFormData fromCoursDEnseingnementFormData(Cours coursDEnseignement) {

        EditCoursFormData editCoursDEnseingnementFormData = new EditCoursFormData();

        editCoursDEnseingnementFormData.setNomDuCours(coursDEnseignement.getNomDuCours());
        editCoursDEnseingnementFormData.setMatiere(coursDEnseignement.getMatiere());
        editCoursDEnseingnementFormData.setEnseignant(coursDEnseignement.getEnseignant());
        editCoursDEnseingnementFormData.setNiveau(coursDEnseignement.getNiveau());
        editCoursDEnseingnementFormData.setAnneeScolaire(coursDEnseignement.getAnneeScolaire());
        editCoursDEnseingnementFormData.setAbsenceEleves(coursDEnseignement.getAbsenceEleves());
        editCoursDEnseingnementFormData.setHoraireClasses(coursDEnseignement.getHoraires());
        editCoursDEnseingnementFormData.setSalleDeClasse(coursDEnseignement.getSalle());
        editCoursDEnseingnementFormData.setVersion(coursDEnseignement.getVersion());

        return editCoursDEnseingnementFormData;
    }


    public EditCoursParameters toCoursDEnseignement (){

        EditCoursParameters editCoursDEnseignementParameters = new EditCoursParameters();
        editCoursDEnseignementParameters.setEnseignant(getEnseignant());
        editCoursDEnseignementParameters.setVersion(version);
        editCoursDEnseignementParameters.setNomDuCours(getNomDuCours());
        editCoursDEnseignementParameters.setMatiere(getMatiere());
        editCoursDEnseignementParameters.setNiveau(getNiveau());
        editCoursDEnseignementParameters.setAbsenceEleves(getAbsenceEleves());
        editCoursDEnseignementParameters.setAnneeScolaire(getAnneeScolaire());
        editCoursDEnseignementParameters.setHoraireClasses(getHoraireClasses());
        editCoursDEnseignementParameters.setSalleDeClasse(getSalleDeClasse());

        return  editCoursDEnseignementParameters;


    }
}
