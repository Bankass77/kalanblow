package ml.kalanblow.gestiondesinscriptions.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;
import ml.kalanblow.gestiondesinscriptions.validation.ValidationGroupOne;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

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
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonIgnore
    @JsonFormat
    private LocalDate dateDeNaissance;

    @Column(name = "age")
    private int age;

    @Column
    @NotBlank
    @NotNull(message = "Mother First Name is required")
    @Size(min = 1, max = 200, groups = ValidationGroupOne.class)
    private String motherFirstName;

    @Column
    @NotBlank
    @NotNull(message = "Mother Last Name is required")
    @Size(min = 1, max = 200, groups = ValidationGroupOne.class)
    private String motherLastName;

    @Column
    @NotBlank
    @NotNull(message = "Father Last Name is required")
    @Size(min = 1, max = 200, groups = ValidationGroupOne.class)
    private String fatherLastName;

    @Column
    @NotBlank
    @NotNull(message = "Father First Name is required")
    private String fatherFirstName;
    @OneToMany(mappedBy = "eleve")
    private List<Absence> absences;

    @ManyToOne
    @JoinColumn(name = "etablisementScolaireId")
    private Etablissement etablissement;

    @ManyToOne
    @JoinColumn(name = "classe_Id")
    private Salle salle;

    private Eleve(EleveBuilder eleveBuilder) {

        this.ineNumber = eleveBuilder.ineNumber;
        this.dateDeNaissance = eleveBuilder.dateDeNaissance;
        this.age = eleveBuilder.age;
        this.motherFirstName = eleveBuilder.motherFirstName;
        this.motherLastName = eleveBuilder.motherLastName;
        this.fatherFirstName = eleveBuilder.fatherLastName;
        this.fatherLastName = eleveBuilder.fatherLastName;
        this.etablissement =eleveBuilder.etablissement;
        this.absences=eleveBuilder.absences;

    }


    /**
     * Builder de la class Elève
     */
    @JsonPOJOBuilder(withPrefix = "")
    public static class EleveBuilder extends User.UserBuilder {
        private String ineNumber;
        private LocalDate dateDeNaissance;
        private int age;
        private String motherFirstName;
        private String motherLastName;
        private String fatherFirstName;
        private String fatherLastName;
        private Etablissement etablissement;
        private List<Absence> absences;

        public EleveBuilder ineNumber(String  ineNumber){

            this.ineNumber= ineNumber;
            return  this;
        }

        public  EleveBuilder dateDeNaissance (LocalDate dateDeNaissance){

            this.dateDeNaissance=dateDeNaissance;
            return this;
        }

        public EleveBuilder motherFirstName(String motherFirstName){

            this.motherFirstName=motherFirstName;
            return  this;
        }

        public  EleveBuilder motherLastName (String motherLastName){

            this.motherLastName=motherLastName;
            return  this;
        }

        public EleveBuilder fatherFirstName(String fatherFirstName){

            this.fatherFirstName=fatherFirstName;
            return  this;
        }

        public EleveBuilder fathreLastName(String fatherLastName){

            this.fatherLastName=fatherLastName;

            return  this;
        }

        public EleveBuilder etablissementScolaire (Etablissement etablissement){

            this.etablissement = etablissement;

            return  this;
        }

        public EleveBuilder absences  (List<Absence> absences){

            this.absences=absences;
            return this;
        }
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
