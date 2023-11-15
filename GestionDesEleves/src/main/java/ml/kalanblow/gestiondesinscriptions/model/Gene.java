package ml.kalanblow.gestiondesinscriptions.model;


import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Gene {
    private String nom; // Nom de la caractéristique génétique
    private int valeur; // Valeur associée à la caractéristique génétique


    public void muter() {

        double mutationChance = 0.1;

        if (Math.random() < mutationChance) {
            int mutationMagnitude = 5;
            valeur += (Math.random() < 0.5) ? mutationMagnitude : -mutationMagnitude;
        }
    }
}
