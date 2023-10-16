package ml.kalanblow.gestiondesinscriptions.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.github.javafaker.App;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "presence")
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder = AppelDePresence.AppelDePresenceBuilder.class)
@Data
public class AppelDePresence implements Serializable {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cours_id")
    private CoursDEnseignement cours;

    @OneToMany(mappedBy = "appelDePresence")
    private List<AbsenceEleve> absences;

    @ManyToOne
    @JoinColumn(name = "eleve_id")
    private Eleve eleve;


    private AppelDePresence(AppelDePresenceBuilder appelDePresenceBuilder) {
        this.absences = appelDePresenceBuilder.absences;

        this.cours = appelDePresenceBuilder.cours;

        this.eleve = appelDePresenceBuilder.eleve;

    }


    /**
     * Builder de la class {@link AppelDePresence}
     */
    @JsonPOJOBuilder(withPrefix = "")
    public static class AppelDePresenceBuilder {

        private CoursDEnseignement cours;
        private Eleve eleve;

        private List<AbsenceEleve> absences;

        public AppelDePresence build() {

            return new AppelDePresence(this);
        }

    }


}
