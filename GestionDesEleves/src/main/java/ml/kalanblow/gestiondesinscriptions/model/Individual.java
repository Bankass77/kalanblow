package ml.kalanblow.gestiondesinscriptions.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import ml.kalanblow.gestiondesinscriptions.enums.TypeCours;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Data
@Table(name = "planning")
@Accessors(chain = true)
@RequiredArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Individual implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "individual", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Cours> emploiDuTemps;

    private double fitness;

    @ManyToOne
    @JoinColumn(name = "population_id")
    private Population population;

    @ManyToOne
    @JoinColumn(name = "geneticPopulation_id")
    GeneticPopulation geneticPopulation;

    @ElementCollection
    @CollectionTable(name = "individual_gene", joinColumns = @JoinColumn(name = "individual_id"))
    private  Set<Gene> genes;

    public Individual(List<Gene> genes) {

        this.genes=  new HashSet<>(genes);

    }


    public void evaluerFitness() {

        double scoreConflitsHoraires = evaluerConflitsHoraires();
        double scoreRepartitionEquitable = evaluerRepartitionEquitable();
        double scoreDisponibiliteEnseignants = evaluerDisponibiliteEnseignantsEtSalles();
        double scoreEquiteChargeTravail = evaluerEquiteChargeTravail();
        double scoreVarieteCoursEnseignant = evaluerVarieteCoursEnseignant();
        this.fitness = (scoreConflitsHoraires + scoreRepartitionEquitable + scoreDisponibiliteEnseignants + scoreEquiteChargeTravail + scoreVarieteCoursEnseignant) / 5.0;
    }

    public void effectuerMutation() {

        if (emploiDuTemps != null && emploiDuTemps.size() > 1) {
            int indexCours1 = (int) (Math.random() * emploiDuTemps.size());
            int indexCours2 = (int) (Math.random() * emploiDuTemps.size());

            Collections.swap(new ArrayList<>(emploiDuTemps), indexCours1, indexCours2);
        }
    }

    public Individual croiserAvec(Individual autreIndividu) {

        if (emploiDuTemps != null && autreIndividu.getEmploiDuTemps() != null
                && emploiDuTemps.size() == autreIndividu.getEmploiDuTemps().size()) {

            int pointDeCroisement = (int) (Math.random() * emploiDuTemps.size());

            Individual nouvelIndividu = new Individual();
            nouvelIndividu.setEmploiDuTemps(new HashSet<>(croisementEnUnPoint(new ArrayList<>(emploiDuTemps), new ArrayList<>(autreIndividu.getEmploiDuTemps()), pointDeCroisement)));

            return nouvelIndividu;
        }
        return this;
    }

    private List<Cours> croisementEnUnPoint(List<Cours> emploiDuTemps1, List<Cours> emploiDuTemps2, int pointDeCroisement) {
        List<Cours> nouvelEmploiDuTemps = new ArrayList<>();

        nouvelEmploiDuTemps.addAll(emploiDuTemps1.subList(0, pointDeCroisement));

        nouvelEmploiDuTemps.addAll(emploiDuTemps2.subList(pointDeCroisement, emploiDuTemps2.size()));

        return nouvelEmploiDuTemps;
    }

    private double evaluerConflitsHoraires() {

        if (emploiDuTemps != null && !emploiDuTemps.isEmpty()) {
            List<Cours> coursTries = new ArrayList<>(emploiDuTemps);
            coursTries.sort(Comparator.comparing(cours -> cours.getHoraires().stream()
                    .findFirst().map(Horaire::getHeureDebut).orElse(null)));
            for (int i = 0; i < coursTries.size() - 1; i++) {
                Cours coursActuel = coursTries.get(i);
                Cours coursSuivant = coursTries.get(i + 1);
                if (coursActuel.getHoraires().stream().iterator().next().getHeureFin().isAfter(coursSuivant.getHoraires().stream()
                        .iterator().next().getHeureDebut())) {
                    return 0.0;
                }
            }
        }

        return 1.0;
    }


    private double evaluerRepartitionEquitable() {

        if (emploiDuTemps != null && !emploiDuTemps.isEmpty()) {

            Map<DayOfWeek, Integer> coursParJour = new HashMap<>();

            Arrays.stream(DayOfWeek.values()).forEach(jour -> coursParJour.put(jour, 0));

            for (Cours cours : emploiDuTemps) {
                DayOfWeek jourCours = cours.getHoraires().stream().iterator().next().getDayOfWeek();
                coursParJour.put(jourCours, coursParJour.get(jourCours) + 1);
            }

            int chargeMaximale = Collections.max(coursParJour.values());
            int chargeMinimale = Collections.min(coursParJour.values());
            int differenceCharge = chargeMaximale - chargeMinimale;
            double scoreNormalise = 1.0 - (double) differenceCharge / emploiDuTemps.size();
            return Math.max(0.0, scoreNormalise);
        }
        return 0.0;
    }



    private double evaluerDisponibiliteEnseignantsEtSalles() {
        // Vérifiez si chaque cours respecte à la fois la disponibilité de l'enseignant et de la salle
        double scoreTotal = 0.0;

        for (Cours cours : emploiDuTemps) {
            Enseignant enseignant = cours.getEnseignant();
            Salle salle = cours.getSalle();

            // Supposons qu'il y ait un seul horaire par cours
            Horaire horaire = cours.getHoraires().iterator().next();

            // Vérifiez si l'horaire du cours est dans les plages disponibles de l'enseignant et de la salle
            if (enseignant.getDisponibilites().contains(horaire) &&
                    salle.getHoraires().contains(horaire)) {
                scoreTotal += 1.0; // Le cours respecte la disponibilité de l'enseignant et de la salle
            }
        }

        // Calculez la moyenne des scores par rapport au nombre total de cours
        return scoreTotal / emploiDuTemps.size();
    }

    private double evaluerEquiteChargeTravail() {

        Map<Enseignant, Long> countParEnseignant = emploiDuTemps.stream()
                .collect(Collectors.groupingBy(Cours::getEnseignant, Collectors.counting()));

        long maxCharge = Collections.max(countParEnseignant.values());
        long minCharge = Collections.min(countParEnseignant.values());

        // Calculez la différence relative entre la charge maximale et minimale
        double differenceRelative = (double) (maxCharge - minCharge) / emploiDuTemps.size();

        // Retournez le score d'équité (plus la différence est faible, meilleur est le score)
        return 1.0 - differenceRelative;
    }

    private double evaluerVarieteCoursEnseignant() {

        Map<Enseignant, Set<TypeCours>> typesParEnseignant = emploiDuTemps.stream()
                .collect(Collectors.groupingBy(Cours::getEnseignant,
                        Collectors.mapping(Cours::getTypeCours, Collectors.toSet())));

        double moyenneVariete = typesParEnseignant.values().stream()
                .mapToInt(Set::size)
                .average()
                .orElse(0.0);

        return moyenneVariete / TypeCours.values().length;
    }


    public void muter() {

        for (Gene gene : genes) {
            if (shouldMutate()) {
                gene.muter();
            }
        }
    }

    private boolean shouldMutate() {

        return Math.random() < 0.1;
    }
}


