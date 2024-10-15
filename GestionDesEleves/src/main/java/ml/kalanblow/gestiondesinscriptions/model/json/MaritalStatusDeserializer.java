package ml.kalanblow.gestiondesinscriptions.model.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import ml.kalanblow.gestiondesinscriptions.enums.MaritalStatus;

import java.io.IOException;

public class MaritalStatusDeserializer extends JsonDeserializer<MaritalStatus> {
    @Override
    public MaritalStatus deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String value = jsonParser.getText();
        for (MaritalStatus maritalStatus : MaritalStatus.values()) {
            if (maritalStatus.getValue().equalsIgnoreCase(value)) {
                return maritalStatus;
            }
        }
        throw new IllegalArgumentException("Invalid MaritalStatus value: " + value);
    }
}
