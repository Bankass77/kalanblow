package ml.kalanblow.gestiondesinscriptions.request;


import lombok.Data;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.CoursDEnseignement;

@Data
@NoArgsConstructor
public class EditCoursDEnseignementParameters extends CreateCoursDEnseignementParameters {

    public void updateCoursDEnseignement(CoursDEnseignement coursDEnseignement) {

        coursDEnseignement.setEnseignant(getEnseignant());
        coursDEnseignement.setNomDuCours(getNomDuCours());
        coursDEnseignement.setMatiere(getMatiere());
        coursDEnseignement.setNiveau(getNiveau());
        coursDEnseignement.setAnneeScolaire(getAnneeScolaire());
        coursDEnseignement.setSalleDeClasse(getSalleDeClasse());
        coursDEnseignement.setAbsenceEleves(getAbsenceEleves());
        coursDEnseignement.setHoraireClasses(getHoraireClasses());


    }
}
