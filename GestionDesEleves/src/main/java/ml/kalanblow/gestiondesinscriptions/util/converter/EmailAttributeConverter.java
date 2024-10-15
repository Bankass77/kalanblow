package ml.kalanblow.gestiondesinscriptions.util.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ml.kalanblow.gestiondesinscriptions.model.Email;


@Converter(autoApply = true)
public class EmailAttributeConverter implements AttributeConverter<Email, String> {

    @Override
    public String convertToDatabaseColumn(Email attribute) {

        return attribute.asString();
    }

    @Override
    public Email convertToEntityAttribute(String dbData) {

        return new Email(dbData);
    }

}
