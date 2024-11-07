
package ml.kalanblow.gestiondesinscriptions.service.impl;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.ClassRule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;

import ml.kalanblow.gestiondesinscriptions.enums.Gender;
import ml.kalanblow.gestiondesinscriptions.enums.MaritalStatus;
import ml.kalanblow.gestiondesinscriptions.enums.UserRole;
import ml.kalanblow.gestiondesinscriptions.model.Address;
import ml.kalanblow.gestiondesinscriptions.model.Administrateur;
import ml.kalanblow.gestiondesinscriptions.model.Email;
import ml.kalanblow.gestiondesinscriptions.model.PhoneNumber;
import ml.kalanblow.gestiondesinscriptions.model.User;
import ml.kalanblow.gestiondesinscriptions.model.UserName;
import ml.kalanblow.gestiondesinscriptions.repository.AdministrateurRepository;
import ml.kalanblow.gestiondesinscriptions.util.KalanblowPostgresqlContainer;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Disabled
class AdminServiceIntegrationTest {

    @Autowired
    private AdminServiceImpl adminService;

    @Autowired
    AdministrateurRepository administrateurRepository;

    User user = new User();

    Administrateur administrateur = new Administrateur();

    @ClassRule
    public static PostgreSQLContainer<KalanblowPostgresqlContainer> postgreSQLContainer = KalanblowPostgresqlContainer.getInstance();

    @BeforeAll
    static void beforAll(){
        postgreSQLContainer.start();
    }

    @AfterAll
    static  void afterAll(){
        postgreSQLContainer.stop();
    }

    @Test
    @Transactional
    void createAdminTest(){
        administrateur = new Administrateur();
            user = new User();
        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(UserRole.ADMIN);
        user.setRoles(userRoles);
        user.setGender(Gender.MALE);
        user.setUserEmail( new Email("admintest@exemple.fr"));
        user.setCreatedDate(LocalDateTime.now());
        user.setLastModifiedDate(LocalDateTime.now());
        user.setMaritalStatus(MaritalStatus.MARRIED);
        user.setPassword("securePassword");
        user.setUser_phoneNumber( new PhoneNumber("0022366766832"));
        user.setUserName(new UserName("Admin1", "Admin1"));
        Address address = new Address();
        address.setCity("Kayes");
        address.setCountry("Mali");
        address.setStreet("Damonzon Diarra");
        address.setCodePostale(99999);
        address.setStreetNumber(189);
        user.setAddress(address);
        administrateur.setUser(user);
        adminService.ajouterAdministrateur(administrateur);
        Optional<Administrateur> administrateur1= administrateurRepository.findById(administrateur.getAdminId());
        assertTrue(administrateur1.isPresent());
        assertNotNull(administrateur);

    }

    @Test
    void authentifierAdministrateur() {
    }

    @Test
    void supprimerAdministrateur() {
    }

    @Test
    void getAllAdministrateurs() {
    }

    @Test
    void updateAdministrateur() {
    }
}
