package ml.kalanblow.gestiondesinscriptions.service.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import ml.kalanblow.gestiondesinscriptions.model.ScheduleClass;
import ml.kalanblow.gestiondesinscriptions.repository.ScheduleClassRepository;
import ml.kalanblow.gestiondesinscriptions.service.ScheduleClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@Slf4j
public class ScheduleClassServiceImpl  implements ScheduleClassService {
    private final ScheduleClassRepository scheduleClassRepository;

    @Autowired
    public ScheduleClassServiceImpl(ScheduleClassRepository scheduleClassRepository) {
        this.scheduleClassRepository = scheduleClassRepository;
    }

    @Override
    public Set<ScheduleClass> getAllScheduleClasses() {
        return new HashSet<>(scheduleClassRepository.findAll());
    }

    @Override
    public Optional<ScheduleClass> getScheduleClassById(Long id) {
        return scheduleClassRepository.findById(id);
    }

    @Override
    public ScheduleClass saveScheduleClass(ScheduleClass scheduleClass) {
        return scheduleClassRepository.save(scheduleClass);
    }

    @Override
    public void deleteScheduleClass(Long id) {
        scheduleClassRepository.deleteById(id);
    }

}
