package ml.kalanblow.gestiondesinscriptions.service;

import ml.kalanblow.gestiondesinscriptions.model.Timeslot;

import java.util.Optional;
import java.util.Set;

public interface TimeslotService {
    public Set<Timeslot> getAllTimeslots();

    public Optional<Timeslot> getTimeslotById(Long id);

    public Timeslot saveTimeslot(Timeslot timeslot);

    public void deleteTimeslot(Long id);
}
