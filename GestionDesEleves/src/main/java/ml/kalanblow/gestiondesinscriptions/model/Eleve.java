package ml.kalanblow.gestiondesinscriptions.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.*;
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

    @NotNull(message = "dateDeNaissance is required")
    @Column(name = "birthDate")
    @Past
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonIgnore
    @JsonFormat
    private LocalDate dateDeNaissance;

    @Column(name = "age")
    private int age;


    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "pere_id")
    private Parent pere;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "mere_id")
    private Parent mere;

    @OneToMany(mappedBy = "eleve", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Absence> absences;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "etablisementScolaireId")
    private Etablissement etablissement;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "classe_Id")
    private Salle salle;

    private Eleve(EleveBuilder eleveBuilder) {

        this.ineNumber = eleveBuilder.ineNumber;
        this.dateDeNaissance = eleveBuilder.dateDeNaissance;
        this.age = eleveBuilder.age;
        this.pere= eleveBuilder.pere;
        this.mere=eleveBuilder.mere;
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

        private Parent parent;

        private Etablissement etablissement;
        private List<Absence> absences;

        public EleveBuilder ineNumber(String  ineNumber){

            this.ineNumber= ineNumber;
            return  this;
        }


        public EleveBuilder pere (Parent pere){

            this.pere=pere;

            return this;
        }

        public  EleveBuilder mere (Parent mere){

            this.mere=mere;
            return  this;
        }
        public  EleveBuilder dateDeNaissance (LocalDate dateDeNaissance){

            this.dateDeNaissance=dateDeNaissance;
            return this;
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
