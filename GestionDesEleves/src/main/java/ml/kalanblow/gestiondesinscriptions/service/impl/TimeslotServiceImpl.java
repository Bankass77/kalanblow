package ml.kalanblow.gestiondesinscriptions.service.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import ml.kalanblow.gestiondesinscriptions.model.Timeslot;
import ml.kalanblow.gestiondesinscriptions.repository.TimeslotRepository;
import ml.kalanblow.gestiondesinscriptions.service.TimeslotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Transactional
@Service
@Slf4j
public class TimeslotServiceImpl  implements TimeslotService {

    private final TimeslotRepository timeslotRepository;

    @Autowired
    public TimeslotServiceImpl(TimeslotRepository timeslotRepository) {
        this.timeslotRepository = timeslotRepository;
    }
    @Override
    public Set<Timeslot> getAllTimeslots() {
        return new HashSet<>(timeslotRepository.findAll());
    }

    @Override
    public Optional<Timeslot> getTimeslotById(Long id) {
        return timeslotRepository.findById(id);
    }

    @Override
    public Timeslot saveTimeslot(Timeslot timeslot) {
        return timeslotRepository.save(timeslot);
    }

    @Override
    public void deleteTimeslot(Long id) {
        timeslotRepository.deleteById(id);
    }
}
