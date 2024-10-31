package ml.kalanblow.gestiondesinscriptions.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {


    @InjectMocks
    private AdminServiceImpl adminService;

    @Mock
    private AdministrateurRepository administrateurRepository;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void ajouterAdministrateur() {

        // Arrange : crréer un administrateur
        Administrateur administrateur = new Administrateur();
        User user = new User();
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

        //Mock: simule le comportement du repository pour le test
        Mockito.when(administrateurRepository.save(any(Administrateur.class))).thenReturn(administrateur);

        // Act: appelle de la méthode d'ajout
        Administrateur result = adminService.ajouterAdministrateur(administrateur);

        // Assert: vérifie que l'administrateur est bien ajouté
        assertNotNull(result);
        assertEquals("admintest@exemple.fr", result.getUser().getUserEmail().asString());
    }

    @Test
    void authentifierAdministrateur() {
        // Arrange : créer un administrateur avec un email et mot de passe spécifiques
        Administrateur administrateur = new Administrateur();
        User user = new User();
        user.setUserEmail(new Email("admintest@exemple.fr"));
        user.setPassword("securePassword");
        administrateur.setUser(user);

        // Mock : configuration du repository pour retourner l'administrateur
        Mockito.when(administrateurRepository.findByUserUserEmailEmail("admintest@exemple.fr"))
                .thenReturn(Optional.of(administrateur));

        // Act : appeler la méthode d'authentification du service
        Administrateur result = adminService.authentifierAdministrateur("admintest@exemple.fr", "securePassword");

        // Assert : vérifier que l'authentification a réussi
        assertNotNull(result, "L'administrateur ne doit pas être null après authentification.");
        assertEquals("admintest@exemple.fr", result.getUser().getUserEmail().getEmail(), "L'email de l'utilisateur devrait correspondre.");
    }

    @Test
    void supprimerAdministrateur() {

        // Arrange
        long adminId = 1L;
        // Act
        adminService.supprimerAdministrateur(adminId);
        //Assert
        verify(administrateurRepository, times(1)).deleteById(adminId);
    }

    @Test
    public void testFindAllAdministrateurs(){

        //arrange
        Administrateur administrateur = new Administrateur();
        User user = new User();
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

        Administrateur administrateur2 = new Administrateur();
        User user2 = new User();
        Set<UserRole> userRoles2 = new HashSet<>();
        userRoles2.add(UserRole.ADMIN);
        user2.setRoles(userRoles2);
        user2.setGender(Gender.MALE);
        user2.setUserEmail( new Email("admintest2@exemple.fr"));
        user2.setCreatedDate(LocalDateTime.now());
        user2.setLastModifiedDate(LocalDateTime.now());
        user2.setMaritalStatus(MaritalStatus.MARRIED);
        user2.setPassword("securePassword!");
        user2.setUser_phoneNumber( new PhoneNumber("0022366766887"));
        user2.setUserName(new UserName("Admin2", "Admin2"));
        Address address2 = new Address();
        address2.setCity("Bamako");
        address2.setCountry("Mali");
        address2.setStreet("Damonzon Diarra");
        address2.setCodePostale(99999);
        address2.setStreetNumber(189);
        user2.setAddress(address2);
        administrateur.setUser(user2);
        Set<Administrateur> administrateurs = new HashSet<>(Arrays.asList(administrateur, administrateur2));

        when( administrateurRepository.findAll()).thenReturn( new ArrayList<>(administrateurs));
        //Act
        Set<Administrateur>  results= adminService.getAllAdministrateurs();
        //Assert
        assertEquals(2, results.size());

    }

    @Test
    public void testUpdateAdministrateur() {
        // Arrange
        long adminId = 1L;
        Administrateur existingAdministrateur = new Administrateur();
        User user = new User();
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
        existingAdministrateur.setUser(user);

        Administrateur updatedAdmin = existingAdministrateur;
        user.setUserEmail(new Email("new@example.com"));
        updatedAdmin.setUser(user);
        updatedAdmin.setUser(user);
        when(administrateurRepository.findById(adminId)).thenReturn(Optional.of(existingAdministrateur));
        when(administrateurRepository.save(any(Administrateur.class))).thenReturn(updatedAdmin);

        // Act
        Administrateur result = adminService.updateAdministrateur(adminId, updatedAdmin);

        // Assert
        assertEquals("new@example.com", result.getUser().getUserEmail().asString());

    }
}