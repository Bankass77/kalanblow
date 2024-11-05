package ml.kalanblow.gestiondesinscriptions.service;

import ml.kalanblow.gestiondesinscriptions.model.Address;
import ml.kalanblow.gestiondesinscriptions.model.Email;
import ml.kalanblow.gestiondesinscriptions.model.Etablissement;
import ml.kalanblow.gestiondesinscriptions.model.PhoneNumber;

import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

public interface EtablissementService {

    // Créer un nouvel établissement
    Etablissement createEtablissement(Etablissement etablissement);

    // Mettre à jour un établissement existant
    Etablissement updateEtablissement(Long etablisementScolaireId, Etablissement etablissement);

    // Supprimer un établissement par ID
    void deleteEtablissement(Long etablisementScolaireId);

    /**
     * Trouve un établissement scolaire par son nom.
     *
     * @param nom Le nom de l'établissement scolaire à rechercher.
     * @return L'établissement scolaire correspondant au nom spécifié, s'il existe.
     */
    Optional <Etablissement> trouverEtablissementScolaireParSonNom(String nom);

    /**
     * Trouve un établissement scolaire par son identifiant unique.
     *
     * @param id L'identifiant de l'établissement scolaire à rechercher.
     * @return L'établissement scolaire correspondant à l'identifiant spécifié, s'il existe.
     */
    Optional <Etablissement> trouverEtablissementScolaireParSonIdentifiant(long id);

    /**
     * Trouve un établissement scolaire par son identifiant unique.
     *
     * @param identifiant L'identifiant de l'établissement scolaire à rechercher.
     * @return L'établissement scolaire correspondant à l'identifiant spécifié, s'il existe.
     */
    Optional<Etablissement> findEtablissementScolaireByIdentiantEtablissement (String identifiant);


    /**
     * Recherche un établissement scolaire par son adresse e-mail.
     *
     * @param email L'adresse e-mail associée à l'établissement à rechercher.
     * @return Une instance optionnelle contenant l'établissement scolaire correspondant à l'adresse e-mail, si elle existe.
     */
    Optional<Etablissement> findEtablissementScolaireByEmail(Email email);

    /**
     * Recherche un établissement scolaire par son numéro de téléphone.
     *
     * @param phoneNumber Le numéro de téléphone associé à l'établissement à rechercher.
     * @return Une instance optionnelle contenant l'établissement scolaire correspondant au numéro de téléphone, si il existe.
     */
    Optional<Etablissement> findEtablissementScolaireByPhoneNumber(PhoneNumber phoneNumber);

    /**
     * Recherche un établissement scolaire par son adresse postale.
     *
     * @param address L'adresse postale associée à l'établissement à rechercher.
     * @return Une instance optionnelle contenant l'établissement scolaire correspondant à l'adresse postale, si elle existe.
     */
    Optional<Etablissement> findEtablissementScolaireByAddress(Address address);
    public Etablissement uploadLogo(Long etablissementId, MultipartFile logoFile);
}
