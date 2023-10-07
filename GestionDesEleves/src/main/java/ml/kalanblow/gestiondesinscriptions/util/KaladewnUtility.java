package ml.kalanblow.gestiondesinscriptions.util;

import java.util.Random;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KaladewnUtility {

	public static String generatingandomAlphaNumericStringWithFixedLength() {

		int leftLimit = 48;
		int rightLimit = 122;
		int targetStringLength = 16;
		Random random = new Random();

		String generatedString = random.ints(leftLimit, rightLimit + 1)
				.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

		log.debug("Ine Number generation: {}", generatedString);
		return generatedString;

	}

}
