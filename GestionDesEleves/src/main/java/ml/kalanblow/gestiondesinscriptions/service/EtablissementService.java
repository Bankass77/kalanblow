package ml.kalanblow.gestiondesinscriptions.service;

import ml.kalanblow.gestiondesinscriptions.model.Address;
import ml.kalanblow.gestiondesinscriptions.model.Email;
import ml.kalanblow.gestiondesinscriptions.model.Etablissement;
import ml.kalanblow.gestiondesinscriptions.model.PhoneNumber;
import ml.kalanblow.gestiondesinscriptions.request.CreateEtablissementParameters;
import ml.kalanblow.gestiondesinscriptions.request.EditEtablissementParameters;

import java.util.Optional;

public interface EtablissementService {

    /**
     * Crée un nouvel établissement scolaire en utilisant les paramètres fournis.
     *
     * @param createEtablissementScolaireParameters Les paramètres pour la création de l'établissement scolaire.
     * @return L'établissement scolaire créé.
     */
    Etablissement creerEtablissementScolaire(CreateEtablissementParameters createEtablissementScolaireParameters);

    /**
     * Met à jour un établissement scolaire existant en utilisant son identifiant et les paramètres fournis.
     *
     * @param id L'identifiant de l'établissement scolaire à mettre à jour.
     * @param editEtablissementScolaireParameters Les paramètres pour la mise à jour de l'établissement scolaire.
     * @return L'établissement scolaire mis à jour.
     */
    Etablissement miseAJourEtablissementScolaire(long id, EditEtablissementParameters editEtablissementScolaireParameters);

    /**
     * Trouve un établissement scolaire par son nom.
     *
     * @param nom Le nom de l'établissement scolaire à rechercher.
     * @return L'établissement scolaire correspondant au nom spécifié, s'il existe.
     */
    Etablissement trouverEtablissementScolaireParSonNom(String nom);

    /**
     * Trouve un établissement scolaire par son identifiant unique.
     *
     * @param id L'identifiant de l'établissement scolaire à rechercher.
     * @return L'établissement scolaire correspondant à l'identifiant spécifié, s'il existe.
     */
    Etablissement trouverEtablissementScolaireParSonIdentifiant(long id);


    /**
     * Recherche un établissement scolaire par son nom.
     *
     * @param nomEtablissement Le nom de l'établissement à rechercher.
     * @return L'établissement scolaire correspondant au nom spécifié, s'il existe.
     */
    Etablissement findByNomEtablissement(String nomEtablissement);


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

}
