package ml.kalanblow.gestiondescours.service.impl;

import java.util.List;
import java.util.Optional;

import javax.management.openmbean.KeyAlreadyExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import ml.kalanblow.gestiondescours.exception.EntityType;
import ml.kalanblow.gestiondescours.exception.ExceptionType;
import ml.kalanblow.gestiondescours.exception.KaladewnManagementException;
import ml.kalanblow.gestiondescours.model.Cours;
import ml.kalanblow.gestiondescours.model.Salle;
import ml.kalanblow.gestiondescours.service.SalleService;

@Slf4j
@Service
@Transactional
public class SalleServiceImpl implements SalleService {

    private final SalleService salleService;

    @Autowired
    public SalleServiceImpl(@Lazy final SalleService salleService) {
        this.salleService = salleService;
    }

    /**
     * @param cours
     * @return
     */
    @Override
    public List<Salle> findAllSalleByCours(final Cours cours) {

        try {
            return salleService.findAllSalleByCours(cours);
        } catch (Exception e) {

            throw KaladewnManagementException.throwExceptionWithId(EntityType.SALLE, ExceptionType.ENTITY_NOT_FOUND, e.getMessage());
        }
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Optional<Salle> findSalleBy(final long id) {
        return salleService.findSalleBy(id);
    }

    /**
     * @param salle
     * @return
     */
    @Override
    public Salle createSalle(final Salle salle) {
        return salleService.createSalle(salle);
    }

    /**
     * @param id
     * @param salle
     * @return
     */
    @Override
    public Salle updateSalle(final long id, final Salle salle) {

        Optional<Salle> salleOptional = findSalleBy(id);
        if (salleOptional.isPresent()) {

            salleOptional.get().setNomSalle(salle.getNomSalle());
            salleOptional.get().setTypeSalle(salle.getTypeSalle());
            salleOptional.get().setBatiment(salle.getBatiment());
            salleOptional.get().setCours(salle.getCours());
            salleOptional.get().setCapacite(salle.getCapacite());
            salleOptional.get().setEquipementSpecial(salle.isEquipementSpecial());
        }
        return salleOptional.get();
    }
}
