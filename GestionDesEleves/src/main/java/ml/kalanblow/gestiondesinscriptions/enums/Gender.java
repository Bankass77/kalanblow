package ml.kalanblow.gestiondesinscriptions.enums;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.json.GenderDeserializer;

@Getter
@JsonDeserialize(using = GenderDeserializer.class)
@NoArgsConstructor
@AllArgsConstructor
public enum Gender  {

    MALE("Homme"), FEMALE("Femme");

    private String value;


}
