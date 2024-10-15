package ml.kalanblow.gestiondesinscriptions.repository;

import ml.kalanblow.gestiondesinscriptions.model.Parent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {

    List<Parent> findByProfession(String profession);

    // Méthode pour récupérer un parent par son ID
    Optional<Parent> findByParentId(long id);

    Optional<Parent> findByUserUserNamePrenomAndUserUserNameNomDeFamille(String prenom, String nomDeFamille);

    Optional<Parent> findByUserEmailEmail(String email);

    Optional<Parent> findByUserPhoneNumberPhoneNumber(String phonenumber);

    List<Parent> findParentByEnfants(Parent parent);

}
