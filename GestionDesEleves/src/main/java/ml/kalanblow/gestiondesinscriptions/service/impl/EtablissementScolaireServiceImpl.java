package ml.kalanblow.gestiondesinscriptions.service.impl;

import ml.kalanblow.gestiondesinscriptions.exception.EntityType;
import ml.kalanblow.gestiondesinscriptions.exception.ExceptionType;
import ml.kalanblow.gestiondesinscriptions.exception.KaladewnManagementException;
import ml.kalanblow.gestiondesinscriptions.model.Eleve;
import ml.kalanblow.gestiondesinscriptions.model.EtablissementScolaire;
import ml.kalanblow.gestiondesinscriptions.repository.EtablissementScolaireRepository;
import ml.kalanblow.gestiondesinscriptions.request.CreateEleveParameters;
import ml.kalanblow.gestiondesinscriptions.request.CreateEtablissementScolaireParameters;
import ml.kalanblow.gestiondesinscriptions.request.EditEtablissementScolaireParameters;
import ml.kalanblow.gestiondesinscriptions.service.EtablissementScolaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class EtablissementScolaireServiceImpl implements EtablissementScolaireService {

    private final EtablissementScolaireRepository etablissementScolaireRepository;

    @Autowired
    public EtablissementScolaireServiceImpl(EtablissementScolaireRepository etablissementScolaireRepository) {
        this.etablissementScolaireRepository = etablissementScolaireRepository;
    }

    /**
     * Crée un nouvel établissement scolaire en utilisant les paramètres fournis.
     *
     * @param createEtablissementScolaireParameters Les paramètres de création de l'établissement scolaire.
     * @return L'établissement scolaire créé et enregistré dans la base de données.
     */
    @Override
    public EtablissementScolaire creerEtablissementScolaire(CreateEtablissementScolaireParameters createEtablissementScolaireParameters) {
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
    public EtablissementScolaire miseAJourEtablissementScolaire(long id, EditEtablissementScolaireParameters editEtablissementScolaireParameters) {
        // Récupère l'établissement scolaire à partir de l'identifiant
        EtablissementScolaire etablissementScolaire = etablissementScolaireRepository.findByEtablisementScolaireId(id);

        // Vérifie si l'établissement scolaire existe
        if (etablissementScolaire != null) {
            // Mise à jour de l'établissement scolaire avec les paramètres de mise à jour
            editEtablissementScolaireParameters.updateEtablissementScolaire(etablissementScolaire);
        }

        return etablissementScolaire;
    }

    /**
     * Recherche un établissement scolaire par son nom.
     *
     * @param nom Le nom de l'établissement scolaire à rechercher.
     * @return L'établissement scolaire trouvé, ou null si aucun établissement correspondant n'est trouvé.
     */
    @Override
    public EtablissementScolaire trouverEtablissementScolaireParSonNom(String nom) {
        return etablissementScolaireRepository.findByNomEtablissement(nom);
    }

    /**
     * Recherche un établissement scolaire par son identifiant.
     *
     * @param id L'identifiant de l'établissement scolaire à rechercher.
     * @return L'établissement scolaire trouvé, ou null si aucun établissement correspondant n'est trouvé.
     */
    @Override
    public EtablissementScolaire trouverEtablissementScolaireParSonIdentifiant(long id) {
        return etablissementScolaireRepository.findByEtablisementScolaireId(id);
    }


    /**
     * Cette méthode permet d'ajouter une photo au profil d'un élève s'il est fourni dans les paramètres.
     *
     * @param parameters            Les paramètres de création de l'établissement scolaire.
     * @param etablissementScolaire L'élève auquel la photo doit être associée.
     * @throws KaladewnManagementException Si une erreur survient lors de la récupération ou de la sauvegarde de la photo.
     */
    private static void ajouterPhotoSiPresent(CreateEtablissementScolaireParameters parameters, EtablissementScolaire etablissementScolaire) {
        // Récupère le fichier de profil de l'élève depuis les paramètres.
        MultipartFile profileEleve = parameters.getAvatar();

        if (profileEleve != null) {
            try {
                // Convertit le contenu du fichier en tableau de bytes et l'associe à l'élève.
                etablissementScolaire.setAvatar(profileEleve.getBytes());
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
    private EtablissementScolaire ajouterEtablissementScolaire(CreateEtablissementScolaireParameters createEtablissementScolaireParameters) {

        EtablissementScolaire etablissementScolaire = new EtablissementScolaire();
        etablissementScolaire.setNomEtablissement(createEtablissementScolaireParameters.getNomEtablissement());
        etablissementScolaire.setEmail(createEtablissementScolaireParameters.getEmail());
        etablissementScolaire.setAddress(createEtablissementScolaireParameters.getAddress());
        etablissementScolaire.setEleves(createEtablissementScolaireParameters.getEleves());
        etablissementScolaire.setAvatar(etablissementScolaire.getAvatar());
        etablissementScolaire.setEnseignants(createEtablissementScolaireParameters.getEnseignants());
        etablissementScolaire.setLastModifiedDate(createEtablissementScolaireParameters.getLastModifiedDate());
        etablissementScolaire.setCreatedDate(createEtablissementScolaireParameters.getCreatedDate());
        etablissementScolaire.setAddress(createEtablissementScolaireParameters.getAddress());
        etablissementScolaire.setSalleDeClasses(createEtablissementScolaireParameters.getSalleDeClasses());
        ajouterPhotoSiPresent(createEtablissementScolaireParameters, etablissementScolaire);

        return etablissementScolaireRepository.save(etablissementScolaire);
    }

}
