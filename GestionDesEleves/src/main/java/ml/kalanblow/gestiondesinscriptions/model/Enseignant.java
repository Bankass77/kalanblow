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

import java.time.LocalDate;

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

    @ManyToOne
    @JoinColumn(name = "etablisementScolaireId")
    private EtablissementScolaire etablissementScolaire;

    private Enseignant(EnseignantBuilder enseignantBuilder) {
        this.leMatricule = enseignantBuilder.leMatricule;
        this.age= enseignantBuilder.age;
        this.etablissementScolaire= enseignantBuilder.etablissementScolaire;
        this.dateDeNaissance=enseignantBuilder.dateDeNaissance;

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

    public  Enseignant build() {

        return  new Enseignant();
    }

    }

    // Méthode statique pour créer une instance d'Enseignant à partir du builder
    @JsonDeserialize(builder = User.UserBuilder.class)
    public static Enseignant createEnseignatFromBuilder(EnseignantBuider builder) {

        return builder().build();
    }


}
