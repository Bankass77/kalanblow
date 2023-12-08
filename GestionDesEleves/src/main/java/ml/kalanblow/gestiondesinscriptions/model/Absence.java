package ml.kalanblow.gestiondesinscriptions.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "absenceEleve")
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder = Absence.AbsenceBuilder.class)
@Data
public class Absence implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long version;

    @Column
    @NotNull(message = "{notnull.message}")
    private String motif;

    @Column
    @NotNull(message = "{notnull.message}")
    private LocalDate dateDeCreation;

    @Column
    @NotNull(message = "{notnull.message}")
    private LocalDate dateDeJustification;

    @Column
    @NotNull(message = "{notnull.message}")
    private boolean estJustifiee;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "cours_id")
    @JsonBackReference
    private Cours cours;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "eleve_id")
    @JsonBackReference
    private Eleve eleve;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "appel_de_presence_id")
    @JsonBackReference
    private Presence presence;

    private Absence(AbsenceBuilder absenceBuilder) {

        this.estJustifiee = absenceBuilder.estJustifiee;
        this.cours = absenceBuilder.cours;
        this.motif = absenceBuilder.motif;
        this.eleve = absenceBuilder.eleve;
        this.presence = absenceBuilder.presence;
    }

    /**
     * Builder de la class {@link Absence}
     */
    @JsonPOJOBuilder(withPrefix = "")
    public static class AbsenceBuilder {
        private Horaire horaireClasse;
        private String motif;
        private boolean estJustifiee;
        private Cours cours;

        private Presence presence;
        private Eleve eleve;

        public AbsenceBuilder motif(String motif) {
            this.motif = motif;
            return this;
        }

        public AbsenceBuilder estJustifiee(boolean estJustifiee) {
            this.estJustifiee = estJustifiee;
            return this;
        }

        public AbsenceBuilder cours(Cours cours) {
            this.cours = cours;
            return this;
        }

        public AbsenceBuilder appelDePresence ( Presence presence){

            this.presence = presence;

            return  this;
        }

        public AbsenceBuilder eleve (Eleve eleve){

            this.eleve=eleve;

            return this;
        }

        public Absence build() {

            return new Absence(this);
        }
    }


}
