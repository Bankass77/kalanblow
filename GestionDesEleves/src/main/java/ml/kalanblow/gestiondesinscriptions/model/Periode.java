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

@Entity
@Table(name = "anneeScolaire")
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder = Periode.PeriodeBuilder.class)
@Data
public class Periode {

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

    @Column
    @Version()
    private Long version;


    private Periode(PeriodeBuilder periodeBuilder) {

        this.annee = periodeBuilder.annee;
        this.duree = periodeBuilder.duree;
        this.typeDeVacances = periodeBuilder.typeDeVacances;


    }

    public  static class PeriodeBuilder {
        private int annee;
        private Duration duree;
        private TypeDeVacances typeDeVacances;


        public PeriodeBuilder periodeBuilder (int annee){

            this.annee=annee;
            return  this;
        }


        public PeriodeBuilder dureeAnneeScolaire( Duration duree){

            this.duree=duree;

            return  this;
        }

        public PeriodeBuilder typeDeVacancesBuilder(TypeDeVacances typeDeVacances){

            this.typeDeVacances=typeDeVacances;

            return this;
        }
        public Periode build() {

            return new Periode(this);
        }

    }
}
