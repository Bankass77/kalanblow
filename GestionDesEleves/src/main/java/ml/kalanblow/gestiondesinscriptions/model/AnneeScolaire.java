package ml.kalanblow.gestiondesinscriptions.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ml.kalanblow.gestiondesinscriptions.enums.TypeDeVacances;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Duration;
import java.util.List;

@Entity
@Table(name = "anneeScolaire")
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder = AnneeScolaire.AnneeScolaireBuilder.class)
@Data
public class AnneeScolaire {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private int annee;

    @Column
    private Duration duree;

    @Enumerated(EnumType.STRING)
    private TypeDeVacances typeDeVacances;


    private AnneeScolaire(AnneeScolaireBuilder anneeScolaireBuilder) {

        this.annee = anneeScolaireBuilder.annee;
        this.duree = anneeScolaireBuilder.duree;
        this.typeDeVacances = anneeScolaireBuilder.typeDeVacances;


    }

    public  static class AnneeScolaireBuilder {
        private int annee;
        private Duration duree;
        private TypeDeVacances typeDeVacances;


        public AnneeScolaireBuilder anneeScolaireBuilder (int annee){

            this.annee=annee;
            return  this;
        }


        public AnneeScolaireBuilder dureeAnneeScolaire( Duration duree){

            this.duree=duree;

            return  this;
        }

        public AnneeScolaireBuilder typeDeVacancesBuilder(TypeDeVacances typeDeVacances){

            this.typeDeVacances=typeDeVacances;

            return this;
        }
        public AnneeScolaire build() {

            return new AnneeScolaire(this);
        }

    }
}
