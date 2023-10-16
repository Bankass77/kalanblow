package ml.kalanblow.gestiondesinscriptions.config;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import ml.kalanblow.gestiondesinscriptions.enums.Gender;
import ml.kalanblow.gestiondesinscriptions.enums.MaritalStatus;
import ml.kalanblow.gestiondesinscriptions.enums.UserRole;
import ml.kalanblow.gestiondesinscriptions.model.*;
import ml.kalanblow.gestiondesinscriptions.repository.UserRoleRepository;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Component
@Profile("dev")
public class DatabaseInitializer implements CommandLineRunner {

    @Value("${database.initialization.enabled}")
    private boolean databaseInitializationEnabled;
    private final Faker faker = new Faker(new Locale("fr"));

    private final EleveService eleveService;
    private final EtablissementScolaireService etablissementScolaireService;

    private final UserRoleRepository  userRoleRepository;

    public DatabaseInitializer(EleveService eleveService, EtablissementScolaireService etablissementScolaireService,UserRoleRepository  userRoleRepository) {
        this.eleveService = eleveService;
        this.etablissementScolaireService = etablissementScolaireService;
        this.userRoleRepository=userRoleRepository;
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

            for (int i = 0; i < 20; i++) {
                CreateEleveParameters createEleveParameters = newRandomEleveParameters();
                eleveService.CreationUtilisateur(createEleveParameters);
            }
        }

    }

    private CreateEleveParameters newRandomEleveParameters() {

        Name name = faker.name();
        UserName userName = new UserName(name.firstName(), name.lastName());
        Gender gender = faker.bool().bool() ? Gender.MADAME : Gender.MONSIEUR;
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
        String password = faker.internet().password();
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
        Set<Role> roles = new HashSet<>();
        List<AbsenceEleve> absenceEleves = new ArrayList<>();
        Role role = new Role();
        role.setUserRole(UserRole.STUDENT);
        userRoleRepository.save(role);
        roles.add(role);

        EtablissementScolaire etablissementScolaire = etablissementScolaireService.trouverEtablissementScolaireParSonIdentifiant(2);
        CreateEtablissementScolaireParameters etablissementScolaireParameters= new EditEtablissementScolaireParameters(etablissementScolaire.getNomEtablissement(),etablissementScolaire.getAddress(),etablissementScolaire.getEmail(),etablissementScolaire.getCreatedDate(),etablissementScolaire.getLastModifiedDate(),etablissementScolaire.getPhoneNumber(),etablissementScolaire.getEleves(),etablissementScolaire.getEnseignants(),etablissementScolaire.getSalleDeClasses(),null);

        etablissementScolaireService.creerEtablissementScolaire(etablissementScolaireParameters);
        return new CreateEleveParameters(userName, gender, maritalStatus, email, password, phoneNumber, address,
                createdDate, modifyDate, dateDeNaissance, age, studentIneNumber, motherFirstName, motherLastName, motherMobile,
                fatherLastName, fatherFirstName, fatherMobile, roles, etablissementScolaire, absenceEleves);

    }

    private String generateEmailLocalPart(UserName userName) {

        return String.format("%s.%s", StringUtils.remove(userName.getPrenom().toLowerCase(), "'"), StringUtils.remove(userName.getNomDeFamille().toLowerCase(), "'"));

    }
}
