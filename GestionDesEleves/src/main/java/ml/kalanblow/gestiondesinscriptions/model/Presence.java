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
import java.time.LocalDateTime;
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
@JsonDeserialize(builder = Presence.PresenceBuilder.class)
@Data
public class Presence implements Serializable {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Version()
    private Long version;

    @Column
    private LocalDateTime dateAppel;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "cours_id")
    private Cours cours;

    @OneToMany(mappedBy = "presence", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Absence> absences;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "eleve_id")
    private Eleve eleve;


    private Presence(PresenceBuilder presenceBuilder) {
        this.absences = presenceBuilder.absences;
        this.cours = presenceBuilder.cours;
        this.eleve = presenceBuilder.eleve;
        this.dateAppel = presenceBuilder.dateAppel;

    }


    /**
     * Builder de la class {@link Presence}
     */
    @JsonPOJOBuilder(withPrefix = "")
    public static class PresenceBuilder {
        private Cours cours;
        private Eleve eleve;
        private LocalDateTime dateAppel;
        private List<Absence> absences;
        public Presence build() {

            return new Presence(this);
        }

    }


}
