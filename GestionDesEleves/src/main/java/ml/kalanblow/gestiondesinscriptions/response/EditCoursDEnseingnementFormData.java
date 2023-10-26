package ml.kalanblow.gestiondesinscriptions.response;


import lombok.Data;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.*;
import ml.kalanblow.gestiondesinscriptions.request.EditCoursDEnseignementParameters;

import java.util.Set;

@NoArgsConstructor
@Data
public class EditCoursDEnseingnementFormData  {
    
    private long version;
    private String nomDuCours;
    private String niveau;
    private Periode anneeScolaire;
    private Matiere matiere;
    private Set<Absence> absenceEleves;
    private Set<Horaire> horaireClasses;
    private Enseignant enseignant;
    private Salle salleDeClasse;

    public static EditCoursDEnseingnementFormData fromCoursDEnseingnementFormData(Cours coursDEnseignement) {

        EditCoursDEnseingnementFormData editCoursDEnseingnementFormData = new EditCoursDEnseingnementFormData();

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


    public EditCoursDEnseignementParameters toCoursDEnseignement (){

        EditCoursDEnseignementParameters editCoursDEnseignementParameters = new EditCoursDEnseignementParameters();
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
