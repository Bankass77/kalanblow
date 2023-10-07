package ml.kalanblow.gestiondesinscriptions.model.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import ml.kalanblow.gestiondesinscriptions.enums.UserRole;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class UserRoleDeserializer extends  JsonDeserializer<Set<UserRole>> {
@Override
public Set<UserRole> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String value = jsonParser.getText();
        String[] roleValues = value.split(","); // Supposons que les rôles sont séparés par des virgules
        Set<UserRole> userRoles = new HashSet<>();

        for (String roleValue : roleValues) {
        UserRole userRole = UserRole.fromValue(roleValue.trim());
        userRoles.add(userRole);
        }

        return userRoles;
        }
}
