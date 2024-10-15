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
import java.util.HashSet;
import java.util.List;
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

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "etablisementScolaireId")
    private Etablissement etablissement;

    @NotNull(message = "{notnull.message}")
    private boolean disponible;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "enseignant_disponibilites", joinColumns = @JoinColumn(name = "enseignant_id"))
    @Column(name = "jour_disponible")
    private Set<DayOfWeek> disponibilites = new HashSet<>();

}
