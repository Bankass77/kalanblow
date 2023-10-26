package ml.kalanblow.gestiondesinscriptions.config;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import ml.kalanblow.gestiondesinscriptions.enums.Gender;
import ml.kalanblow.gestiondesinscriptions.enums.MaritalStatus;
import ml.kalanblow.gestiondesinscriptions.enums.UserRole;
import ml.kalanblow.gestiondesinscriptions.model.*;
import ml.kalanblow.gestiondesinscriptions.request.CreateEleveParameters;
import ml.kalanblow.gestiondesinscriptions.request.CreateParentParameters;
import ml.kalanblow.gestiondesinscriptions.service.EleveService;
import ml.kalanblow.gestiondesinscriptions.service.EtablissementService;
import ml.kalanblow.gestiondesinscriptions.service.ParentService;
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
import java.util.Collections;
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
    private final EtablissementService etablissementScolaireService;

    private ParentService parentService;


    public DatabaseInitializer(EleveService eleveService, EtablissementService etablissementScolaireService,ParentService parentService) {
        this.eleveService = eleveService;
        this.etablissementScolaireService = etablissementScolaireService;
        this.parentService=parentService;

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
                // Créez et sauvegardez les entités Parent (mère et père) d'abord.
                Parent mere = parentService.saveParent(createParentParametersForMere()).orElse(null);
                Parent pere = parentService.saveParent(createParentParametersForPere()).orElse(null);

                CreateEleveParameters createEleveParameters = newRandomEleveParameters();

           /*   Optional<Parent> parent=  parentService.saveParent( getCreateParentParameters(createEleveParameters.getPere()));
               createEleveParameters.setPere(parent.get());*/
                createEleveParameters.setMere(mere);
                createEleveParameters.setPere(pere);
               Eleve eleve= eleveService.ajouterUnEleve(createEleveParameters);


            }


        }

    }

    private CreateEleveParameters newRandomEleveParameters(){

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
        String password = null;


        try {
            password = generateRandomPassword(12);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
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
// Créez les paramètres pour le père et la mère de l'élève.
        CreateParentParameters pereParameters = createParentParametersForPere();
        CreateParentParameters mereParameters = createParentParametersForMere();

        Etablissement etablissement = etablissementScolaireService.trouverEtablissementScolaireParSonIdentifiant(4002);

        CreateEleveParameters createEleveParameters= new CreateEleveParameters();

        createEleveParameters.setEtablissement(etablissement);

        createEleveParameters.setAge(CalculateUserAge.calculateAge(dateDeNaissance));
        createEleveParameters.setDateDeNaissance(dateDeNaissance);
        createEleveParameters.setPassword(password);
        createEleveParameters.setGender(gender);
        createEleveParameters.setCreatedDate(createdDate);
        createEleveParameters.setAddress(address);
        createEleveParameters.setEmail(email);
        createEleveParameters.setModifyDate(modifyDate);
        createEleveParameters.setUserName(userName);
        createEleveParameters.setMaritalStatus(maritalStatus);
        createEleveParameters.setPhoneNumber(phoneNumber);

        return  createEleveParameters;

    }

    private Parent createEleveParent(UserName userName, Gender gender, Email email, PhoneNumber phoneNumber, MaritalStatus maritalStatus, Address address, String password) {
        Parent pere = new Parent();
        pere.setProfession(faker.company().profession());
        pere.setUserName(userName);
        pere.setRoles(Collections.singleton(UserRole.USER));
        pere.setMaritalStatus(maritalStatus);
        pere.setPhoneNumber(phoneNumber);
        pere.setLastModifiedDate(LocalDateTime.now());
        pere.setCreatedDate(LocalDateTime.now());
        pere.setEmail(email);
        pere.setPassword(password);
        pere.setGender(gender);
        pere.setAddress(address);
        return pere;
    }

    private static String generateEmailLocalPart(UserName userName) {

        return String.format("%s.%s", StringUtils.remove(userName.getPrenom().toLowerCase(), "'"), StringUtils.remove(userName.getNomDeFamille().toLowerCase(), "'"));

    }

    private static CreateParentParameters getCreateParentParameters(Parent parent) {
        Faker faker = new Faker(new Locale("fr"));
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
        String password = null;


        try {
            password = generateRandomPassword(12);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        LocalDateTime createdDate = LocalDateTime.now();
        LocalDateTime modifyDate = LocalDateTime.now();
        parent = new Parent();
        parent.setAddress(address);
        parent.setEmail(email);
        parent.setCreatedDate(createdDate);
        parent.setPassword(password);
        parent.setGender(gender);
        parent.setPhoneNumber(phoneNumber);
        parent.setMaritalStatus(maritalStatus);
        parent.setLastModifiedDate(modifyDate);
        parent.setProfession(faker.company().profession());
        parent.setUserName(userName);


        CreateParentParameters createParentParameters= new CreateParentParameters();
        createParentParameters.setEmail(parent.getEmail());
        createParentParameters.setCreatedDate(parent.getCreatedDate());
        createParentParameters.setProfession(parent.getProfession());
        createParentParameters.setModifyDate(parent.getLastModifiedDate());
        createParentParameters.setMaritalStatus(parent.getMaritalStatus());
        createParentParameters.setPhoneNumber(parent.getPhoneNumber());
        createParentParameters.setUserName(parent.getUserName());
        createParentParameters.setAddress(parent.getAddress());
        createParentParameters.setPassword(parent.getPassword());
        createParentParameters.setGender(parent.getGender());
        return createParentParameters;
    }


    private CreateParentParameters createParentParametersForPere() {
        Name name = faker.name();
        UserName userName = new UserName(name.firstName(), name.lastName());
        Gender gender = Gender.MALE; // Vous pouvez définir le genre du père ici.
        LocalDate dateDeNaissance = LocalDate.ofInstant(faker.date().birthday(18, 50).toInstant(), ZoneId.systemDefault());
        Email email = new Email(faker.internet().emailAddress(generateEmailLocalPart(userName)));
        PhoneNumber phoneNumber = new PhoneNumber(faker.phoneNumber().phoneNumber());
        MaritalStatus maritalStatus = MaritalStatus.MARRIED; // Vous pouvez définir le statut matrimonial du père ici.
        Address address = new Address();
        address.setStreetNumber(Integer.valueOf(faker.address().streetAddressNumber()));
        address.setStreet(faker.address().streetAddress());
        address.setCountry(faker.address().country());
        address.setCity(faker.address().city());
        address.setCodePostale(Integer.valueOf(faker.address().zipCode()));
        String password = null;

        try {
            password = generateRandomPassword(12);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

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
        createParentParameters.setPassword(password);
        createParentParameters.setCreatedDate(createdDate);
        createParentParameters.setModifyDate(modifyDate);

        return createParentParameters;
    }


    private CreateParentParameters createParentParametersForMere() {
        Name name = faker.name();
        UserName userName = new UserName(name.firstName(), name.lastName());
        Gender gender = Gender.MALE; // Vous pouvez définir le genre du père ici.
        LocalDate dateDeNaissance = LocalDate.ofInstant(faker.date().birthday(18, 50).toInstant(), ZoneId.systemDefault());
        Email email = new Email(faker.internet().emailAddress(generateEmailLocalPart(userName)));
        PhoneNumber phoneNumber = new PhoneNumber(faker.phoneNumber().phoneNumber());
        MaritalStatus maritalStatus = MaritalStatus.MARRIED; // Vous pouvez définir le statut matrimonial du père ici.
        Address address = new Address();
        address.setStreetNumber(Integer.valueOf(faker.address().streetAddressNumber()));
        address.setStreet(faker.address().streetAddress());
        address.setCountry(faker.address().country());
        address.setCity(faker.address().city());
        address.setCodePostale(Integer.valueOf(faker.address().zipCode()));
        String password = null;

        try {
            password = generateRandomPassword(12);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

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
        createParentParameters.setPassword(password);
        createParentParameters.setCreatedDate(createdDate);
        createParentParameters.setModifyDate(modifyDate);

        return createParentParameters;
    }

}
