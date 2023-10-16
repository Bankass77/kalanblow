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
public class Enseignant extends User {

    @NotBlank
    @Column
    private String leMatricule;

    @NotNull(message = "Age is required")
    @Column(name = "birthDate")
    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonIgnore
    @JsonFormat
    private LocalDate dateDeNaissance;

    @Column(name = "age")
    private int age;

    @OneToMany(mappedBy = "enseignant")
    private List<CoursDEnseignement> coursDEnseignements;

    @ElementCollection
    @CollectionTable(name = "disponibilites", joinColumns = @JoinColumn(name = "enseignant_id"))
    @Column(name = "jour_semaine")
    private List<DayOfWeek> joursDisponibles;

    @Column(name = "heure_debut_disponibilite")
    private LocalTime heureDebutDisponibilite;

    @Column(name = "heure_fin_disponibilite")
    private LocalTime heureFinDisponibilite;

    @ManyToOne
    @JoinColumn(name = "etablisementScolaireId")
    private EtablissementScolaire etablissementScolaire;

    @ElementCollection
    @CollectionTable(name = "horaires_enseignant", joinColumns = @JoinColumn(name = "enseignant_id"))
    @OrderColumn(name = "jour_semaine")
    private List<HoraireClasse> horaireClasses;

    private Enseignant(EnseignantBuider enseignantBuilder) {
        this.leMatricule = enseignantBuilder.leMatricule;
        this.age = enseignantBuilder.age;
        this.etablissementScolaire = enseignantBuilder.etablissementScolaire;
        this.dateDeNaissance = enseignantBuilder.dateDeNaissance;
        this.coursDEnseignements= enseignantBuilder.coursDEnseignements;
        this.heureDebutDisponibilite= enseignantBuilder.heureDebutDisponibilite;
        this.heureFinDisponibilite= enseignantBuilder.heureFinDisponibilite;
        this.joursDisponibles= enseignantBuilder.joursDisponibles;
        this.horaireClasses= enseignantBuilder.horaireClasses;

    }

    /**
     * Builder de la class Elève
     */
    @JsonPOJOBuilder(withPrefix = "")
    public static class EnseignantBuider extends UserBuilder {
        private String leMatricule;
        private LocalDate dateDeNaissance;
        private EtablissementScolaire etablissementScolaire;
        private int age;
        private List<CoursDEnseignement> coursDEnseignements;
        private LocalTime heureDebutDisponibilite;
        private List<DayOfWeek> joursDisponibles;
        private List<HoraireClasse> horaireClasses;
        private LocalTime heureFinDisponibilite;

        public Enseignant build (){

            return new Enseignant(this);
        }

    }

    // Méthode statique pour créer une instance d'Enseignant à partir du builder
    @JsonDeserialize(builder = User.UserBuilder.class)
    public static Enseignant createEnseignatFromBuilder(EnseignantBuider builder) {

        return builder().build();
    }


}
