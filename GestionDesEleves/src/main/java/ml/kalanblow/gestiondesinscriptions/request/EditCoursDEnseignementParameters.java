package ml.kalanblow.gestiondesinscriptions.request;


import lombok.Data;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.Cours;

@Data
@NoArgsConstructor
public class EditCoursDEnseignementParameters extends CreateCoursDEnseignementParameters {
    private  long version;
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