package ml.kalanblow.gestiondesinscriptions.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ml.kalanblow.gestiondesinscriptions.enums.TypeCours;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Version()
    private Long version;

    @Column
    @NotNull(message = "{notnull.message}")
    private String nomDuCours;

    @Column
    @NotNull(message = "{notnull.message}")
    private String niveau;

    @OneToMany(mappedBy = "cours", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Absence> absenceEleves;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "matiere_id")
    private Matiere matiere;

    @ElementCollection
    @CollectionTable(name = "horaire_cours", joinColumns = @JoinColumn(name = "cours_id"))
    @OrderColumn(name = "jour_semaine")
    private Set<Horaire> horaires;

    @JoinColumn(name = "enseignant_id")
    @ManyToOne
    private Enseignant enseignant;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "classe_id")
    private Salle salle;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "annee_scolaire_id")
    private Periode anneeScolaire;

    @ManyToMany
    @JoinTable(
            name = "cours_group",
            joinColumns = @JoinColumn(name = "cours_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private Set<Group> groups;


    @ManyToOne
    @JoinColumn(name = "timeslot_id")
    private Timeslot timeslot;

    @ManyToOne
    @JoinColumn(name = "individual_id")
    private Individual individual;

    private TypeCours typeCours;

    @ManyToMany( cascade = CascadeType.ALL)
    private Set<Timetable> timetables;



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

    public void addTimetable(Timetable timetable) {
        timetables.add(timetable);
        timetable.getCours().add(this);
    }
}
