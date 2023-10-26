package ml.kalanblow.gestiondesinscriptions.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "matiere")
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder = Matiere.MatiereBuilder.class)
@Data
public class Matiere implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version()
    private Long version;

    @Column
    private double note;

    @Column
    private double coefficient;

    @Column
    private double moyenne;

    @Column
    private String nomMatiere;

    @Column
    private String description;


    @OneToMany(mappedBy = "matiere", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Cours> coursDEnseignements;


    private Matiere(MatiereBuilder matiereBuilder) {
        this.coursDEnseignements = matiereBuilder.coursDEnseignements;
        this.nomMatiere = matiereBuilder.nomMatiere;
        this.description = matiereBuilder.description;
        this.moyenne = matiereBuilder.coefficient;
        this.note = matiereBuilder.note;
        this.coefficient = matiereBuilder.coefficient;


    }

    /**
     * Builder de la class {@link Matiere}
     */
    @JsonPOJOBuilder(withPrefix = "")
    public static class MatiereBuilder {
        private double note;
        private double coefficient;
        private double moyenne;
        private String nomMatiere;

        private String description;
        private Set<Cours> coursDEnseignements;


        public MatiereBuilder note(double note) {

            this.note = note;
            return this;
        }

        public MatiereBuilder coefficient(double coefficient) {

            this.coefficient = coefficient;

            return this;
        }

        public MatiereBuilder moyenne(double moyenne) {

            this.moyenne = moyenne;

            return this;
        }

        public MatiereBuilder nomMatiere(String nomMatiere) {

            this.nomMatiere = nomMatiere;
            return this;
        }

        public MatiereBuilder description(String description) {

            this.description = description;
            return this;
        }

        public MatiereBuilder cours(Set<Cours> coursDEnseignements) {

            this.coursDEnseignements = coursDEnseignements;

            return this;
        }

        public Matiere build() {

            return new Matiere(this);
        }

        public static Matiere creerMatiere(MatiereBuilder matiereBuilder) {

            return matiereBuilder.build();
        }

    }
}
