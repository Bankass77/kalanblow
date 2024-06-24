package ml.kalanblow.gestiondesinscriptions.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.*;
import ml.kalanblow.gestiondesinscriptions.constraint.PlageHoraire;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@PrimaryKeyJoinColumn(name = "id")
@DiscriminatorValue("Enseignant")
@Table(name = "enseignant")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@JsonDeserialize(builder = Enseignant.EnseignantBuider.class)
@PlageHoraire
public class Enseignant extends User {

    @NotBlank
    @Column
    private String leMatricule;

    @NotNull(message = "{notnull.message}")
    @Column(name = "birthDate")
    @Past
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonIgnore
    @JsonFormat
    private LocalDate dateDeNaissance;

    @Column(name = "age")
    @NotNull(message = "{notnull.message}")
    private int age;

    @OneToMany(mappedBy = "enseignant", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cours> coursDEnseignements;

    @ElementCollection
    @CollectionTable(name = "disponibilites", joinColumns = @JoinColumn(name = "enseignant_id"))
    @Column(name = "jour_semaine")
    private List<DayOfWeek> joursDisponibles;

    @Column(name = "heure_debut_disponibilite")
    private LocalTime heureDebutDisponibilite;

    @Column(name = "heure_fin_disponibilite")
    private LocalTime heureFinDisponibilite;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "etablisementScolaireId")
    private Etablissement etablissement;


    // collection d'horaires pendant lesquels l'enseignant est disponible
    @ElementCollection
    @CollectionTable(name = "horaires_enseignant", joinColumns = @JoinColumn(name = "enseignant_id"))
    @OrderColumn(name = "jour_semaine")
    private List<Horaire> disponibilites;

    @NotNull(message = "{notnull.message}")
    private boolean disponible;

    private Enseignant(EnseignantBuider enseignantBuilder) {
        this.leMatricule = enseignantBuilder.leMatricule;
        this.age = enseignantBuilder.age;
        this.etablissement = enseignantBuilder.etablissement;
        this.dateDeNaissance = enseignantBuilder.dateDeNaissance;
        this.coursDEnseignements = enseignantBuilder.coursDEnseignements;
        this.heureDebutDisponibilite = enseignantBuilder.heureDebutDisponibilite;
        this.heureFinDisponibilite = enseignantBuilder.heureFinDisponibilite;
        this.joursDisponibles = enseignantBuilder.joursDisponibles;
        this.disponibilites = enseignantBuilder.horaireClasses;

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
        private List<Cours> coursDEnseignements;
        private LocalTime heureDebutDisponibilite;
        private List<DayOfWeek> joursDisponibles;
        private List<Horaire> horaireClasses;
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

        public EnseignantBuider coursDEnseignements(List<Cours> coursDEnseignements) {

            this.coursDEnseignements = coursDEnseignements;

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

        public EnseignantBuider horaireClasses(List<Horaire> horaireClasses) {

            this.horaireClasses = horaireClasses;
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
