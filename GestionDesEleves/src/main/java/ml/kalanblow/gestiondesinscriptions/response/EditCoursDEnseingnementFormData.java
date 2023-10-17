package ml.kalanblow.gestiondesinscriptions.response;


import lombok.Data;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.CoursDEnseignement;
import ml.kalanblow.gestiondesinscriptions.request.EditCoursDEnseignementParameters;

@NoArgsConstructor
@Data
public class EditCoursDEnseingnementFormData extends CreateCoursDEnseingnementFormData {

    public static EditCoursDEnseingnementFormData fromCoursDEnseingnementFormData(CoursDEnseignement coursDEnseignement) {

        EditCoursDEnseingnementFormData editCoursDEnseingnementFormData = new EditCoursDEnseingnementFormData();

        editCoursDEnseingnementFormData.setNomDuCours(coursDEnseignement.getNomDuCours());
        editCoursDEnseingnementFormData.setMatiere(coursDEnseignement.getMatiere());
        editCoursDEnseingnementFormData.setEnseignant(coursDEnseignement.getEnseignant());
        editCoursDEnseingnementFormData.setNiveau(coursDEnseignement.getNiveau());
        editCoursDEnseingnementFormData.setAnneeScolaire(coursDEnseignement.getAnneeScolaire());
        editCoursDEnseingnementFormData.setAbsenceEleves(coursDEnseignement.getAbsenceEleves());
        editCoursDEnseingnementFormData.setHoraireClasses(coursDEnseignement.getHoraireClasses());
        editCoursDEnseingnementFormData.setSalleDeClasse(coursDEnseignement.getSalleDeClasse());

        return editCoursDEnseingnementFormData;
    }


    public EditCoursDEnseignementParameters toCoursDEnseignement (){

        EditCoursDEnseignementParameters editCoursDEnseignementParameters = new EditCoursDEnseignementParameters();


        return  editCoursDEnseignementParameters;


    }
}
