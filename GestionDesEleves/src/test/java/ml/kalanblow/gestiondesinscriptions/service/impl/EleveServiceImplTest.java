package ml.kalanblow.gestiondesinscriptions.service.impl;

import ml.kalanblow.gestiondesinscriptions.enums.Gender;
import ml.kalanblow.gestiondesinscriptions.enums.MaritalStatus;
import ml.kalanblow.gestiondesinscriptions.enums.UserRole;
import ml.kalanblow.gestiondesinscriptions.model.*;
import ml.kalanblow.gestiondesinscriptions.repository.EleveRepository;
import ml.kalanblow.gestiondesinscriptions.request.CreateEleveParameters;
import ml.kalanblow.gestiondesinscriptions.service.EleveService;
import ml.kalanblow.gestiondesinscriptions.util.CalculateUserAge;
import ml.kalanblow.gestiondesinscriptions.util.KaladewnUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class EleveServiceImplTest {
    private EleveRepository eleveRepository;

    private EleveService eleveService;


    @BeforeEach
    void initialisationService() {

        eleveRepository = mock(EleveRepository.class);
        eleveService = new EleveServiceImpl(eleveRepository);
        Eleve eleve = new Eleve();
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
        eleve.setGender(Gender.MALE);
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
        eleve.setRoles(Collections.singleton(UserRole.STUDENT));


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

        CreateEleveParameters createEleveParameters = new CreateEleveParameters();
        createEleveParameters.setModifyDate(LocalDateTime.now());
        createEleveParameters.setPhoneNumber(new PhoneNumber("0022367894326"));

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
        createEleveParameters.setGender(Gender.MALE);
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
        List<Absence> absenceEleves = new ArrayList<>();
        createEleveParameters.setAbsences(absenceEleves);
        Eleve builder = getEleve(createEleveParameters);

        when(eleveRepository.save(any(Eleve.class))).thenReturn(builder);
        Eleve nouveauEleve = eleveService.ajouterUnEleve(createEleveParameters);
        assertNotNull(nouveauEleve);

    }

    private static Eleve getEleve(CreateEleveParameters createEleveParameters) {
        Eleve builder = new Eleve();
        builder.setGender(createEleveParameters.getGender());
        builder.setCreatedDate(createEleveParameters.getCreatedDate());
        builder.setUserName(createEleveParameters.getUserName());
        builder.setPhoneNumber(createEleveParameters.getPhoneNumber());
        builder.setEmail(createEleveParameters.getEmail());
        builder.setIneNumber(createEleveParameters.getStudentIneNumber());
        builder.setDateDeNaissance(createEleveParameters.getDateDeNaissance());

        builder.setAge(createEleveParameters.getAge());
        builder.setMotherFirstName(createEleveParameters.getMotherFirstName());
        builder.setMotherLastName(createEleveParameters.getMotherLastName());
        builder.setFatherLastName(createEleveParameters.getFatherLastName());
        builder.setFatherFirstName(createEleveParameters.getFatherFirstName());
        builder.setAbsences(createEleveParameters.getAbsences());
        builder.setEtablissement(createEleveParameters.getEtablissement());

        return builder;
    }
}
