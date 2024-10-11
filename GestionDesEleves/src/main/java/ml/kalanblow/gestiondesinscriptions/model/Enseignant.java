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
import java.util.List;


@Table(name = "enseignant")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@JsonDeserialize(builder = Enseignant.EnseignantBuider.class)
public class Enseignant implements Serializable {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long enseignantId;

    private User user;
    @NotBlank
    @Column
    private String leMatricule;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "etablisementScolaireId")
    private Etablissement etablissement;

    @NotNull(message = "{notnull.message}")
    private boolean disponible;

    private Enseignant(EnseignantBuider enseignantBuilder) {
        this.leMatricule = enseignantBuilder.leMatricule;
        this.etablissement = enseignantBuilder.etablissement;
    }


    /**
     * Builder de la class Elève
     */
    @JsonPOJOBuilder(withPrefix = "")
    public static class EnseignantBuider extends User.UserBuilder {
        private String leMatricule;
        private LocalDate dateDeNaissance;
        private Etablissement etablissement;
        private int age;
        private LocalTime heureDebutDisponibilite;
        private List<DayOfWeek> joursDisponibles;
        private LocalTime heureFinDisponibilite;


        public EnseignantBuider leMatricule(String leMatricule) {

            this.leMatricule = leMatricule;

            return this;
        }

        public EnseignantBuider dateDeNaissance(LocalDate dateDeNaissance) {

            this.dateDeNaissance = dateDeNaissance;

            return this;
        }

        public EnseignantBuider etablissementScolaire(Etablissement etablissement) {

            this.etablissement = etablissement;

            return this;
        }

        public EnseignantBuider heureDebutDisponibilite(LocalTime heureDebutDisponibilite) {

            this.heureDebutDisponibilite = heureDebutDisponibilite;
            return this;
        }

        public EnseignantBuider heureFinDisponibilite(LocalTime heureFinDisponibilite) {
            this.heureFinDisponibilite = heureFinDisponibilite;

            return this;
        }

        public EnseignantBuider joursDisponibilite(List<DayOfWeek> joursDisponibles) {
            this.joursDisponibles = joursDisponibles;
            return this;
        }

        public Enseignant build() {

            return new Enseignant(this);
        }

    }

    // Méthode statique pour créer une instance d'Enseignant à partir du builder
    @JsonDeserialize(builder = User.UserBuilder.class)
    public static Enseignant createEnseignatFromBuilder(EnseignantBuider builder) {

        return builder().build();
    }

}
