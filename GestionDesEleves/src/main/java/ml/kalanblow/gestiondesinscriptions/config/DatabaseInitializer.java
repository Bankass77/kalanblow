package ml.kalanblow.gestiondesinscriptions.config;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import ml.kalanblow.gestiondesinscriptions.enums.Gender;
import ml.kalanblow.gestiondesinscriptions.enums.MaritalStatus;
import ml.kalanblow.gestiondesinscriptions.model.*;
import ml.kalanblow.gestiondesinscriptions.request.CreateEleveParameters;
import ml.kalanblow.gestiondesinscriptions.request.CreateEtablissementScolaireParameters;
import ml.kalanblow.gestiondesinscriptions.request.EditEtablissementScolaireParameters;
import ml.kalanblow.gestiondesinscriptions.service.EleveService;
import ml.kalanblow.gestiondesinscriptions.service.EtablissementScolaireService;
import ml.kalanblow.gestiondesinscriptions.util.CalculateUserAge;
import ml.kalanblow.gestiondesinscriptions.util.KaladewnUtility;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static ml.kalanblow.gestiondesinscriptions.config.PasswordGenerator.generateRandomPassword;

@Component
@Profile("dev")
public class DatabaseInitializer implements CommandLineRunner {

    @Value("${database.initialization.enabled}")
    private boolean databaseInitializationEnabled;
    private final Faker faker = new Faker(new Locale("fr"));

    private final EleveService eleveService;
    private final EtablissementScolaireService etablissementScolaireService;


    public DatabaseInitializer(EleveService eleveService, EtablissementScolaireService etablissementScolaireService) {
        this.eleveService = eleveService;
        this.etablissementScolaireService = etablissementScolaireService;

    }

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {

        if (databaseInitializationEnabled) {
            Etablissement.EtablissementBuilder etablissementScolaireBuilder = new Etablissement.EtablissementBuilder();

            Address address = new Address();
            address.setStreet("Rue du Rond Point");
            address.setStreetNumber(80);
            address.setCity("Bamako");
            address.setCodePostale(24560);
            address.setCountry("Mali");
            etablissementScolaireBuilder.adresse(address);
            etablissementScolaireBuilder.createDate(LocalDateTime.now());
            etablissementScolaireBuilder.lastModiedDate(LocalDateTime.now());
            etablissementScolaireBuilder.email(new Email("collegedujeunefille@exemple.com"));
            etablissementScolaireBuilder.nomEtablissement("Amical Kabral");
            PhoneNumber phoneNumber = new PhoneNumber("+22367890123");
            etablissementScolaireBuilder.phoneNumber(phoneNumber);

            etablissementScolaireBuilder.build();
            Etablissement etablissement = Etablissement.creerEtablissementScolaireFromBuilder(etablissementScolaireBuilder);
            CreateEtablissementScolaireParameters etablissementScolaireParameters = getCreateEtablissementScolaireParameters(etablissement);
            Etablissement newEtablissement = etablissementScolaireService.creerEtablissementScolaire(etablissementScolaireParameters);


            for (int i = 0; i < 20; i++) {
                CreateEleveParameters createEleveParameters = newRandomEleveParameters();
                eleveService.ajouterUnEleve(createEleveParameters);
                // System.out.println(createEleveParameters);
            }
        }

    }

    private CreateEleveParameters newRandomEleveParameters() throws NoSuchAlgorithmException {

        Name name = faker.name();
        UserName userName = new UserName(name.firstName(), name.lastName());
        Gender gender = faker.bool().bool() ? Gender.MALE : Gender.FEMALE;
        LocalDate dateDeNaissance = LocalDate.ofInstant(faker.date().birthday(6, 14).toInstant(), ZoneId.systemDefault());
        Email email = new Email(faker.internet().emailAddress(generateEmailLocalPart(userName)));
        PhoneNumber phoneNumber = new PhoneNumber(faker.phoneNumber().phoneNumber());
        MaritalStatus maritalStatus = faker.bool().bool() ? MaritalStatus.MARRIED : MaritalStatus.SINGLE;
        Address address = new Address();
        address.setStreetNumber(Integer.valueOf(faker.address().streetAddressNumber()));
        address.setStreet(faker.address().streetAddress());
        address.setCountry(faker.address().country());
        address.setCity(faker.address().city());
        address.setCodePostale(Integer.valueOf(faker.address().zipCode()));
        String password = generateRandomPassword(12);
        LocalDateTime createdDate = LocalDateTime.now();
        LocalDateTime modifyDate = LocalDateTime.now();
        int age = CalculateUserAge.calculateAge(dateDeNaissance);
        String studentIneNumber = KaladewnUtility.generatingandomAlphaNumericStringWithFixedLength();
        String motherFirstName = name.firstName();
        String motherLastName = name.lastName();
        PhoneNumber motherMobile = phoneNumber;
        String fatherLastName = name.lastName();
        String fatherFirstName = name.firstName();
        PhoneNumber fatherMobile = phoneNumber;
        List<Absence> absenceEleves = new ArrayList<>();


        Etablissement etablissement = etablissementScolaireService.trouverEtablissementScolaireParSonIdentifiant(4002);
        return new CreateEleveParameters(userName, gender, maritalStatus, email, password, phoneNumber, address,
                createdDate, modifyDate, dateDeNaissance, age, studentIneNumber, motherFirstName, motherLastName, motherMobile,
                fatherLastName, fatherFirstName, fatherMobile, etablissement, absenceEleves);

    }

    private String generateEmailLocalPart(UserName userName) {

        return String.format("%s.%s", StringUtils.remove(userName.getPrenom().toLowerCase(), "'"), StringUtils.remove(userName.getNomDeFamille().toLowerCase(), "'"));

    }


    private static CreateEtablissementScolaireParameters getCreateEtablissementScolaireParameters(Etablissement etablissement) {
        CreateEtablissementScolaireParameters etablissementScolaireParameters = new EditEtablissementScolaireParameters();
        etablissementScolaireParameters.setSalleDeClasses(etablissement.getSalles());
        etablissementScolaireParameters.setEmail(etablissement.getEmail());
        etablissementScolaireParameters.setLastModifiedDate(etablissement.getLastModifiedDate());
        etablissementScolaireParameters.setNomEtablissement(etablissement.getNomEtablissement());
        etablissementScolaireParameters.setAddress(etablissement.getAddress());
        etablissementScolaireParameters.setNomEtablissement(etablissement.getNomEtablissement());
        etablissementScolaireParameters.setPhoneNumber(etablissement.getPhoneNumber());
        return etablissementScolaireParameters;
    }


}
