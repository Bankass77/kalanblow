package ml.kalanblow.gestiondesinscriptions.request;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.Absence;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class EditAbsenceParameters extends CreateAbsenceParameters {

    private  long version;
    public void updateAbsenceEleve (Absence absenceEleve){

        Absence.AbsenceBuilder absenceEleveBuilder= new Absence.AbsenceBuilder();

        absenceEleveBuilder
                .motif(getMotif())
                .estJustifiee(isEstJustifiee())
                .cours(getCours())
                .appelDePresence(getPresence())
                .eleve(getEleve());
        absenceEleve  = absenceEleveBuilder.build();


    }
}
