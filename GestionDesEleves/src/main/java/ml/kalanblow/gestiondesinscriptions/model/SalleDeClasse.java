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
@JsonDeserialize(builder = SalleDeClasse.SalleDeClasseBuilder.class)
@Data
public class SalleDeClasse implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String nomDeLaSalle;

    @Column
    private int nombreDePlace;

    @Column
    @Enumerated(EnumType.STRING)
    private TypeDeClasse typeDeClasse;

    @ManyToOne
    @JoinColumn(name = "etablisementScolaireId")
    private EtablissementScolaire etablissementScolaire;


    @OneToMany(mappedBy = "salleDeClasse")
    private Set<CoursDEnseignement> coursPlanifies;


    private SalleDeClasse(SalleDeClasseBuilder salleDeClasseBuilder) {

        this.coursPlanifies = salleDeClasseBuilder.coursPlanifies;
        this.etablissementScolaire = salleDeClasseBuilder.etablissementScolaire;
        this.nomDeLaSalle = salleDeClasseBuilder.nomDeLaSalle;
        this.nombreDePlace = salleDeClasseBuilder.nombreDePlace;
        this.typeDeClasse = salleDeClasseBuilder.typeDeClasse;

    }

    /**
     * Builder de la class {@link SalleDeClasse}
     */
    @JsonPOJOBuilder(withPrefix = "")
    public static class SalleDeClasseBuilder {
        private String nomDeLaSalle;
        private int nombreDePlace;
        private TypeDeClasse typeDeClasse;
        private EtablissementScolaire etablissementScolaire;
        private Set<CoursDEnseignement> coursPlanifies;


        public SalleDeClasse build() {
            return new SalleDeClasse(this);
        }

    }

}
