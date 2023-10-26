package ml.kalanblow.gestiondesinscriptions.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@Table(name = "Cours")
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Cours implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    @Version()
    private Long version;

    @Column
    private String nomDuCours;

    @Column
    private String niveau;

    @OneToMany(mappedBy = "cours")
    private Set<Absence> absenceEleves;

    @ManyToOne
    @JoinColumn(name = "matiere_id")
    private Matiere matiere;

    @ElementCollection
    @CollectionTable(name = "horaire_cours", joinColumns = @JoinColumn(name = "cours_id"))
    @OrderColumn(name = "jour_semaine")
    private Set<Horaire> horaires;

    @JoinColumn(name = "enseignant_id")
    @ManyToOne
    private Enseignant enseignant;

    @ManyToOne
    @JoinColumn(name = "classe_id")
    private Salle salle;

    @ManyToOne
    @JoinColumn(name = "annee_scolaire_id")
    private Periode anneeScolaire;

    private Cours(CoursBuilder coursDEnseignementBuilder) {

        this.enseignant= coursDEnseignementBuilder.enseignant;
        this.matiere= coursDEnseignementBuilder.matiere;
        this.horaires=coursDEnseignementBuilder.horaires;
        this.salle= coursDEnseignementBuilder.salle;
        this.absenceEleves=coursDEnseignementBuilder.absenceEleves;
        this.niveau=coursDEnseignementBuilder.niveau;
        this.nomDuCours= coursDEnseignementBuilder.nomDuCours;
        this.anneeScolaire=coursDEnseignementBuilder.periode;

    }

    /**
     * Builder de la class {@link Cours}
     */
    @JsonPOJOBuilder(withPrefix = "")
    public static class CoursBuilder {
        private String nomDuCours;
        private String niveau;
        private Periode periode;
        private Matiere matiere;
        private Set<Absence> absenceEleves;
        private Set<Horaire> horaires;
        private Enseignant enseignant;
        private Salle salle;
        public Cours build() {
            return new Cours(this);
        }
    }
}
