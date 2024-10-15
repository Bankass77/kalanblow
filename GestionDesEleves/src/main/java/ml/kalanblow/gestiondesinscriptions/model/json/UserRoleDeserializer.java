package ml.kalanblow.gestiondesinscriptions.model.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import ml.kalanblow.gestiondesinscriptions.enums.UserRole;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class UserRoleDeserializer extends JsonDeserializer<Set<UserRole>> {
    @Override
    public Set<UserRole> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        Set<UserRole> roles = new HashSet<>();

        if (node.isArray()) {
            Iterator<JsonNode> elements = node.elements();
            while (elements.hasNext()) {
                JsonNode element = elements.next();
                String roleValue = element.asText();
                roles.add(UserRole.fromValue(roleValue));  // Utilisation de la méthode fromValue pour valider et ajouter
            }
        }
        return roles;
    }
}
