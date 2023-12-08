package ml.kalanblow.gestiondesinscriptions.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;


@Entity
@Data
@Table(name = "population")
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Population {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  populationId;

    @OneToMany(mappedBy = "population", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Individual> population;

    private double populationFitness = -1;

    /**
     * Récupère l'individu le plus apte à partir de la population, en fonction de la fitness.
     * L'offset spécifie la position dans la liste triée des individus, du plus apte au moins apte.
     *
     * @param offset La position de l'individu dans la liste triée, avec 0 pour l'individu le plus apte.
     * @return L'individu le plus apte à la position spécifiée.
     */
    public Individual getFittest(int offset) {

        Individual[] individuals = population.toArray(new Individual[0]);

        Arrays.sort(individuals, (o1, o2) -> {
            if (o1.getFitness() > o2.getFitness()) {
                return -1;
            } else if (o1.getFitness() < o2.getFitness()) {
                return 1;
            }
            return 0;
        });

        return individuals[offset];
    }


    /**
     * Mélange aléatoirement les individus dans la population.
     * Utilise l'algorithme de mélange de Fisher-Yates.
     */
    public void shuffle() {
        // Convertissez la population en tableau
        Individual[] individuals = population.toArray(new Individual[0]);

        Random rnd = new Random();
        for (int i = individuals.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            Individual a = individuals[index];
            individuals[index] = individuals[i];
            individuals[i] = a;
        }
        population = new HashSet<>(Arrays.asList(individuals));
    }

}
