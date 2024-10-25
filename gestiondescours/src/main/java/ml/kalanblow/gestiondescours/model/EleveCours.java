package ml.kalanblow.gestiondescours.model;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Entity
@Table(name = "eleveCours")
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class EleveCours implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eleveId;  // Référence à l'élève (ID provenant du service Gestion des Élèves)

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "cours_eleves",
            joinColumns = @JoinColumn(name = "eleveId"),
            inverseJoinColumns = @JoinColumn(name = "coursId"))
    private List<Cours> cours;  // Cours auxquels l'élève est inscrit
}
