package ml.kalanblow.gestiondescours.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
    public SalleServiceImpl(final SalleService salleService) {
        this.salleService = salleService;
    }

    /**
     * @param cours à retourner
     * @return une Liste de Salle , sinon lève une exception si la liste est vide
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
     * @param id de la Salle
     * @return une salle
     */
    @Override
    public Optional<Salle> findSalleBy(final long id) {
        return salleService.findSalleBy(id);
    }

    /**
     * @param salle de cours
     * @return une nouvele Salle crée
     */
    @Override
    public Salle createSalle(final Salle salle) {
        return salleService.createSalle(salle);
    }

    /**
     * @param id de la Salle à mettre jour
     * @param salle à mettre à jour
     * @return une Sallle mise à jour
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
