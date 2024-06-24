package ml.kalanblow.gestiondesinscriptions.model;


import jakarta.persistence.Embeddable;
import lombok.*;
import ml.kalanblow.gestiondesinscriptions.service.PlageHoraire;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Embeddable
@Data
@PlageHoraire
public class Horaire implements Comparable<Horaire> {

    private DayOfWeek dayOfWeek;
    private LocalTime heureDebut;
    private LocalTime heureFin;

    @Override
    public int compareTo(Horaire horaire) {
        // Comparaison basée sur le jour de la semaine et l'heure de début
        int comparaisonJour = this.dayOfWeek.compareTo(horaire.getDayOfWeek());
        if (comparaisonJour != 0) {
            return comparaisonJour;
        }

        return this.heureDebut.compareTo(horaire.getHeureDebut());
    }

    public boolean isWithin(LocalTime start, LocalTime end) {
        return !heureDebut.isBefore(start) && !heureFin.isAfter(end);
    }
}
