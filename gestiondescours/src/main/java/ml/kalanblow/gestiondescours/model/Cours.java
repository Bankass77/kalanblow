package ml.kalanblow.gestiondescours.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Entity
@Table(name = "cours")
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
//@JsonInclude(value = JsonInclude.Include.NON_NULL)
//@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
public class Cours implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long coursId;

    @Column
    private String intitule;

    @Column
    private String description;

    @ElementCollection
    private List<Long> eleveCours; // Liste des élèves inscrits, référencés par leur ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matiereId")
    private Matiere matiere; // Matière enseignée dans ce cours

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enseignantId")
    private EnseignantCours enseignantResponsable; // Enseignant en charge de la matière


    @ElementCollection
    private List<Long> eleveId; // Référence aux élèves (IDs provenant du service Gestion des Élèves)

    @Column(nullable = true)
    private LocalDateTime dateDebut; // Date et heure de début du cours

    @Column(nullable = true)
    private LocalDateTime dateFin; // Date et heure de fin du cours

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salleId")
    private Salle salle; // Salle où le cours a lieu

    @Column(nullable = true)
    private int duree; // Durée du cours (en minutes)
}
