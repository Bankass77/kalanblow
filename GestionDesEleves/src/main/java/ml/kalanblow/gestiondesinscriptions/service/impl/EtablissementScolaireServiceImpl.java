package ml.kalanblow.gestiondesinscriptions.service.impl;

import ml.kalanblow.gestiondesinscriptions.exception.EntityType;
import ml.kalanblow.gestiondesinscriptions.exception.ExceptionType;
import ml.kalanblow.gestiondesinscriptions.exception.KaladewnManagementException;
import ml.kalanblow.gestiondesinscriptions.model.*;
import ml.kalanblow.gestiondesinscriptions.repository.EtablissementRepository;
import ml.kalanblow.gestiondesinscriptions.request.CreateEtablissementScolaireParameters;
import ml.kalanblow.gestiondesinscriptions.request.EditEtablissementScolaireParameters;
import ml.kalanblow.gestiondesinscriptions.service.EtablissementScolaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@Transactional
public class EtablissementScolaireServiceImpl implements EtablissementScolaireService {

    private final EtablissementRepository etablissementScolaireRepository;

    @Autowired
    public EtablissementScolaireServiceImpl(EtablissementRepository etablissementScolaireRepository) {
        this.etablissementScolaireRepository = etablissementScolaireRepository;
    }

    /**
     * Crée un nouvel établissement scolaire en utilisant les paramètres fournis.
     *
     * @param createEtablissementScolaireParameters Les paramètres de création de l'établissement scolaire.
     * @return L'établissement scolaire créé et enregistré dans la base de données.
     */
    @Override
    public Etablissement creerEtablissementScolaire(CreateEtablissementScolaireParameters createEtablissementScolaireParameters) {
        return ajouterEtablissementScolaire(createEtablissementScolaireParameters);
    }

    /**
     * Met à jour un établissement scolaire existant en fonction de son identifiant et des paramètres de mise à jour.
     *
     * @param id                                  L'identifiant de l'établissement scolaire à mettre à jour.
     * @param editEtablissementScolaireParameters Les paramètres de mise à jour de l'établissement scolaire.
     * @return L'établissement scolaire mis à jour.
     */
    @Override
    public Etablissement miseAJourEtablissementScolaire(long id, EditEtablissementScolaireParameters editEtablissementScolaireParameters) {
        // Récupère l'établissement scolaire à partir de l'identifiant
        Etablissement etablissement = etablissementScolaireRepository.findByEtablisementScolaireId(id);

        // Vérifie si l'établissement scolaire existe
        if (etablissement != null) {
            // Mise à jour de l'établissement scolaire avec les paramètres de mise à jour
            editEtablissementScolaireParameters.updateEtablissementScolaire(etablissement);
        }

        return etablissement;
    }

    /**
     * Recherche un établissement scolaire par son nom.
     *
     * @param nom Le nom de l'établissement scolaire à rechercher.
     * @return L'établissement scolaire trouvé, ou null si aucun établissement correspondant n'est trouvé.
     */
    @Override
    public Etablissement trouverEtablissementScolaireParSonNom(String nom) {
        return etablissementScolaireRepository.findByNomEtablissement(nom);
    }

    /**
     * Recherche un établissement scolaire par son identifiant.
     *
     * @param id L'identifiant de l'établissement scolaire à rechercher.
     * @return L'établissement scolaire trouvé, ou null si aucun établissement correspondant n'est trouvé.
     */
    @Override
    public Etablissement trouverEtablissementScolaireParSonIdentifiant(long id) {
        return etablissementScolaireRepository.findByEtablisementScolaireId(id);
    }


    /**
     * Cette méthode permet d'ajouter une photo au profil d'un élève s'il est fourni dans les paramètres.
     *
     * @param parameters            Les paramètres de création de l'établissement scolaire.
     * @param etablissement L'élève auquel la photo doit être associée.
     * @throws KaladewnManagementException Si une erreur survient lors de la récupération ou de la sauvegarde de la photo.
     */
    private static void ajouterPhotoSiPresent(CreateEtablissementScolaireParameters parameters, Etablissement etablissement) {
        // Récupère le fichier de profil de l'élève depuis les paramètres.
        MultipartFile profileEleve = parameters.getAvatar();

        if (profileEleve != null) {
            try {
                // Convertit le contenu du fichier en tableau de bytes et l'associe à l'élève.
                etablissement.setAvatar(profileEleve.getBytes());
            } catch (IOException e) {
                // En cas d'erreur lors de la récupération des bytes du fichier, lance une exception personnalisée.
                throw new KaladewnManagementException().throwException(EntityType.ELEVE, ExceptionType.ENTITY_NOT_FOUND, e.getMessage());
            }
        }
    }


    /**
     * Crée un nouvel établissement scolaire en utilisant les paramètres fournis et l'ajoute à la base de données.
     *
     * @param createEtablissementScolaireParameters Les paramètres de création de l'établissement scolaire.
     * @return L'établissement scolaire créé et enregistré dans la base de données.
     */
    private Etablissement ajouterEtablissementScolaire(CreateEtablissementScolaireParameters createEtablissementScolaireParameters) {

        Etablissement etablissement = new Etablissement();
        etablissement.setNomEtablissement(createEtablissementScolaireParameters.getNomEtablissement());
        etablissement.setEmail(createEtablissementScolaireParameters.getEmail());
        etablissement.setAddress(createEtablissementScolaireParameters.getAddress());
        etablissement.setEleves(createEtablissementScolaireParameters.getEleves());
        etablissement.setAvatar(etablissement.getAvatar());
        etablissement.setEnseignants(createEtablissementScolaireParameters.getEnseignants());
        etablissement.setLastModifiedDate(createEtablissementScolaireParameters.getLastModifiedDate());
        etablissement.setCreatedDate(createEtablissementScolaireParameters.getCreatedDate());
        etablissement.setAddress(createEtablissementScolaireParameters.getAddress());
        etablissement.setSalles(createEtablissementScolaireParameters.getSalleDeClasses());
        ajouterPhotoSiPresent(createEtablissementScolaireParameters, etablissement);

        return etablissementScolaireRepository.save(etablissement);
    }

    /**
     * Recherche un établissement scolaire par son nom.
     *
     * @param nomEtablissement Le nom de l'établissement à rechercher.
     * @return L'établissement scolaire correspondant au nom spécifié, s'il existe.
     */
    @Override
    public Etablissement findByNomEtablissement(String nomEtablissement) {
        return null;
    }

    /**
     * Recherche un établissement scolaire par son adresse e-mail.
     *
     * @param email L'adresse e-mail associée à l'établissement à rechercher.
     * @return Une instance optionnelle contenant l'établissement scolaire correspondant à l'adresse e-mail, si elle existe.
     */
    @Override
    public Optional<Etablissement> findEtablissementScolaireByEmail(Email email) {
        return Optional.empty();
    }

    /**
     * Recherche un établissement scolaire par son numéro de téléphone.
     *
     * @param phoneNumber Le numéro de téléphone associé à l'établissement à rechercher.
     * @return Une instance optionnelle contenant l'établissement scolaire correspondant au numéro de téléphone, si il existe.
     */
    @Override
    public Optional<Etablissement> findEtablissementScolaireByPhoneNumber(PhoneNumber phoneNumber) {
        return Optional.empty();
    }

    /**
     * Recherche un établissement scolaire par son adresse postale.
     *
     * @param address L'adresse postale associée à l'établissement à rechercher.
     * @return Une instance optionnelle contenant l'établissement scolaire correspondant à l'adresse postale, si elle existe.
     */
    @Override
    public Optional<Etablissement> findEtablissementScolaireByAddress(Address address) {
        return Optional.empty();
    }
}
