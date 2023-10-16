package ml.kalanblow.gestiondesinscriptions.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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
public class CoursDEnseignement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String nomDuCours;

    @Column
    private String niveau;

    @OneToMany(mappedBy = "cours")
    private Set<AbsenceEleve> absenceEleves;

    @ManyToOne
    @JoinColumn(name = "matiere_id")
    private Matiere matiere;

    @ElementCollection
    @CollectionTable(name = "horaire_cours", joinColumns = @JoinColumn(name = "cours_id"))
    @OrderColumn(name = "jour_semaine")
    private Set<HoraireClasse> horaireClasses;

    @JoinColumn(name = "enseignant_id")
    @ManyToOne
    private Enseignant enseignant;

    @ManyToOne
    @JoinColumn(name = "classe_id")
    private SalleDeClasse salleDeClasse;

    @ManyToOne
    @JoinColumn(name = "annee_scolaire_id")
    private AnneeScolaire anneeScolaire;

    private CoursDEnseignement(CoursDEnseignementBuilder coursDEnseignementBuilder) {

        this.enseignant= coursDEnseignementBuilder.enseignant;
        this.matiere= coursDEnseignementBuilder.matiere;
        this.horaireClasses=coursDEnseignementBuilder.horaireClasses;
        this.salleDeClasse= coursDEnseignementBuilder.salleDeClasse;
        this.absenceEleves=coursDEnseignementBuilder.absenceEleves;
        this.niveau=coursDEnseignementBuilder.niveau;
        this.nomDuCours= coursDEnseignementBuilder.nomDuCours;
        this.anneeScolaire=coursDEnseignementBuilder.anneeScolaire;

    }

    /**
     * Builder de la class {@link CoursDEnseignement}
     */
    @JsonPOJOBuilder(withPrefix = "")
    public static class CoursDEnseignementBuilder {
        private String nomDuCours;
        private String niveau;
        private AnneeScolaire anneeScolaire;
        private Matiere matiere;
        private Set<AbsenceEleve> absenceEleves;
        private Set<HoraireClasse> horaireClasses;
        private Enseignant enseignant;
        private SalleDeClasse salleDeClasse;
        public CoursDEnseignement build() {
            return new CoursDEnseignement(this);
        }
    }
}
