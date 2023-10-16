package ml.kalanblow.gestiondesinscriptions.service.impl;

import ml.kalanblow.gestiondesinscriptions.enums.Gender;
import ml.kalanblow.gestiondesinscriptions.enums.MaritalStatus;
import ml.kalanblow.gestiondesinscriptions.enums.UserRole;
import ml.kalanblow.gestiondesinscriptions.model.*;
import ml.kalanblow.gestiondesinscriptions.repository.EleveRepository;
import ml.kalanblow.gestiondesinscriptions.repository.UserRoleRepository;
import ml.kalanblow.gestiondesinscriptions.request.CreateEleveParameters;
import ml.kalanblow.gestiondesinscriptions.service.EleveService;
import ml.kalanblow.gestiondesinscriptions.util.CalculateUserAge;
import ml.kalanblow.gestiondesinscriptions.util.KaladewnUtility;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class EleveServiceImplTest {
    private EleveRepository eleveRepository;

    private EleveService eleveService;

    private UserRoleRepository userRoleRepository;
    @BeforeEach
    void initialisationService(){

        eleveRepository= mock(EleveRepository.class);
        eleveService= new EleveServiceImpl(eleveRepository, userRoleRepository);
        Eleve  eleve = new Eleve();
        eleve.setIneNumber(KaladewnUtility.generatingandomAlphaNumericStringWithFixedLength());
        eleve.setDateDeNaissance(LocalDate.of(1980, 6, 23));
        eleve.setAge(CalculateUserAge.calculateAge(eleve.getDateDeNaissance()));
        eleve.setMotherFirstName("Troubadour");
        eleve.setMotherLastName("Sissoko");
        eleve.setFatherFirstName("FantaMady");
        eleve.setFatherLastName("Sissoko");
        UserName userName = new UserName();
        userName.setPrenom("Adama");
        userName.setNomDeFamille("Traoré");
        eleve.setUserName(userName);
        eleve.setGender(Gender.MONSIEUR);
        eleve.setMaritalStatus(MaritalStatus.MARRIED);
        eleve.setEmail(new Email("test1@example.com"));
        eleve.setPhoneNumber(new PhoneNumber("0022367894326"));
        Address address = new Address();
        address.setStreet("Rue Abdoul Kabral Kamara");
        address.setCity("Bamako");
        address.setStreetNumber(34);
        address.setCodePostale(91410);
        address.setCountry("Mali");
        eleve.setAddress(address);
        eleve.setCreatedDate(LocalDateTime.now());
        eleve.setLastModifiedDate(LocalDateTime.now());
        eleve.setPassword("Homeboarding2014&");
        Set<Role> roles = new HashSet<>();
        Role role= new Role();
        role.setUserRole(UserRole.STUDENT);
        eleve.setRoles(roles);

      eleveRepository.save(eleve);
        System.out.println(eleve);
    }

    @Test
    @Disabled
    void verifierExistenceEmail() {
    }

    @Test
    @Disabled
    void chercherParEmail() {
    }

    @Test
    @Disabled
    void obtenirListeElevePage() {
    }

    @Test
    @Disabled
    void mettreAjourUtilisateur() {
    }

    @Test
    @Disabled
    void obtenirEleveParSonId() {
    }

    @Test
    @Disabled
    void chercherParSonNumeroIne() {
    }

    @Test
    @Disabled
    void getEleveByIneNumber() {
    }

    @Test
    void creationUtilisateur() {

        CreateEleveParameters createEleveParameters= new CreateEleveParameters();
        createEleveParameters.setModifyDate(LocalDateTime.now());
        createEleveParameters.setPhoneNumber(new PhoneNumber("0022367894326"));
        Set<Role> userRoles= new HashSet<>();
        Role role = new Role();
        role.setUserRole(UserRole.STUDENT);
        userRoles.add(role);
        createEleveParameters.setRoles(userRoles);
        createEleveParameters.setStudentIneNumber(KaladewnUtility.generatingandomAlphaNumericStringWithFixedLength());
        createEleveParameters.setDateDeNaissance(LocalDate.of(1980, 6, 23));

        createEleveParameters.setMotherFirstName("Troubadour");
        createEleveParameters.setMotherLastName("Sissoko");
        createEleveParameters.setFatherFirstName("FantaMady");
        createEleveParameters.setMotherMobile(new PhoneNumber("0022367894356"));
        createEleveParameters.setFatherMobile(new PhoneNumber("0022367894327"));
        createEleveParameters.setFatherLastName("Sissoko");
        UserName userName = new UserName();
        userName.setPrenom("Adama");
        userName.setNomDeFamille("Traoré");
        createEleveParameters.setUserName(userName);
        createEleveParameters.setGender(Gender.MONSIEUR);
        createEleveParameters.setMaritalStatus(MaritalStatus.MARRIED);
        createEleveParameters.setEmail(new Email("test@example.com"));
        createEleveParameters.setPhoneNumber(new PhoneNumber("0022367894326"));
        Address address = new Address();
        address.setStreet("Rue Abdoul Kabral Kamara");
        address.setCity("Bamako");
        address.setStreetNumber(34);
        address.setCodePostale(91410);
        address.setCountry("Mali");
        createEleveParameters.setAddress(address);
        createEleveParameters.setCreatedDate(LocalDateTime.now());
        createEleveParameters.setPassword("Homeboarding2014&");

        Eleve builder = getEleve(createEleveParameters);

        when(eleveRepository.save(any(Eleve.class))).thenReturn(builder);
        Eleve nouveauEleve=eleveService.CreationUtilisateur(createEleveParameters);
        assertNotNull(nouveauEleve);

    }

    private static Eleve getEleve(CreateEleveParameters createEleveParameters) {
        Eleve builder = new Eleve(createEleveParameters.getStudentIneNumber(), createEleveParameters.getDateDeNaissance(),
                createEleveParameters.getAge(), createEleveParameters.getMotherFirstName(), createEleveParameters.getMotherLastName()
                , createEleveParameters.getFatherLastName(), createEleveParameters.getFatherFirstName(), createEleveParameters.getEtablissementScolaire());

        builder.setRoles(createEleveParameters.getRoles());
        builder.setGender(createEleveParameters.getGender());
        builder.setCreatedDate(createEleveParameters.getCreatedDate());
        builder.setUserName(createEleveParameters.getUserName());
        builder.setPhoneNumber(createEleveParameters.getPhoneNumber());
        builder.setEmail(createEleveParameters.getEmail());
        return builder;
    }
}
