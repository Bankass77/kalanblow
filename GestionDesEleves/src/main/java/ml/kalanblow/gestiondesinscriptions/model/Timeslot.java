package ml.kalanblow.gestiondesinscriptions.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ml.kalanblow.gestiondesinscriptions.service.PlageHoraire;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "creneaux")
@PlageHoraire
public class Timeslot  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long timeslotId;

    @Column
    private String timeslot;

    @OneToMany(mappedBy = "timeslot", cascade = CascadeType.ALL)
    private Set<Cours> coursList = new HashSet<>();

    @Embedded
    private Horaire horaire;
    public boolean overlaps(Timeslot other) {

        return this.horaire.getHeureFin().isBefore(other.horaire.getHeureFin()) && other.horaire.getHeureDebut().isBefore(this.horaire.getHeureFin());
    }
}
