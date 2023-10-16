package ml.kalanblow.gestiondesinscriptions.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Past;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

@PrimaryKeyJoinColumn(name = "id")
@DiscriminatorValue("ELEVES")
@Table(name = "eleves")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@JsonDeserialize(builder = Eleve.EleveBuilder.class)
public class Eleve extends User {

    private static final long serialVersionUID = 1L;
    @Column(name = "ine_number")
    private String ineNumber;

    @NotNull(message = "Age is required")
    @Column(name = "birthDate")
    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonIgnore
    @JsonFormat
    private LocalDate dateDeNaissance;

    @Column(name = "age")
    private int age;

    @Column
    @NotBlank
    @NotNull(message = "Mother First Name is required")
    private String motherFirstName;

    @Column
    @NotBlank
    @NotNull(message = "Mother Last Name is required")
    private String motherLastName;

    @Column
    @NotBlank
    @NotNull(message = "Father Last Name is required")
    private String fatherLastName;

    @Column
    @NotBlank
    @NotNull(message = "Father First Name is required")
    private String fatherFirstName;
    @OneToMany(mappedBy = "eleve")
    private List<AbsenceEleve> absences;

    @ManyToOne
    @JoinColumn(name = "etablisementScolaireId")
    private EtablissementScolaire etablissementScolaire;

    private Eleve(EleveBuilder eleveBuilder) {

        this.ineNumber = eleveBuilder.ineNumber;
        this.dateDeNaissance = eleveBuilder.dateDeNaissance;
        this.age = eleveBuilder.age;
        this.motherFirstName = eleveBuilder.motherFirstName;
        this.motherLastName = eleveBuilder.motherLastName;
        this.fatherFirstName = eleveBuilder.fatherLastName;
        this.fatherLastName = eleveBuilder.fatherLastName;
        this.etablissementScolaire=eleveBuilder.etablissementScolaire;
        this.absences=eleveBuilder.absences;
    }


    /**
     * Builder de la class Elève
     */
    @JsonPOJOBuilder(withPrefix = "")
    public static class EleveBuilder extends UserBuilder {
        private String ineNumber;
        private LocalDate dateDeNaissance;
        private int age;
        private String motherFirstName;
        private String motherLastName;
        private String fatherFirstName;
        private String fatherLastName;
        private EtablissementScolaire etablissementScolaire;
        private List<AbsenceEleve> absences;
        public Eleve build() {
            return new Eleve(this);
        }
    }

    // Méthode statique pour créer une instance de Eleve à partir du builder
    @JsonDeserialize(builder = User.UserBuilder.class)
    public static Eleve createEleveFromBuilder(EleveBuilder builder) {

        return builder().build();
    }
}
