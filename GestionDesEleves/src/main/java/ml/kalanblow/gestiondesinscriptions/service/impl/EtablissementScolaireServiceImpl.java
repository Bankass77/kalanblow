package ml.kalanblow.gestiondesinscriptions.service.impl;

import ml.kalanblow.gestiondesinscriptions.model.EtablissementScolaire;
import ml.kalanblow.gestiondesinscriptions.repository.EtablissementScolaireRepository;
import ml.kalanblow.gestiondesinscriptions.service.EtablissementScolaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EtablissementScolaireServiceImpl implements EtablissementScolaireService {

    private final EtablissementScolaireRepository etablissementScolaireRepository;

    @Autowired
    public EtablissementScolaireServiceImpl(EtablissementScolaireRepository etablissementScolaireRepository) {
        this.etablissementScolaireRepository = etablissementScolaireRepository;
    }

    /**
     * @param etablissementScolaire 
     * @return
     */
    @Override
    public EtablissementScolaire creerEtablissementScolaire(EtablissementScolaire etablissementScolaire) {
        return etablissementScolaireRepository.save(etablissementScolaire);
    }

    /**
     * @param id 
     * @param etablissementScolaire
     * @return
     */
    @Override
    public EtablissementScolaire miseAJourEtablissementScolaire(long id, EtablissementScolaire etablissementScolaire) {
        etablissementScolaire= etablissementScolaireRepository.findByEtablisementScolaireId(id);

        return etablissementScolaire;
    }

    /**
     * @param nom 
     * @return
     */
    @Override
    public EtablissementScolaire trouverEtablissementScolaireParSonNom(String nom) {
        return etablissementScolaireRepository.findByNomEtablissement(nom);
    }

    @Override
    public EtablissementScolaire trouverEtablissementScolaireParSonIdentifiant(long id) {
        return etablissementScolaireRepository.findByEtablisementScolaireId(id);
    }
}
