package ml.kalanblow.gestiondesinscriptions.request;


import lombok.Data;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.AbsenceEleve;

@NoArgsConstructor
@Data
public class EditAbsenceEleveParameters extends  CreateAbsenceEleveParameters{


    public void updateAbsenceEleve (AbsenceEleve absenceEleve){

        AbsenceEleve.AbsenceEleveBuilder absenceEleveBuilder= new AbsenceEleve.AbsenceEleveBuilder();

        absenceEleveBuilder
                .motif(getMotif())
                .estJustifiee(isEstJustifiee())
                .cours(getCours())
                .appelDePresence(getAppelDePresence())
                .eleve(getEleve());
        absenceEleve  = absenceEleveBuilder.build();


    }
}
