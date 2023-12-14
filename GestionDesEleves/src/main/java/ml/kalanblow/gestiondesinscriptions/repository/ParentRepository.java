package ml.kalanblow.gestiondesinscriptions.repository;

import ml.kalanblow.gestiondesinscriptions.model.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ParentRepository  extends JpaRepository<Parent, Long> , UserBaseRepository<Parent>{


    List<Parent> findByProfession(String profession);


    // Méthode pour récupérer un parent par son ID
    Parent findParentById(long id);

    List<Parent> findParentByEnfantsMereOrEnfantsPere( Parent parent1, Parent parent2);

    List<Parent> findParentByEnfantsMereAndEnfantsPere( Parent parent1, Parent parent2);

    Optional<Parent>  deleteParentById(Long aLong);
}
