package ml.kalanblow.gestiondesinscriptions.model;

import java.io.Serializable;
import java.time.LocalTime;

import jakarta.persistence.Embeddable;

// Classe pour stocker les heures de disponibilité
@Embeddable
public  class Disponibilite implements Serializable {
    private LocalTime heureDebut;
    private LocalTime heureFin;

    // Getters et setters
    public LocalTime getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(LocalTime heureDebut) {
        this.heureDebut = heureDebut;
    }

    public LocalTime getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(LocalTime heureFin) {
        this.heureFin = heureFin;
    }
}
