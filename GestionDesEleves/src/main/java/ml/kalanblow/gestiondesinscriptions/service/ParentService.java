package ml.kalanblow.gestiondesinscriptions.service;

import ml.kalanblow.gestiondesinscriptions.model.Parent;
import ml.kalanblow.gestiondesinscriptions.request.CreateParentParameters;
import ml.kalanblow.gestiondesinscriptions.request.EditParentParameters;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ParentService {

    List<Parent> getAllParents();

    Parent getParentById(Long id);

    List<Parent> getParentsByProfession(String profession);

    Optional<Parent> saveParent(CreateParentParameters createParentParameters) throws IOException;

    Optional<Parent> editParent(Long id, EditParentParameters editParentParameters);

    void deleteParent(Long id);

     void remove(Parent parent);

}
