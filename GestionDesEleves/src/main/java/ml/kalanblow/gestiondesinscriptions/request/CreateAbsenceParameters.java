package ml.kalanblow.gestiondesinscriptions.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.Cours;
import ml.kalanblow.gestiondesinscriptions.model.Eleve;
import ml.kalanblow.gestiondesinscriptions.model.Horaire;
import ml.kalanblow.gestiondesinscriptions.model.Presence;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAbsenceParameters {

     
    private Horaire horaireClasse;

    private String motif;

    private boolean estJustifiee;

    private Cours cours;

     
    
    private Presence presence;

     
    
    private Eleve eleve;
}
