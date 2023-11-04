package ml.kalanblow.gestiondesinscriptions.security;

import jakarta.transaction.Transactional;
import ml.kalanblow.gestiondesinscriptions.enums.UserRole;
import ml.kalanblow.gestiondesinscriptions.model.Eleve;
import ml.kalanblow.gestiondesinscriptions.model.Email;
import ml.kalanblow.gestiondesinscriptions.model.Enseignant;
import ml.kalanblow.gestiondesinscriptions.repository.EleveRepository;
import ml.kalanblow.gestiondesinscriptions.repository.EnseignantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {


    private final EleveRepository  eleveRepository;

    private final EnseignantRepository  enseignantRepository;

    @Autowired
    public CustomUserDetailsService(EleveRepository eleveRepository, EnseignantRepository enseignantRepository) {
        this.eleveRepository = eleveRepository;

        this.enseignantRepository= enseignantRepository;
    }

    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Eleve> eleve = eleveRepository.findElevesByEmail(new Email(username));
        Optional<Enseignant> enseignant = enseignantRepository.searchAllByEmailIsLike(username);

        if (eleve.isPresent()) {
            return loadEleve(eleve.get());
        } else if (enseignant.isPresent()) {
            return loadGeneralAdminOrEnseignant(enseignant.get());
        } else {
            throw new UsernameNotFoundException("Utilisateur non trouvé avec le nom d'utilisateur: " + username);
        }
    }

    /**
     * Charge les détails d'un utilisateur de type "Eleve" et crée un objet UserDetails personnalisé.
     *
     * @param eleve L'objet Eleve à partir duquel extraire les détails.
     * @return Un objet UserDetails représentant l'utilisateur de type "Eleve".
     */
    private UserDetails loadEleve(Eleve eleve) {
        Set<UserRole> eleveRoles = eleve.getRoles();
        Set<GrantedAuthority> authorities = eleveRoles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getValue()))
                .collect(Collectors.toSet());

        return new KalanblowUserDetails(
                eleve.getEmail().asString(),
                eleve.getPassword(),
                eleve.getIneNumber(),
                authorities
        );
    }

    /**
     * Charge les détails d'un utilisateur de type "Enseignant" et crée un objet UserDetails personnalisé.
     *
     * @param enseignant L'objet Enseignant à partir duquel extraire les détails.
     * @return Un objet UserDetails représentant l'utilisateur de type "Enseignant".
     */
    private UserDetails loadGeneralAdminOrEnseignant(Enseignant enseignant) {
        Set<UserRole> enseignantsRoles = enseignant.getRoles();
        Set<GrantedAuthority> authorities = enseignantsRoles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getValue()))
                .collect(Collectors.toSet());

        return new KalanblowUserDetails(
                enseignant.getEmail().asString(),
                enseignant.getPassword(),
                enseignant.getLeMatricule(),
                authorities
        );
    }

    /**
     * Charge les détails d'un administrateur général et crée un objet UserDetails personnalisé.
     *
     * @param email L'adresse e-mail de l'administrateur général.
     * @return Un objet UserDetails représentant l'administrateur général.
     * @throws UsernameNotFoundException si l'administrateur général n'est pas trouvé.
     */
    private UserDetails loadGeneralAdmin(String email) {
        var admin = enseignantRepository.searchAllByEmailIsLike(email).orElseThrow(
                () -> new UsernameNotFoundException(
                        "'" + email + "' was not found as a general admin."));
        var auths = new HashSet<GrantedAuthority>();
        auths.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        return new KalanblowUserDetails(admin.getEmail().asString(), admin.getPassword(), admin.getUserName().getFullName(),
                auths);
    }

}
