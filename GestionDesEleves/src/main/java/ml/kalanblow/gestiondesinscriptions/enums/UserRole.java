package ml.kalanblow.gestiondesinscriptions.enums;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.json.UserRoleDeserializer;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@JsonDeserialize(using = UserRoleDeserializer.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum UserRole implements Serializable {
    STUDENT ("Elève"), TEACHER ("Enseignant"), MANAGER("Gestionnaire"), ADMIN("Administrateur"), USER("Utilisateur"), PARENT("Parent");

    private String value;
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
}
