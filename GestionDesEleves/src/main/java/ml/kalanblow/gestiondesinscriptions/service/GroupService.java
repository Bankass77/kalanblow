package ml.kalanblow.gestiondesinscriptions.service;

import ml.kalanblow.gestiondesinscriptions.model.Group;

import java.util.Optional;
import java.util.Set;

public interface GroupService {
    Set<Group> getAllGroups();
    public Optional<Group> getGroupById(Long id);

    public Group saveGroup(Group group);

    public void deleteGroup(Long id);
}
