package ml.kalanblow.gestiondesinscriptions.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Table(name = "enseignant")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Enseignant implements Serializable {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long enseignantId;

    private User user;
    @NotNull(message = "{notnull.message}")
    @Column(unique = true, nullable = false, length = 20)
    private String leMatricule;

    @Column
    private int heuresTravaillees;  // Les heures normales

    @Column
    private int heuresSup;  // Les heures supplémentaires

    private static final int HEURES_MAX_SEMAINE = 18;  // 18 heures maximum par semaine

    private static final int HEURES_SUP_MAX = 3;  // 3 heures supplémentaires max

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "etablisementScolaireId")
    private Etablissement etablissement;

    @NotNull(message = "{notnull.message}")
    private boolean disponible;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "enseignant_disponibilites", joinColumns = @JoinColumn(name = "enseignant_id"))
    @Column(name = "jour_disponible")
    private Set<DayOfWeek> disponibilites = new HashSet<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "enseignant_disponibilites", joinColumns = @JoinColumn(name = "enseignant_id"))
    @MapKeyColumn(name = "jour_disponible")
    @Column(name = "heure_disponibilite")
    private Map<DayOfWeek, Disponibilite> hueresDisponibilites = new HashMap<>();
}
