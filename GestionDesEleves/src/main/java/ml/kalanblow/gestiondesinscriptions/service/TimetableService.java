package ml.kalanblow.gestiondesinscriptions.service;

import ml.kalanblow.gestiondesinscriptions.model.Timetable;

import java.util.List;

public interface TimetableService {
    Timetable initializeTimetable();

    Timetable getTimetableById(Long id);

    List<Timetable> getAllTimetables();

    void saveTimetable(Timetable existingTimetable);

    void deleteTimetableById(Long id);
}
