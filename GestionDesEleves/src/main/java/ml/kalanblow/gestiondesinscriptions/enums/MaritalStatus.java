package ml.kalanblow.gestiondesinscriptions.enums;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.json.MaritalStatusDeserializer;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = MaritalStatusDeserializer.class)
public enum MaritalStatus implements Serializable {
    MARRIED("Married"), SINGLE("Single"), DIVORCED("Divorce");

	private String value;

}
