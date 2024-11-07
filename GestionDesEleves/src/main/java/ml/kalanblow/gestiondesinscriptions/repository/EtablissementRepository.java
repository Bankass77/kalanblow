package ml.kalanblow.gestiondesinscriptions.repository;

import ml.kalanblow.gestiondesinscriptions.model.Address;
import ml.kalanblow.gestiondesinscriptions.model.Email;
import ml.kalanblow.gestiondesinscriptions.model.Etablissement;
import ml.kalanblow.gestiondesinscriptions.model.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Interface pour la gestion des opérations de persistance des établissements scolaires.
 * Cette interface étend JpaRepository pour fournir des fonctionnalités de base de persistance,
 * ainsi que JpaSpecificationExecutor pour permettre les requêtes avancées basées sur des spécifications.
 *
 * @see org.springframework.data.jpa.repository.JpaRepository
 * @see org.springframework.data.jpa.repository.JpaSpecificationExecutor
 */
@Repository
public interface EtablissementRepository extends JpaRepository<Etablissement, JpaSpecificationExecutor<Etablissement>> {

    /**
     * Recherche un établissement scolaire par son nom.
     *
     * @param nomEtablissement Le nom de l'établissement à rechercher.
     * @return L'établissement scolaire correspondant au nom spécifié, s'il existe.
     */
   Optional <Etablissement> findEtablissementByNomEtablissement(String nomEtablissement);

    /**
     * Recherche un établissement scolaire par son identifiant unique.
     *
     * @param id L'identifiant de l'établissement à rechercher.
     * @return L'établissement scolaire correspondant à l'identifiant spécifié, s'il existe.
     */
    Optional<Etablissement> findByEtablisementScolaireId(long id);

    /**
     * Recherche un établissement scolaire par son adresse e-mail.
     *
     * @param email L'adresse e-mail associée à l'établissement à rechercher.
     * @return Une instance optionnelle contenant l'établissement scolaire correspondant à l'adresse e-mail, si elle existe.
     */
    Optional<Etablissement> findEtablissementScolaireByEtablissementEmail(Email email);

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

    Optional<Etablissement> findEtablissementScolaireByIdentiantEtablissement(String identiantEtablissement);

}
