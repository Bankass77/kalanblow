package ml.kalanblow.gestiondesinscriptions.config;

import com.github.javafaker.Faker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TelephoneGenerator {
    public static void main(String[] args) {
        Faker faker = new Faker();

        // Définir le pattern regex
        String TELEPHONE_REGEX = "^(\\+223|00223)(6|7)\\d{7}$";
        Pattern pattern = Pattern.compile(TELEPHONE_REGEX);

        // Générer un numéro de téléphone correspondant au regex
        String numeroTelephone = generatePhoneNumber(faker, pattern);

        System.out.println(numeroTelephone);
    }

    private static String generatePhoneNumber(Faker faker, Pattern pattern) {
        String numeroTelephone;
        Matcher matcher;

        do {
            numeroTelephone = String.format("%s%s%s",
                    faker.regexify("[+0][2][23]"),
                    faker.regexify("[67]"),
                    faker.regexify("\\d{7}"));

            matcher = pattern.matcher(numeroTelephone);
        } while (!matcher.matches());

        return numeroTelephone;
    }
}
