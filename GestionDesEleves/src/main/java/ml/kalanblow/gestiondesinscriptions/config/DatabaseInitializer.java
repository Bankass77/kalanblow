package ml.kalanblow.gestiondesinscriptions.config;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import jakarta.validation.constraints.NotNull;
import ml.kalanblow.gestiondesinscriptions.enums.Gender;
import ml.kalanblow.gestiondesinscriptions.enums.MaritalStatus;
import ml.kalanblow.gestiondesinscriptions.model.*;
import ml.kalanblow.gestiondesinscriptions.request.CreateEleveParameters;
import ml.kalanblow.gestiondesinscriptions.request.CreateParentParameters;
import ml.kalanblow.gestiondesinscriptions.service.EleveService;
import ml.kalanblow.gestiondesinscriptions.service.EtablissementService;
import ml.kalanblow.gestiondesinscriptions.service.ParentService;
import ml.kalanblow.gestiondesinscriptions.util.CalculateUserAge;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;

import static ml.kalanblow.gestiondesinscriptions.config.PasswordGenerator.generateRandomPassword;

@Component
@Profile("dev")
public class DatabaseInitializer implements CommandLineRunner {

    @Value("${database.initialization.enabled}")
    private boolean databaseInitializationEnabled;
    private final Faker faker = new Faker(new Locale("fr"));

    private final EtablissementService etablissementScolaireService;

    private  final ParentService parentService;

    private final EleveService eleveService;


    private  final BCryptPasswordEncoder passwordEncoder;


    public DatabaseInitializer(EtablissementService etablissementScolaireService, ParentService parentService, EleveService eleveService,BCryptPasswordEncoder passwordEncoder) {
        this.etablissementScolaireService = etablissementScolaireService;
        this.parentService = parentService;
        this.eleveService=eleveService;
        this.passwordEncoder=passwordEncoder;

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
          /*  Etablissement.EtablissementBuilder etablissementScolaireBuilder = new Etablissement.EtablissementBuilder();

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
            CreateEtablissementParameters etablissementScolaireParameters = getCreateEtablissementScolaireParameters(etablissement);
            Etablissement newEtablissement = etablissementScolaireService.creerEtablissementScolaire(etablissementScolaireParameters);*/


            for (int i = 0; i < 20; i++) {
                Parent mere = parentService.saveParent(createParentParametersForMere()).orElse(null);
                Parent pere = parentService.saveParent(createParentParametersForPere()).orElse(null);

                CreateEleveParameters createEleveParameters = newRandomEleveParameters();

                createEleveParameters.setMere(mere);
                createEleveParameters.setPere(pere);
                eleveService.ajouterUnEleve(createEleveParameters);



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
        address.setStreetNumber(Integer.parseInt(faker.address().streetAddressNumber()));
        address.setStreet(faker.address().streetAddress());
        address.setCountry(faker.address().country());
        address.setCity(faker.address().city());
        address.setCodePostale(Integer.valueOf(faker.address().zipCode()));
        String password =generateRandomPassword(12);



        LocalDateTime createdDate = LocalDateTime.now();
        LocalDateTime modifyDate = LocalDateTime.now();

        Etablissement etablissement = etablissementScolaireService.trouverEtablissementScolaireParSonIdentifiant(4002);

        CreateEleveParameters createEleveParameters = new CreateEleveParameters();

        createEleveParameters.setEtablissement(etablissement);

        createEleveParameters.setAge(CalculateUserAge.calculateAge(dateDeNaissance));
        createEleveParameters.setDateDeNaissance(dateDeNaissance);
        createEleveParameters.setPassword(passwordEncoder.encode(password));
        createEleveParameters.setGender(gender);
        createEleveParameters.setCreatedDate(createdDate);
        createEleveParameters.setAddress(address);
        createEleveParameters.setEmail(email);
        createEleveParameters.setModifyDate(modifyDate);
        createEleveParameters.setUserName(userName);
        createEleveParameters.setMaritalStatus(maritalStatus);
        createEleveParameters.setPhoneNumber(phoneNumber);

        return createEleveParameters;

    }

    private static String generateEmailLocalPart(UserName userName) {

        return String.format("%s.%s", StringUtils.remove(userName.getPrenom().toLowerCase(), "'"), StringUtils.remove(userName.getNomDeFamille().toLowerCase(), "'"));

    }


    private CreateParentParameters createParentParametersForPere() throws NoSuchAlgorithmException {
        Name name = faker.name();
        UserName userName = new UserName(name.firstName(), name.lastName());
        Gender gender = Gender.MALE;
        return getCreateParentParameters(userName, gender);
    }


    private CreateParentParameters createParentParametersForMere() throws NoSuchAlgorithmException {
        Name name = faker.name();
        UserName userName = new UserName(name.firstName(), name.lastName());
        Gender gender = Gender.FEMALE;
        return getCreateParentParameters(userName, gender);
    }

    @NotNull
    private CreateParentParameters getCreateParentParameters(UserName userName, Gender gender) throws NoSuchAlgorithmException {
        Email email = new Email(faker.internet().emailAddress(generateEmailLocalPart(userName)));
        PhoneNumber phoneNumber = new PhoneNumber(faker.phoneNumber().phoneNumber());
        MaritalStatus maritalStatus = MaritalStatus.MARRIED; // Vous pouvez définir le statut matrimonial du père ici.
        Address address = new Address();
        address.setStreetNumber(Integer.parseInt(faker.address().streetAddressNumber()));
        address.setStreet(faker.address().streetAddress());
        address.setCountry(faker.address().country());
        address.setCity(faker.address().city());
        address.setCodePostale(Integer.valueOf(faker.address().zipCode()));

        LocalDateTime createdDate = LocalDateTime.now();
        LocalDateTime modifyDate = LocalDateTime.now();

        CreateParentParameters createParentParameters = new CreateParentParameters();
        createParentParameters.setProfession(faker.company().profession());
        createParentParameters.setUserName(userName);
        createParentParameters.setGender(gender);
        createParentParameters.setEmail(email);
        createParentParameters.setPhoneNumber(phoneNumber);
        createParentParameters.setMaritalStatus(maritalStatus);
        createParentParameters.setAddress(address);
        createParentParameters.setPassword(passwordEncoder.encode(generateRandomPassword(12)));
        createParentParameters.setCreatedDate(createdDate);
        createParentParameters.setModifyDate(modifyDate);

        return createParentParameters;
    }

}
