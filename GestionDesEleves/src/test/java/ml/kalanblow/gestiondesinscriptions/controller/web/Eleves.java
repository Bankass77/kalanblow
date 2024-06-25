package ml.kalanblow.gestiondesinscriptions.controller.web;

import ml.kalanblow.gestiondesinscriptions.enums.Gender;
import ml.kalanblow.gestiondesinscriptions.model.Eleve;
import ml.kalanblow.gestiondesinscriptions.model.Email;
import ml.kalanblow.gestiondesinscriptions.model.Parent;
import ml.kalanblow.gestiondesinscriptions.model.UserName;
import ml.kalanblow.gestiondesinscriptions.util.CalculateUserAge;

import java.time.LocalDate;

import static ml.kalanblow.gestiondesinscriptions.util.KaladewnUtility.generatingandomAlphaNumericStringWithFixedLength;

public class Eleves {

    public static Eleve ajouterUnEleve() {

        return ajouterUnEleve(new UserName("Jean-Paul", "Duang"));
    }

    public static Eleve ajouterUnEleve(UserName userName) {

        Eleve.EleveBuilder eleveBuilder = Eleve.builder();
        LocalDate localDate = LocalDate.of(1989, 1, 1);
        eleveBuilder.dateDeNaissance(localDate);
        eleveBuilder.age(CalculateUserAge.calculateAge(localDate));
        Parent parentMere = new Parent();
        parentMere.setUserName(userName);
        parentMere.setGender(Gender.FEMALE);
        parentMere.setEmail(new Email("mere@gmail.com"));
        parentMere.setProfession("Informaticien");
        eleveBuilder.mere(parentMere);
        eleveBuilder.ineNumber(generatingandomAlphaNumericStringWithFixedLength());
        return Eleve.createEleveFromBuilder(eleveBuilder);
    }

}
