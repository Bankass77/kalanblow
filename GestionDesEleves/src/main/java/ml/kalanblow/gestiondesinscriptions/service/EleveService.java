package ml.kalanblow.gestiondesinscriptions.service;


import ml.kalanblow.gestiondesinscriptions.model.Eleve;
import ml.kalanblow.gestiondesinscriptions.model.Email;
import ml.kalanblow.gestiondesinscriptions.request.CreateEleveParameters;
import ml.kalanblow.gestiondesinscriptions.request.EditEleveParameters;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface EleveService {


    /**
     * @param email
     * @return
     */
    boolean verifierExistenceEmail(Email email);

    /**
     * @param email
     * @return
     */
    Optional<Eleve> chercherParEmail(String email);


    /**
     * @param pageable
     * @return
     */
    Page<Eleve> obtenirListeElevePage(Pageable pageable);

    // tag::editEleve[]
    Eleve mettreAjourUtilisateur(Long userId, EditEleveParameters parameters);
    // end::editEleve[]

    /**
     * @param userId
     * @return
     */
    Optional<Eleve> obtenirEleveParSonId(Long userId);

    Optional<Eleve> chercherParSonNumeroIne(String ineNumber);

    /**
     * @param userId
     */
    void supprimerEleveParSonId(Long userId);

    long countEleves();

    void deleteAllEleves();

    /**
     * Récupère un élève en fonction de son numéro INE.
     *
     * @param ineNumber Le numéro INE de l'élève.
     * @return Une instance d'Élève enveloppée dans un Optional, ou Optional.empty() si aucun élève correspondant n'est trouvé.
     */
    Optional<Eleve> getEleveByIneNumber(String ineNumber);

    Eleve CreationUtilisateur(CreateEleveParameters createEleveParameters);

    List<Eleve> recupererEleveParPrenomEtNom(String  prenom, String nomDeFamille);

    /**
     * @param userId
     */
    void supprimerUtilisateurParId(Long userId);

    Optional<Eleve> recupererEleveParTelephone( String telephone);

}
