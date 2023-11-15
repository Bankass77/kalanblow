package ml.kalanblow.gestiondesinscriptions.model;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled
public class TimetableTest {

    @Test
    public void testCreateClassesAndCalcClashes() {
        // Initialisation de Timetable et autres entités nécessaires
        Timetable timetable = initializeTimetable();
        Individual individual = createSampleIndividual();

        // Création des classes
        timetable.createClasses(individual);

        // Vérification du nombre de classes créées
        assertEquals(12, timetable.getClasses().size());

        // Calcul des conflits dans l'emploi du temps
        int numClashes = timetable.calcClashes();

        // Vérification du nombre de conflits (peut être différent en fonction des données)
        assertEquals(2, numClashes);
    }

    @Test
    void testAddClass() {
        Timetable timetable = initializeTimetable();
        ScheduleClass scheduleClass = createSampleClass(); // Créez une ScheduleClass de test
        timetable.addClass(scheduleClass);

        // Vérifiez que la classe a été correctement ajoutée à l'emploi du temps
        assertEquals(1, timetable.getClasses().size());

    }

    // Méthode pour initialiser une instance de Timetable
    private Timetable initializeTimetable() {
        // Initialisation des structures de données (salles, enseignants, etc.)
        Salle salle1 = new Salle();
        Salle salle2 = new Salle();
        Enseignant enseignant1 = new Enseignant();
        Enseignant enseignant2 = new Enseignant();
        Cours cours1 = new Cours();
        Cours cours2 = new Cours();
        Group group1 = new Group();
        Group group2 = new Group();
        Timeslot timeslot1 = new Timeslot();
        Timeslot timeslot2 = new Timeslot();

        // Création d'une instance de Timetable
        Timetable timetable = new Timetable();

        // Ajout des éléments à la Timetable
        timetable.addSalle(salle1);
        timetable.addSalle(salle2);
        timetable.addEnseignant(enseignant1);
        timetable.addEnseignant(enseignant2);
        timetable.addCours(cours1);
        timetable.addCours(cours2);
        timetable.addGroup(group1);
        timetable.addGroup(group2);
        timetable.addTimeslot(timeslot1);
        timetable.addTimeslot(timeslot2);

        return timetable;
    }

    // Méthode pour créer un exemple d'individu avec un emploi du temps
    private Individual createSampleIndividual() {
        // Créez un ensemble de cours pour l'individu
        Set<Cours> emploiDuTemps = new HashSet<>();
        // Ajoutez des cours à l'emploi du temps
        Cours cours1 =new Cours();
        Cours cours2 = new Cours();

        emploiDuTemps.add(cours1);
        emploiDuTemps.add(cours2);

        // Créez et configurez un individu avec l'emploi du temps
        Individual individual = new Individual();
        individual.setEmploiDuTemps(emploiDuTemps);

        return individual;
    }

    private ScheduleClass createSampleClass() {
        Enseignant enseignant = new Enseignant() ;
        Salle salle = new Salle();
        Timeslot timeslot = new Timeslot();
        Group group = new Group();
        Cours cours =new Cours();
        ScheduleClass scheduleClass = new ScheduleClass();
        scheduleClass.setEnseignant(enseignant);
        scheduleClass.setSalle(salle);
        scheduleClass.setTimeslot(timeslot);
        scheduleClass.setGroup(group);
        scheduleClass.setCours(cours);

        return scheduleClass;
    }

}
