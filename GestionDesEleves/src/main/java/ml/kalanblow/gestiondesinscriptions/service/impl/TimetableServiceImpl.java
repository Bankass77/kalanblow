package ml.kalanblow.gestiondesinscriptions.service.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import ml.kalanblow.gestiondesinscriptions.model.*;
import ml.kalanblow.gestiondesinscriptions.repository.TimetableRepository;
import ml.kalanblow.gestiondesinscriptions.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@Service
@Transactional
@Slf4j
public class TimetableServiceImpl implements TimetableService {

    private final TimetableRepository timetableRepository;

    private final EnseignantService enseignantService;

    private final CoursService coursService;

    private final SalleService salleService;

    private final ScheduleClassService scheduleClassService;

    private final GroupService groupService;

    private final TimeslotService timeslotService;

    @Autowired
    public TimetableServiceImpl(TimetableRepository timetableRepository, EnseignantService enseignantService, SalleService salleService, CoursService coursService, ScheduleClassService scheduleClassService, TimeslotService timeslotService, GroupService groupService) {
        this.timetableRepository = timetableRepository;
        this.enseignantService = enseignantService;
        this.salleService = salleService;
        this.coursService = coursService;
        this.scheduleClassService = scheduleClassService;
        this.timeslotService = timeslotService;
        this.groupService = groupService;
    }

    @Override
    public Timetable initializeTimetable() {

        Set<Enseignant> enseignants = enseignantService.getAllEnseignants();

        Set<Salle> salles = salleService.getAllSalles();

        Set<Cours> cours = coursService.getAllCours();

        Set<Group> groups = groupService.getAllGroups();
        Set<Timeslot> timeslots = timeslotService.getAllTimeslots();

        Set<ScheduleClass> scheduleClasses = scheduleClassService.getAllScheduleClasses();
        Timetable timetable = new Timetable();
        timetable.setEnseignants(enseignants);
        timetable.setSalles(salles);
        timetable.setCours(cours);
        timetable.setClasses(scheduleClasses);
        timetable.setGroups(groups);
        timetable.setTimeslots(timeslots);

        timetableRepository.save(timetable);

        return timetable;
    }

    @Override
    public Timetable getTimetableById(Long id) {
        return timetableRepository.getReferenceById(id);
    }

    @Override
    public List<Timetable> getAllTimetables() {
        return timetableRepository.findAll();
    }

    @Override
    public void saveTimetable(Timetable existingTimetable) {
        initializeTimetable();
    }

    @Override
    public void deleteTimetableById(Long id) {


        timetableRepository.deleteById(id);
    }
}
