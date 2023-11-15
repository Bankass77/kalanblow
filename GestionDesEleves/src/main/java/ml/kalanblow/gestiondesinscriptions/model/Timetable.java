package ml.kalanblow.gestiondesinscriptions.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "emploidutemps")
public class Timetable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "timetable", fetch = FetchType.EAGER)
    private Set<ScheduleClass> classes ;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Salle> salles ;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Enseignant> enseignants ;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Cours> cours;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Group> groups ;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Timeslot> timeslots ;


    // Méthode pour ajouter une classe à l'emploi du temps
    public void addClass(ScheduleClass scheduleClass) {
        scheduleClass.setTimetable(this);
        classes.add(scheduleClass);
    }

    // Méthode pour ajouter une salle à l'emploi du temps
    public void addSalle(Salle salle) {
        salles.add(salle);
    }

    // Méthode pour ajouter un enseignant à l'emploi du temps
    public void addEnseignant(Enseignant enseignant) {
        enseignants.add(enseignant);
    }

    // Méthode pour ajouter un cours à l'emploi du temps
    public void addCours(Cours cours) {
        cours.addTimetable(this);
        this.cours.add(cours);
    }

    // Méthode pour ajouter un groupe à l'emploi du temps
    public void addGroup(Group group) {
        groups.add(group);
    }

    // Méthode pour ajouter un timeslot à l'emploi du temps
    public void addTimeslot(Timeslot timeslot) {
        timeslots.add(timeslot);
    }


    // Méthode pour créer les classes de l'emploi du temps à partir d'un individu
    public void createClasses(Individual individual) {
        Set<Cours> emploiDuTemps = individual.getEmploiDuTemps();

        for (Cours cours : emploiDuTemps) {
            Enseignant enseignant = cours.getEnseignant();
            Salle salle = cours.getSalle();
            Timeslot timeslot = cours.getTimeslot();
            Set<Group> groups = cours.getGroups();

            for (Group group : groups) {
                ScheduleClass scheduleClass = new ScheduleClass();
                scheduleClass.setEnseignant(enseignant);
                scheduleClass.setSalle(salle);
                scheduleClass.setTimeslot(timeslot);
                scheduleClass.setGroup(group);
                scheduleClass.setCours(cours);
                addClass(scheduleClass);
            }
        }
    }

    // Méthode pour calculer le nombre de conflits dans l'emploi du temps
    public int calcClashes() {
        int numClashes = 0;

        for (ScheduleClass scheduleClass : classes) {
            Enseignant enseignant = scheduleClass.getEnseignant();
            Salle salle = scheduleClass.getSalle();
            Timeslot timeslot = scheduleClass.getTimeslot();

            if (clashExists(enseignant, salle, timeslot)) {
                numClashes++;
            }
        }

        return numClashes;
    }

    // Méthode pour vérifier si un conflit existe pour une nouvelle classe
    private boolean clashExists(Enseignant newEnseignant, Salle newSalle, Timeslot newTimeslot) {
        for (ScheduleClass existingClass : classes) {
            Enseignant existingEnseignant = existingClass.getEnseignant();
            Salle existingSalle = existingClass.getSalle();
            Timeslot existingTimeslot = existingClass.getTimeslot();

            if (existingTimeslot.overlaps(newTimeslot)
                    && (existingEnseignant.equals(newEnseignant) || existingSalle.equals(newSalle))) {
                return true;
            }
        }

        return false;
    }
    public void addScheduleClass(ScheduleClass scheduleClass) {
        scheduleClass.setTimetable(this);
        this.classes.add(scheduleClass);
    }

}
