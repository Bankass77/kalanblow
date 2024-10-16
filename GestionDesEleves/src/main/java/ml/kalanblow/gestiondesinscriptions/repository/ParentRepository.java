package ml.kalanblow.gestiondesinscriptions.repository;

import ml.kalanblow.gestiondesinscriptions.model.Parent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {

    List<Parent> findByProfession(String profession);

    // Méthode pour récupérer un parent par son ID
    Optional<Parent> findByParentId(long id);

    Optional<Parent> findByUserUserNamePrenomAndUserUserNameNomDeFamille(String prenom, String nomDeFamille);

    @Query("SELECT p FROM Parent p WHERE p.user.userEmail.email = :email")
    Optional<Parent> findByUserEmail(@Param("email") String email);

    @Query("SELECT p FROM Parent p WHERE p.user.user_phoneNumber.phoneNumber = :phoneNumber")
    Optional<Parent> findByUserPhoneNumber(@Param("phoneNumber") String phoneNumber);

    List<Parent> findParentByEnfants(Parent parent);

}
