package ml.kalanblow.gestiondesinscriptions.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "absenceEleve")
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder = AbsenceEleve.AbsenceEleveBuilder.class)
public class AbsenceEleve implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String motif;

    @Column
    private LocalDate dateDeCreation;

    @Column
    private LocalDate dateDeJustification;

    @Column
    private boolean estJustifiee;

    @ManyToOne
    @JoinColumn(name = "cours_id")
    private CoursDEnseignement cours;

    @ManyToOne
    @JoinColumn(name = "eleve_id")
    private Eleve eleve;

    @ManyToOne
    @JoinColumn(name = "appel_de_presence_id")
    private AppelDePresence appelDePresence;

    private AbsenceEleve(AbsenceEleveBuilder absenceEleveBuilder) {

        this.estJustifiee = absenceEleveBuilder.estJustifiee;
        this.cours = absenceEleveBuilder.cours;
        this.motif = absenceEleveBuilder.motif;
        this.eleve = absenceEleveBuilder.eleve;
        this.appelDePresence = absenceEleveBuilder.appelDePresence;
    }

    /**
     * Builder de la class {@link AbsenceEleve}
     */
    @JsonPOJOBuilder(withPrefix = "")
    public static class AbsenceEleveBuilder {
        private HoraireClasse horaireClasse;
        private String motif;
        private boolean estJustifiee;
        private CoursDEnseignement cours;

        private AppelDePresence appelDePresence;
        private Eleve eleve;

        public AbsenceEleve build() {

            return new AbsenceEleve(this);
        }
    }


}
