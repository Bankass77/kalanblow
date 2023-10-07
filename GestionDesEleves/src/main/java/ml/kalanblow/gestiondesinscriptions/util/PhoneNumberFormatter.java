package ml.kalanblow.gestiondesinscriptions.util;

import java.text.ParseException;
import java.util.Locale;



import jakarta.annotation.Nonnull;

import ml.kalanblow.gestiondesinscriptions.model.PhoneNumber;
import org.springframework.format.Formatter;


public class PhoneNumberFormatter implements Formatter<PhoneNumber> {

	@Override
	@Nonnull
	public String print(@Nonnull PhoneNumber object, @Nonnull Locale locale) {

		return object.asString();
	}

	@Nonnull
	@Override
	public PhoneNumber parse(@Nonnull String text, @Nonnull Locale locale) throws ParseException {

		return new PhoneNumber(text);
	}

}
