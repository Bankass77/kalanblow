package ml.kalanblow.gestiondesinscriptions.util.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ml.kalanblow.gestiondesinscriptions.model.PhoneNumber;
;

@Converter(autoApply = true)
public class PhoneNumberAttributeConverter implements AttributeConverter<PhoneNumber, String> {

    @Override
    public String convertToDatabaseColumn(PhoneNumber attribute) {

        return attribute.asString();
    }

    @Override
    public PhoneNumber convertToEntityAttribute(String dbData) {

        return new PhoneNumber(dbData);
    }

}
