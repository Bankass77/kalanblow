package ml.kalanblow.gestiondesinscriptions.service.impl;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import ml.kalanblow.gestiondesinscriptions.exception.EntityNotFoundException;
import ml.kalanblow.gestiondesinscriptions.model.Address;
import ml.kalanblow.gestiondesinscriptions.model.Email;
import ml.kalanblow.gestiondesinscriptions.model.Etablissement;
import ml.kalanblow.gestiondesinscriptions.model.PhoneNumber;
import ml.kalanblow.gestiondesinscriptions.repository.EtablissementRepository;
import ml.kalanblow.gestiondesinscriptions.service.EtablissementService;

@Service
@Slf4j
@Transactional
public class EtablissementServiceImpl implements EtablissementService {

    private final EtablissementRepository etablissementRepository;

    @Autowired
    public EtablissementServiceImpl(final EtablissementRepository etablissementRepository) {
        this.etablissementRepository = etablissementRepository;
    }

    /**
     * @param etablissement
     * @return
     */
    @Override
    public Etablissement createEtablissement(final Etablissement etablissement) {
        return etablissementRepository.save(etablissement);
    }

    /**
     * @param etablisementScolaireId
     * @param etablissement
     * @return
     */
    @Override
    public Etablissement updateEtablissement(final Long etablisementScolaireId, final Etablissement etablissement) {

        Optional <Etablissement> etab = Optional.ofNullable(etablissementRepository.findByEtablisementScolaireId(etablisementScolaireId))
                .orElseThrow(() -> new EntityNotFoundException(etablisementScolaireId, Etablissement.class));

        if (etab.isPresent()) {

            etab.get().setAddress(etablissement.getAddress());
            etab.get().setNomEtablissement(etablissement.getNomEtablissement());
            etab.get().setEleves(etablissement.getEleves());
            etab.get().setEtablissementEmail(etablissement.getEtablissementEmail());
            etab.get().setCreatedDate(etablissement.getCreatedDate());
            etab.get().setEnseignants(etablissement.getEnseignants());
            etab.get().setLastModifiedDate(etablissement.getLastModifiedDate());
            etab.get().setPhoneNumber(etablissement.getPhoneNumber());
            etab.get().setLogo(etablissement.getLogo());
            etab.get().setClasses(etablissement.getClasses());
            etab.get().setChefEtablissement(etablissement.getChefEtablissement());

        }

        return etablissementRepository.save(etab.get());
    }

    /**
     * @param etablisementScolaireId
     */
    @Override
    public void deleteEtablissement(final Long etablisementScolaireId) {

        Optional<Etablissement> etablissement = Optional.ofNullable(etablissementRepository
                .findByEtablisementScolaireId(etablisementScolaireId)).orElseThrow(
                () -> new EntityNotFoundException(etablisementScolaireId, Etablissement.class));
        etablissementRepository.delete(etablissement.get());
    }

    /**
     * @param nom Le nom de l'établissement scolaire à rechercher.
     * @return
     */
    @Override
    public Optional <Etablissement> trouverEtablissementScolaireParSonNom(final String nom) {

            return etablissementRepository.findEtablissementByNomEtablissement(nom);
    }

    /**
     * @param id L'identifiant de l'établissement scolaire à rechercher.
     * @return
     */
    @Override
    public Optional <Etablissement> trouverEtablissementScolaireParSonIdentifiant(final long id) {

        return etablissementRepository.findByEtablisementScolaireId(id);
    }

    /**
     * @param identifiant L'identifiant de l'établissement scolaire à rechercher.
     * @return
     */
    @Override
    public Optional<Etablissement> findEtablissementScolaireByIdentiantEtablissement(final String identifiant) {

        return etablissementRepository.findEtablissementScolaireByIdentiantEtablissement(identifiant);
    }

    /**
     * @param email L'adresse e-mail associée à l'établissement à rechercher.
     * @return
     */
    @Override
    public Optional<Etablissement> findEtablissementScolaireByEmail(final Email email) {

        return etablissementRepository.findEtablissementScolaireByEtablissementEmail(email);

    }

    /**
     * @param phoneNumber Le numéro de téléphone associé à l'établissement à rechercher.
     * @return
     */
    @Override
    public Optional<Etablissement> findEtablissementScolaireByPhoneNumber(final PhoneNumber phoneNumber) {

        return etablissementRepository.findEtablissementScolaireByPhoneNumber(phoneNumber);
    }

    /**
     * @param address L'adresse postale associée à l'établissement à rechercher.
     * @return Etablissement
     */
    @Override
    public Optional<Etablissement> findEtablissementScolaireByAddress(final Address address) {

        return etablissementRepository.findEtablissementScolaireByAddress(address);
    }

    /**
     * @param etablissementId
     * @param logoFile
     * @return
     */
    @Override
    public Etablissement uploadLogo(final Long etablissementId, final MultipartFile logoFile) {

        Optional<Etablissement> etablissement = Optional.ofNullable(etablissementRepository
                .findByEtablisementScolaireId(etablissementId).orElseThrow(() ->
                        new EntityNotFoundException(etablissementId, Etablissement.class)));

        //Lire l'image et la convertir en tableau d'octets
        byte[] logBytes = null;
        try {
            logBytes = logoFile.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        etablissement.get().setLogo(logBytes);
        return etablissementRepository.save(etablissement.get());

    }

}
