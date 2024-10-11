package ml.kalanblow.gestiondesinscriptions.service;

import ml.kalanblow.gestiondesinscriptions.model.Parent;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ParentService {

    List<Parent> getAllParents();

    Parent getParentById(Long id);

    List<Parent> getParentsByProfession(String profession);


    void deleteParent(Long id);

}
