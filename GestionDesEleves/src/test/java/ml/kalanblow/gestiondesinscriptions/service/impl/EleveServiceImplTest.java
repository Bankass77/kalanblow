package ml.kalanblow.gestiondesinscriptions.service.impl;

import ml.kalanblow.gestiondesinscriptions.enums.Gender;
import ml.kalanblow.gestiondesinscriptions.enums.MaritalStatus;
import ml.kalanblow.gestiondesinscriptions.enums.UserRole;
import ml.kalanblow.gestiondesinscriptions.model.*;
import ml.kalanblow.gestiondesinscriptions.repository.EleveRepository;
import ml.kalanblow.gestiondesinscriptions.request.CreateEleveParameters;
import ml.kalanblow.gestiondesinscriptions.request.CreateParentParameters;
import ml.kalanblow.gestiondesinscriptions.service.EleveService;
import ml.kalanblow.gestiondesinscriptions.service.EtablissementService;
import ml.kalanblow.gestiondesinscriptions.service.ParentService;
import ml.kalanblow.gestiondesinscriptions.util.CalculateUserAge;
import ml.kalanblow.gestiondesinscriptions.util.KaladewnUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
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
@Disabled
class EleveServiceImplTest {
    private EleveRepository eleveRepository;
    private EleveService eleveService;
    private ParentService parentService;

    private EtablissementService etablissementService;

    private


    @BeforeEach
    void initialisationService() throws IOException {

        eleveRepository = mock(EleveRepository.class);
        parentService=mock(ParentService.class);
        etablissementService= mock(EtablissementService.class);
        eleveService = new EleveServiceImpl(eleveRepository,parentService,etablissementService);
        Eleve eleve = new Eleve();
        eleve.setIneNumber(KaladewnUtility.generatingandomAlphaNumericStringWithFixedLength());
        eleve.setDateDeNaissance(LocalDate.of(1980, 6, 23));
        eleve.setAge(CalculateUserAge.calculateAge(eleve.getDateDeNaissance()));

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
        Parent parent= new Parent();
        parent.setUserName(userName);
        parent.setProfession("Medecin");
        parent.setPassword(eleve.getPassword());
        parent.setAddress(address);
        parent.setRoles(Collections.singleton((UserRole.USER) ));
        CreateParentParameters createParentParameters= new CreateParentParameters();
        createParentParameters.setUserName(parent.getUserName());
        createParentParameters.setPhoneNumber(parent.getPhoneNumber());
        createParentParameters.setMaritalStatus(parent.getMaritalStatus());
        createParentParameters.setModifyDate(parent.getLastModifiedDate());
        createParentParameters.setProfession(parent.getProfession());
        createParentParameters.setAddress(parent.getAddress());
        createParentParameters.setCreatedDate(parent.getCreatedDate());
        createParentParameters.setAvatar(null);
        parentService.saveParent(createParentParameters);

        eleve.setPere(parent);


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
        createEleveParameters.setEtablissement(etablissementService.trouverEtablissementScolaireParSonIdentifiant(4002));
        createEleveParameters.setGender(Gender.MALE);
        createEleveParameters.setMaritalStatus(MaritalStatus.MARRIED);
        createEleveParameters.setEmail(new Email("test@example.com"));
        createEleveParameters.setPhoneNumber(new PhoneNumber("0022367894326"));
        createEleveParameters.setUserName(new UserName("Adama", "Traoré"));
        createEleveParameters.setCreatedDate(LocalDateTime.now());
        createEleveParameters.setPassword("Homeboarding2014&");

        Address address = new Address();
        address.setStreet("Rue Abdoul Kabral Kamara");
        address.setCity("Bamako");
        address.setStreetNumber(34);
        address.setCodePostale(91410);
        address.setCountry("Mali");

        createEleveParameters.setAddress(address);

        List<Absence> absenceEleves = new ArrayList<>();

        createEleveParameters.setAbsences(absenceEleves);

        Eleve builder = getEleve(createEleveParameters);


        Parent parent= new Parent();
        parent.setRoles(Collections.singleton(UserRole.USER));
        parent.setUserName(new UserName( "Kalifa", "Sissoko"));
        parent.setPassword("TYRTTUYgufuygi898");
        parent.setAddress(address);
        parent.setPhoneNumber(new PhoneNumber("0022367894326"));
        parent.setMaritalStatus(MaritalStatus.MARRIED);
        parent.setLastModifiedDate(LocalDateTime.now());
        parent.setCreatedDate(LocalDateTime.now());
        parent.setGender(Gender.MALE);
        createEleveParameters.setPere(parent);

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
        builder.setAbsences(createEleveParameters.getAbsences());

        return builder;
    }
}
