package ml.kalanblow.gestiondesinscriptions.request;


import lombok.Data;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.Cours;

@Data
@NoArgsConstructor
public class EditCoursParameters extends CreateCoursParameters {
    private  long version;

    // tag::update[]
    public void updateCoursDEnseignement(Cours coursDEnseignement) {

        coursDEnseignement.setEnseignant(getEnseignant());
        coursDEnseignement.setNomDuCours(getNomDuCours());
        coursDEnseignement.setMatiere(getMatiere());
        coursDEnseignement.setNiveau(getNiveau());
        coursDEnseignement.setAnneeScolaire(getAnneeScolaire());
        coursDEnseignement.setSalle(getSalleDeClasse());
        coursDEnseignement.setAbsenceEleves(getAbsenceEleves());
        coursDEnseignement.setHoraires(getHoraireClasses());


    }
}
