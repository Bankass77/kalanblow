package ml.kalanblow.gestiondesinscriptions.security;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import ml.kalanblow.gestiondesinscriptions.enums.UserRole;
import ml.kalanblow.gestiondesinscriptions.model.Eleve;
import ml.kalanblow.gestiondesinscriptions.model.Email;
import ml.kalanblow.gestiondesinscriptions.model.Enseignant;
import ml.kalanblow.gestiondesinscriptions.repository.EleveRepository;
import ml.kalanblow.gestiondesinscriptions.repository.EnseignantRepository;
import ml.kalanblow.gestiondesinscriptions.repository.ParentRepository;
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
@Slf4j
public class KalanblowCustomUserDetailsService implements UserDetailsService {


    private final EleveRepository  eleveRepository;

    private final EnseignantRepository  enseignantRepository;

    private final ParentRepository parentRepository;



    @Autowired
    public KalanblowCustomUserDetailsService(EleveRepository eleveRepository, EnseignantRepository enseignantRepository, ParentRepository parentRepository) {
        this.eleveRepository = eleveRepository;
        this.parentRepository=parentRepository;
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
        Optional<Enseignant> enseignant = enseignantRepository.findEnseignantByEmail(new Email(username));

        if (eleve.isPresent()) {
            log.debug("Found Eleve: {} with roles: {}", eleve.get().getEmail().asString(), eleve.get().getRoles());
            return loadEleve(eleve.get());
        } else if (enseignant.isPresent()) {
            log.debug("Found Eleve: {} with roles: {}", enseignant.get().getEmail().asString(), enseignant.get().getRoles());
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
                eleve.getUserName().getFullName(),
                eleve.getPassword(),
                authorities
        );
    }

    /**
     * Charge les détails d'un Enseignant de type "Enseignant" et crée un objet UserDetails personnalisé.
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
                enseignant.getUserName().getFullName(),
                enseignant.getPassword(),
                authorities
        );
    }

    /**
     * Charge les détails d'un Manager général et crée un objet UserDetails personnalisé.
     *
     * @param email L'adresse e-mail de l'administrateur général.
     * @return Un objet UserDetails représentant l'administrateur général.
     * @throws UsernameNotFoundException si l'administrateur général n'est pas trouvé.
     */
    private UserDetails loadGeneralManager(Email email) {
        var manager = enseignantRepository.findEnseignantByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(
                        "'" + email + "' was not found as a general admin."));
        var auths = new HashSet<GrantedAuthority>();
        auths.add(new SimpleGrantedAuthority(UserRole.PARENT.getValue()));
        return new KalanblowUserDetails(manager.getEmail().asString(), manager.getUserName().getFullName(), manager.getPassword(),
                auths);
    }


    /**
     * Charge les détails d'un administrateur général et crée un objet UserDetails personnalisé.
     *
     * @param email L'adresse e-mail de l'administrateur général.
     * @return Un objet UserDetails représentant l'administrateur général.
     * @throws UsernameNotFoundException si l'administrateur général n'est pas trouvé.
     */
    private UserDetails loadGeneralParent(Email email) {
        var parent = enseignantRepository.findEnseignantByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(
                        "'" + email + "' was not found as a general admin."));
        var auths = new HashSet<GrantedAuthority>();
        auths.add(new SimpleGrantedAuthority(UserRole.PARENT.getValue()));
        return new KalanblowUserDetails(parent.getEmail().asString(), parent.getPassword(), parent.getUserName().getFullName(),
                auths);
    }
}
