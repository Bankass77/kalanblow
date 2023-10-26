package ml.kalanblow.gestiondesinscriptions.service;

import ml.kalanblow.gestiondesinscriptions.enums.UserRole;

import java.util.Optional;

public interface RoleService {

    /**
     * Recherche un rôle par son identifiant et l'ordonne par le rôle utilisateur associé.
     *
     * @param id L'identifiant du rôle.
     * @param userRole Le rôle utilisateur associé.
     * @return Une option contenant le rôle trouvé, ou une option vide si aucun rôle correspondant n'est trouvé.
     */
    Optional<UserRole> findDistinctByIdOrderByUserRole(Long id, UserRole userRole);

    /**
     * Recherche un rôle par le rôle utilisateur.
     *
     * @param userRole Le rôle utilisateur à rechercher.
     * @return Une option contenant le rôle trouvé, ou une option vide si aucun rôle correspondant n'est trouvé.
     */
    Optional<UserRole> findDistinctByUserRole(UserRole userRole);

}
