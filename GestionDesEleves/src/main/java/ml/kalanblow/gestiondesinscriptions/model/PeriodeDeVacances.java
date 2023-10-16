package ml.kalanblow.gestiondesinscriptions.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import ml.kalanblow.gestiondesinscriptions.enums.TypeDeVacances;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "periodeDeVacances")
@Accessors(chain = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder=PeriodeDeVacances.PeriodeDeVacancesBuilder.class)
public class PeriodeDeVacances {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private LocalDate dateDebut;
    @Column
    private LocalDate dateFin;

    private PeriodeDeVacances (PeriodeDeVacancesBuilder periodeDeVacancesBuilder){
        this.dateDebut=periodeDeVacancesBuilder.dateDebut;
        this.dateFin=periodeDeVacancesBuilder.dateFin;

    }

    public static class PeriodeDeVacancesBuilder {

        private LocalDate dateDebut;

        private LocalDate dateFin;

        public PeriodeDeVacances build(){

            return new PeriodeDeVacances(this);
        }
    }
}
