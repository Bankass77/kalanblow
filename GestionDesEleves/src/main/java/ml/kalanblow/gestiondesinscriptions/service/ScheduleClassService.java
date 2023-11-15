package ml.kalanblow.gestiondesinscriptions.service;

import ml.kalanblow.gestiondesinscriptions.model.ScheduleClass;

import java.util.Optional;
import java.util.Set;

public interface ScheduleClassService {

    public Set<ScheduleClass> getAllScheduleClasses();

    public Optional<ScheduleClass> getScheduleClassById(Long id);
    public ScheduleClass saveScheduleClass(ScheduleClass scheduleClass);

    public void deleteScheduleClass(Long id);
}
