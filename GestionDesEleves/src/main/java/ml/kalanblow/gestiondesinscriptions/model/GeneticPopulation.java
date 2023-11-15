package ml.kalanblow.gestiondesinscriptions.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Data
@Table(name = "genetic_population")
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeneticPopulation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "geneticPopulation", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Individual> individuals;

    private double populationFitness = -1;

    public List<Individual> getFittest(int numberOfIndividuals) {
        List<Individual> fittestIndividuals = new ArrayList<>(individuals);
        fittestIndividuals.sort((o1, o2) -> Double.compare(o2.getFitness(), o1.getFitness()));

        return fittestIndividuals.subList(0, Math.min(numberOfIndividuals, fittestIndividuals.size()));
    }

    public void shuffle() {
        Collections.shuffle(individuals);
    }
}
