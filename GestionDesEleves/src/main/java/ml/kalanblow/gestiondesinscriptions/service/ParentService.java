package ml.kalanblow.gestiondesinscriptions.service;

import ml.kalanblow.gestiondesinscriptions.model.Parent;

import java.util.List;
import java.util.Optional;

public interface ParentService {

    List<Parent> getAllParents();

    Optional<Parent> getParentById(Long id);

    List<Parent> getParentsByProfession(String profession);

    Optional<Parent> findByUserUserNamePrenomAndUserUserNameNomDeFamille(String prenom, String nomDeFamille);

    Optional<Parent> findByUserEmail(String email);

    Optional<Parent> findByPhoneNumber(String phonenumber);

    List<Parent> findParentByEnfants(Parent parent);

    void deleteParent(Long id);

    Optional<Parent> updateParents(long id, Parent parent);

}
