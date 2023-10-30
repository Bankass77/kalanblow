package ml.kalanblow.gestiondesinscriptions.model;


import jakarta.persistence.Embeddable;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Embeddable
@Data
public class Horaire {

    private DayOfWeek dayOfWeek;
    private LocalTime heureDebut;
    private LocalTime heureFin;
}
