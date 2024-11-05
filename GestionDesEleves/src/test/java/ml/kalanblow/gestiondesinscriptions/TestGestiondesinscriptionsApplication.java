
package ml.kalanblow.gestiondesinscriptions;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.ClassRule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import ml.kalanblow.gestiondesinscriptions.enums.Gender;
import ml.kalanblow.gestiondesinscriptions.enums.MaritalStatus;
import ml.kalanblow.gestiondesinscriptions.enums.UserRole;
import ml.kalanblow.gestiondesinscriptions.exception.EntityNotFoundException;
import ml.kalanblow.gestiondesinscriptions.exception.KaladewnManagementException;
import ml.kalanblow.gestiondesinscriptions.model.Address;
import ml.kalanblow.gestiondesinscriptions.model.Administrateur;
import ml.kalanblow.gestiondesinscriptions.model.Email;
import ml.kalanblow.gestiondesinscriptions.model.PhoneNumber;
import ml.kalanblow.gestiondesinscriptions.model.User;
import ml.kalanblow.gestiondesinscriptions.model.UserName;
import ml.kalanblow.gestiondesinscriptions.service.impl.AdminServiceImpl;
import ml.kalanblow.gestiondesinscriptions.util.KalanblowPostgresqlContainer;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestConfiguration(proxyBeanMethods = false)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class TestGestiondesinscriptionsApplication {


    @Autowired
    private AdminServiceImpl adminService;

  // @Container
    @ClassRule
    public static PostgreSQLContainer<KalanblowPostgresqlContainer> postgreSQLContainer = KalanblowPostgresqlContainer.getInstance();

    /*
    @DynamicPropertySource
    public static void ovverideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.driver-class-name", postgreSQLContainer::getDriverClassName);
    }

    @BeforeAll
    public static void setUp() {
        postgreSQLContainer.withReuse(true);
        postgreSQLContainer.start();
    }
*/
    @Test
    @Transactional
    void createAdminTest() {
        Administrateur administrateur = new Administrateur();
        User user = new User();
        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(UserRole.ADMIN);
        user.setRoles(userRoles);
        user.setGender(Gender.MALE);
        user.setUserEmail(new Email("admintest@exemple.fr"));
        user.setCreatedDate(LocalDateTime.now());
        user.setLastModifiedDate(LocalDateTime.now());
        user.setMaritalStatus(MaritalStatus.MARRIED);
        user.setPassword("securePassword");
        user.setUser_phoneNumber(new PhoneNumber("0022366766832"));
        user.setUserName(new UserName("Admin1", "Admin1"));
        Address address = new Address();
        address.setCity("Kayes");
        address.setCountry("Mali");
        address.setStreet("Damonzon Diarra");
        address.setCodePostale(99999);
        address.setStreetNumber(189);
        user.setAddress(address);
        administrateur.setUser(user);
		Administrateur admin= adminService.ajouterAdministrateur(administrateur);
        assertNotNull(admin);

    }

	@Test
	public void testFindAdminByEmailFound(){
		Administrateur administrateur = new Administrateur();
		User user = new User();
		Set<UserRole> userRoles = new HashSet<>();
		userRoles.add(UserRole.ADMIN);
		user.setRoles(userRoles);
		user.setGender(Gender.MALE);
		user.setUserEmail(new Email("admintest@exemple.fr"));
		user.setCreatedDate(LocalDateTime.now());
		user.setLastModifiedDate(LocalDateTime.now());
		user.setMaritalStatus(MaritalStatus.MARRIED);
		user.setPassword("securePassword");
		user.setUser_phoneNumber(new PhoneNumber("0022366766832"));
		user.setUserName(new UserName("Admin1", "Admin1"));
		Address address = new Address();
		address.setCity("Kayes");
		address.setCountry("Mali");
		address.setStreet("Damonzon Diarra");
		address.setCodePostale(99999);
		address.setStreetNumber(189);
		user.setAddress(address);
		administrateur.setUser(user);
		Administrateur admin= adminService.ajouterAdministrateur(administrateur);
        assertEquals("admintest@exemple.fr", admin.getUser().getUserEmail().getEmail());
	}

	@Test
	public void testFindAdminByIdNotFound(){
		Exception exception = assertThrows(
				EntityNotFoundException.class,
				() -> adminService.supprimerAdministrateur(1L));
		assertEquals(Administrateur.class.getName()+" "+ 1 + " "+ "not found!", exception.getMessage());
	}

    @Bean
    @ServiceConnection
    RabbitMQContainer rabbitContainer() {
        return new RabbitMQContainer(DockerImageName.parse("rabbitmq:latest"));
    }


   /* @AfterAll
    public static void tearDown() {
        postgreSQLContainer.stop();
    }
*/
    public static void main(String[] args) {
        SpringApplication.from(GestiondesinscriptionsApplication::main).with(TestGestiondesinscriptionsApplication.class).run(args);
    }

}

