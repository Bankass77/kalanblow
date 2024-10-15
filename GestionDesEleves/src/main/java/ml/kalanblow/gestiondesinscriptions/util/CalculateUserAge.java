package ml.kalanblow.gestiondesinscriptions.util;

import java.time.LocalDate;
import java.time.Period;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.Period;

/**
 * Classe utilitaire pour calculer l'âge d'un utilisateur à partir de sa date de naissance.
 */
public class CalculateUserAge {

    private static final Logger log = LoggerFactory.getLogger(CalculateUserAge.class);

    /**
     * Calcule l'âge d'un utilisateur à partir de sa date de naissance.
     *
     * @param dateDeNaissance La date de naissance de l'utilisateur.
     * @return L'âge de l'utilisateur.
     */
    public static int calculateAge(LocalDate dateDeNaissance) {
        LocalDate currentDate = LocalDate.now();

        if (dateDeNaissance != null && currentDate != null && dateDeNaissance.isBefore(currentDate)) {
            int age = Period.between(dateDeNaissance, currentDate).getYears();
            log.debug("Calcul de l'âge de l'utilisateur : {}", age);
            return age;
        }

        return 0;
    }
}

