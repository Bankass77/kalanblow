package ml.kalanblow.gestiondesinscriptions.model;


import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Embeddable
@Data
public class HoraireClasse {

    private DayOfWeek dayOfWeek;
    private LocalTime heureDebut;
    private LocalTime heureFin;
}
