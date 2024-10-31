package ml.kalanblow.gestiondesinscriptions.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import ml.kalanblow.gestiondesinscriptions.exception.EntityType;
import ml.kalanblow.gestiondesinscriptions.exception.ExceptionType;
import ml.kalanblow.gestiondesinscriptions.exception.KaladewnManagementException;
import ml.kalanblow.gestiondesinscriptions.model.Address;
import ml.kalanblow.gestiondesinscriptions.model.Email;
import ml.kalanblow.gestiondesinscriptions.model.Etablissement;
import ml.kalanblow.gestiondesinscriptions.model.PhoneNumber;
import ml.kalanblow.gestiondesinscriptions.repository.EtablissementRepository;
import ml.kalanblow.gestiondesinscriptions.service.EtablissementService;

@Service
@Slf4j
public class EtablissementServiceImpl implements EtablissementService {

    private final EtablissementRepository etablissementRepository;

    private final KaladewnManagementException kaladewnManagementException;

    @Autowired
    public EtablissementServiceImpl(final EtablissementRepository etablissementRepository, KaladewnManagementException kaladewnManagementException) {
        this.kaladewnManagementException= kaladewnManagementException;
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

        Etablissement etab = Optional.ofNullable(etablissementRepository.findByEtablisementScolaireId(etablisementScolaireId)).orElseThrow(() -> kaladewnManagementException.throwExceptionWithId(EntityType.ETABLISSEMENTSCOLAIRE, ExceptionType.ENTITY_EXCEPTION, "Etablissement non trouvé avec cet id :" + etablisementScolaireId));

        if (etab != null) {

            etab.setAddress(etablissement.getAddress());
            etab.setNomEtablissement(etablissement.getNomEtablissement());
            etab.setEleves(etablissement.getEleves());
            etab.setEtablissementEmail(etablissement.getEtablissementEmail());
            etab.setCreatedDate(etablissement.getCreatedDate());
            etab.setEnseignants(etablissement.getEnseignants());
            etab.setLastModifiedDate(etablissement.getLastModifiedDate());
            etab.setPhoneNumber(etablissement.getPhoneNumber());
            etab.setVersion(etablissement.getVersion());
            etab.setLogo(etablissement.getLogo());
            etab.setClasses(etablissement.getClasses());
            etab.setChefEtablissement(etablissement.getChefEtablissement());

        }
        return etab;
    }

    /**
     * @param etablisementScolaireId
     */
    @Override
    public void deleteEtablissement(final Long etablisementScolaireId) {

        Optional<Etablissement> etablissement = Optional.ofNullable(Optional.ofNullable(etablissementRepository
                .findByEtablisementScolaireId(etablisementScolaireId)).orElseThrow(
                () -> kaladewnManagementException.throwExceptionWithId(EntityType.ETABLISSEMENTSCOLAIRE, ExceptionType.ENTITY_EXCEPTION,
                        " Etablissement non trouvé :" + etablisementScolaireId)));
        etablissementRepository.delete(etablissement.get());
    }

    /**
     * @param nom Le nom de l'établissement scolaire à rechercher.
     * @return
     */
    @Override
    public Etablissement trouverEtablissementScolaireParSonNom(final String nom) {

        if (!nom.isBlank() || !nom.isEmpty()) {
            return etablissementRepository.findByNomEtablissement(nom);
        } else {

            throw kaladewnManagementException.throwException(EntityType.ETABLISSEMENTSCOLAIRE, ExceptionType.ENTITY_EXCEPTION,
                    " Etablissement non trouvé avec cet nom  :" + nom);
        }

    }

    /**
     * @param id L'identifiant de l'établissement scolaire à rechercher.
     * @return
     */
    @Override
    public Etablissement trouverEtablissementScolaireParSonIdentifiant(final long id) {

        return etablissementRepository.findByEtablisementScolaireId(id);
    }

    /**
     * @param identifiant L'identifiant de l'établissement scolaire à rechercher.
     * @return
     */
    @Override
    public Optional<Etablissement> findEtablissementScolaireByIdentiantEtablissement(final String identifiant) {
        Optional<Etablissement> etablissement = etablissementRepository.findEtablissementScolaireByIdentiantEtablissement(identifiant);
        try {
            if (etablissement.isPresent()) {

                return etablissement;
            }
        } catch (Exception e) {
            throw kaladewnManagementException.throwExceptionWithTemplate(EntityType.ETABLISSEMENTSCOLAIRE, ExceptionType.ENTITY_NOT_FOUND, "etablissement", e.getMessage());
        }
        return Optional.empty();
    }

    /**
     * @param email L'adresse e-mail associée à l'établissement à rechercher.
     * @return
     */
    @Override
    public Optional<Etablissement> findEtablissementScolaireByEmail(final Email email) {

        if (!email.getEmail().isEmpty()) {

            return etablissementRepository.findEtablissementScolaireByEtablissementEmail(email);
        } else {

            throw kaladewnManagementException.throwException(EntityType.EMAIL, ExceptionType.ENTITY_EXCEPTION, "Email n'est pas trouvé : " + email.getEmail());
        }

    }

    /**
     * @param phoneNumber Le numéro de téléphone associé à l'établissement à rechercher.
     * @return
     */
    @Override
    public Optional<Etablissement> findEtablissementScolaireByPhoneNumber(final PhoneNumber phoneNumber) {

        if (!phoneNumber.asString().isEmpty()) {
            return etablissementRepository.findEtablissementScolaireByPhoneNumber(phoneNumber);
        } else {
            throw kaladewnManagementException.throwException(EntityType.PHONENUMBER, ExceptionType.ENTITY_EXCEPTION, "Email n'est pas trouvé : " + phoneNumber);
        }

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

        try {
            Etablissement etablissement = Optional.ofNullable(etablissementRepository
                    .findByEtablisementScolaireId(etablissementId)).orElseThrow(() ->
                    kaladewnManagementException.throwExceptionWithId(EntityType.ETABLISSEMENTSCOLAIRE,
                            ExceptionType.ENTITY_NOT_FOUND, " " + etablissementId));

            //Lire l'image et la convertir en tableau d'octets
            byte[] logBytes = logoFile.getBytes();
            etablissement.setLogo(logBytes);
            return etablissementRepository.save(etablissement);
        } catch (Exception e) {
            throw kaladewnManagementException.throwExceptionWithId(EntityType.ETABLISSEMENTSCOLAIRE,
                    ExceptionType.ENTITY_NOT_FOUND, " " + etablissementId);
        }
    }

}
