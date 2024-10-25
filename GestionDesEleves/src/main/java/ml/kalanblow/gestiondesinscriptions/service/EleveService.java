package ml.kalanblow.gestiondesinscriptions.service;


import ml.kalanblow.gestiondesinscriptions.model.*;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;

public interface EleveService {


    /**
     *
     * @param nom le nom de l'élève
     * @param prenom le prénom de l'élève
     * @return une liste d'élève correspondant au nom et prénom
     */
    List<Eleve> findByUserUsername(String nom, String prenom);

    /**
     *
     * @param email de l'élève
     * @return un élève ou null
     */
    Optional<Eleve> findUserByEmail( String email);

    /**
     *
     * @param phoneNumber , numéro de l'élève à chercher
     * @return un élève ou null
     */
    Optional<Eleve> findUserByPhoneNumber( String phoneNumber);

    /**
     *
     * @param nom de l'élève
     * @param classe de l'élève
     * @return une liste d'élève en fonction d'une classe
     */
    @Query("Select e From Eleve e WHERE e.user.userName = userName AND e.classeActuelle = :classe")
    List<Eleve> findByNomAndClasse(@Param("nom") String nom, @Param("classe") String classe);

    /**
     *
     * @param ineNumber identifiant de l'élève
     * @return l'élève en fonction de son numéro de télephone
     */
    Optional<Eleve> findByIneNumber( String ineNumber);

    /**
     *
     * @param eleve qui doit être inscrit
     * @return un élève
     */
    Eleve inscrireNouveauEleve(Eleve eleve);

    /**
     *
     * @param id de l'élève
     * @param eleve à mettre à jour
     * @return un élève
     */
    Eleve mettreAjourEleve(Long id, Eleve eleve);

    void supprimerEleve( long id);

    List<Eleve> getEleveParents(final Parent parent);

    Optional<Eleve> FindEleveById(Long id);

    public Page<Eleve> getElevesPagine(int page, int size);
    // Méthode pour obtenir une page d'élèves d'une classe spécifique
    public Page<Eleve> getElevesPagineParClasse(Classe classe, int page, int size);
}
