package ml.kalanblow.gestiondesinscriptions.repository;


import ml.kalanblow.gestiondesinscriptions.model.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EleveRepository  extends JpaRepository<Eleve, Long> {


    Optional<Eleve> findEleveByEleveId(long id);
    /**
     *
     * @param nomDeFamille le nom de l'élève
     * @param prenom le prénom de l'élève
     * @return une liste d'élève correspondant au nom et prénom
     */
    List<Eleve> findByUserUserNamePrenomAndUserUserNameNomDeFamille(String prenom, String nomDeFamille);

    // Méthode qui renvoie une page d'élèves
    Page<Eleve> findAll(Pageable pageable);

    // Méthode avec filtre par classe
    Page<Eleve> findByClasseActuelle(Classe classe, Pageable pageable);
    /**
     *
     * @param email de l'élève
     * @return un élève ou null
     */
    Optional<Eleve> findByUserUserEmailEmail(String email);

    /**
     *
     * @param phoneNumber , numéro de l'élève à chercher
     * @return un élève ou null
     */
    @Query("SELECT e FROM Eleve e WHERE e.user.user_phoneNumber.phoneNumber = :phoneNumber")
    Optional<Eleve> findByUserUser_phoneNumberPhoneNumber(@Param("phoneNumber") String phoneNumber);

    /**
     *
     * @param nom de l'élève
     * @param classe de l'élève
     * @return une liste d'élève en fonction d'une classe
     */
    @Query("SELECT e FROM Eleve e WHERE e.user.userName.nomDeFamille = :nom AND e.classeActuelle.nom = :classe")
    List<Eleve> findByUserUserNameNomDeFamilleAndClasse(@Param("nom") String nom, @Param("classe") String classe);

    /**
     *
     * @param ineNumber identifiant de l'élève
     * @return l'élève en fonction de son numéro de télephone
     */
    Optional<Eleve> findByIneNumber( String ineNumber);
    /**
     *
     * @param parent de l'élève
     * @return une liste d'élève en fonction des parents
     */
    List<Eleve> findByParents( Parent parent);

    void deleteEleveByEleveId (long id);
}
