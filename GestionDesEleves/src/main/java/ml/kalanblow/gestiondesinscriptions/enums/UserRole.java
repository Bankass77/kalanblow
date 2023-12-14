package ml.kalanblow.gestiondesinscriptions.enums;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ml.kalanblow.gestiondesinscriptions.model.json.UserRoleDeserializer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@JsonDeserialize(using = UserRoleDeserializer.class)
@AllArgsConstructor
@Getter
public enum UserRole implements Serializable {

    STUDENT(Collections.singleton(Privilege.READ_PRIVILEGE), "Elève"),
    TEACHER(new HashSet<>(Arrays.asList(Privilege.READ_PRIVILEGE, Privilege.WRITE_PRIVILEGE)), "Enseignant"),
    MANAGER(new HashSet<>(Arrays.asList(Privilege.READ_PRIVILEGE, Privilege.WRITE_PRIVILEGE)), "Gestionnaire"),
    ADMIN(new HashSet<>(Arrays.asList(Privilege.READ_PRIVILEGE, Privilege.WRITE_PRIVILEGE)), "Administrateur"),
    USER(Collections.singleton(Privilege.READ_PRIVILEGE), "Utilisateur"),
    PARENT(Collections.singleton(Privilege.READ_PRIVILEGE), "Parent");

    private final Set<Privilege> privileges;

    private final String value;


    public List<SimpleGrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = getPrivileges()
                .stream()
                .map(privilege -> new SimpleGrantedAuthority(privilege.name()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }

    public static Set<UserRole> valueAsSet() {
        return new HashSet<>(Arrays.asList(UserRole.values()));
    }

    public static UserRole fromValue(String value) {
        for (UserRole userRole : UserRole.values()) {
            if (userRole.name().equalsIgnoreCase(value)) {
                return userRole;
            }
        }
        throw new IllegalArgumentException("Invalid UserRole value: " + value);
    }


    // Function to map UserRole to a list of SimpleGrantedAuthority
    public static final Function<? super UserRole, ? extends List<SimpleGrantedAuthority>> mapUserRoleToAuthorities =
            userRole -> userRole.getPrivileges()
                    .stream()
                    .map(privilege -> new SimpleGrantedAuthority(privilege.name()))
                    .collect(Collectors.toList());
}
