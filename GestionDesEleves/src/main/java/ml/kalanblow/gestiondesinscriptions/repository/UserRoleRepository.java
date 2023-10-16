package ml.kalanblow.gestiondesinscriptions.repository;


import ml.kalanblow.gestiondesinscriptions.enums.UserRole;
import ml.kalanblow.gestiondesinscriptions.model.Role;
import ml.kalanblow.gestiondesinscriptions.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByUserRole(UserRole userRole);

    List<User> findByUserRoleIn(Collection<String> names, Pageable pageable);

}
