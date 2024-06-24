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
import ml.kalanblow.gestiondesinscriptions.enums.TypeDeClasse;
import ml.kalanblow.gestiondesinscriptions.service.PlageHoraire;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "SalleDeClasse")
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder = Salle.SalleBuilder.class)
@Data
@PlageHoraire
public class Salle implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Version()
    private Long version;

    @Column
    private String nomDeLaSalle;

    @Column
    private int nombreDePlace;

    @Column
    private boolean isAvailable;


    @Column
    @Enumerated(EnumType.STRING)
    private TypeDeClasse typeDeClasse;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "etablisementScolaireId")
    private Etablissement etablissement;

    @ElementCollection
    @CollectionTable(name = "horaires_classe", joinColumns = @JoinColumn(name = "classe_id"))
    @OrderColumn(name = "jour_semaine")
    private Set<Horaire> horaires;

    @OneToMany(mappedBy = "salle", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Cours> coursPlanifies;

    @OneToMany(mappedBy = "salle", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Eleve> eleves;



    private Salle(SalleBuilder salleBuilder) {

        this.coursPlanifies = salleBuilder.coursPlanifies;
        this.etablissement = salleBuilder.etablissement;
        this.nomDeLaSalle = salleBuilder.nomDeLaSalle;
        this.nombreDePlace = salleBuilder.nombreDePlace;
        this.typeDeClasse = salleBuilder.typeDeClasse;
        this.horaires=salleBuilder.horaires;

    }

    /**
     * Builder de la class {@link Salle}
     */
    @JsonPOJOBuilder(withPrefix = "")
    public static class SalleBuilder {
        private String nomDeLaSalle;
        private int nombreDePlace;
        private TypeDeClasse typeDeClasse;
        private Etablissement etablissement;
        private Set<Cours> coursPlanifies;
        private Set<Horaire> horaires;


        public Salle build() {
            return new Salle(this);
        }

    }

}
