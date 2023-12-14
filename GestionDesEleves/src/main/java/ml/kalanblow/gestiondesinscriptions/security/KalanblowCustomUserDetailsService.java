package ml.kalanblow.gestiondesinscriptions.security;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import ml.kalanblow.gestiondesinscriptions.enums.UserRole;
import ml.kalanblow.gestiondesinscriptions.model.*;
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
     * Localise l'utilisateur en fonction du nom d'utilisateur. Dans l'implémentation réelle, la recherche
     * peut être sensible à la casse ou insensible à la casse en fonction de la configuration de l'instance
     * d'implémentation. Dans ce cas, l'objet <code>UserDetails</code> renvoyé peut avoir un nom d'utilisateur
     * qui a une casse différente de celle qui a été demandée.
     *
     * @param username le nom d'utilisateur identifiant l'utilisateur dont les données sont nécessaires.
     * @return un enregistrement d'utilisateur entièrement renseigné (jamais <code>null</code>)
     * @throws UsernameNotFoundException si l'utilisateur n'a pas pu être trouvé ou si l'utilisateur n'a aucune
     *                                   GrantedAuthority
     */


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Eleve> eleve = eleveRepository.findElevesByEmail(new Email(username));
        Optional<Enseignant> enseignant = enseignantRepository.findEnseignantByEmail(new Email(username));

        if (eleve.isPresent()) {
            log.debug("Élève trouvé : {} avec les rôles : {}", eleve.get().getEmail().asString(), eleve.get().getRoles());
            return loadEleve(eleve.get());
        } else if (enseignant.isPresent()) {
            log.debug("Élève trouvé : {} avec les rôles : {}", enseignant.get().getEmail().asString(), enseignant.get().getRoles());
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
        return getUserDetails(eleve.getRoles(), eleve.getEmail(), eleve.getUserName(), eleve.getPassword());
    }

    @NotNull
    private UserDetails getUserDetails(Set<UserRole> roles, Email email, UserName userName, String password) {
        Set<GrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getValue()))
                .collect(Collectors.toSet());

        return new KalanblowUserDetails(
                email.asString(),
                userName.getFullName(),
                password,
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
        return getUserDetails(enseignant.getRoles(), enseignant.getEmail(), enseignant.getUserName(), enseignant.getPassword());
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
                        "'" + email + "' n'a pas été trouvé en tant qu'administrateur."));
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
     * @throws UsernameNotFoundException si l'administrateur n'est pas trouvé.
     */
    private UserDetails loadGeneralParent(Email email) {
        var parent = enseignantRepository.findEnseignantByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(
                        "'" + email + "' n'a pas été trouvé en tant qu'administrateur."));
        var auths = new HashSet<GrantedAuthority>();
        auths.add(new SimpleGrantedAuthority(UserRole.PARENT.getValue()));
        return new KalanblowUserDetails(parent.getEmail().asString(), parent.getPassword(), parent.getUserName().getFullName(),
                auths);
    }
}
