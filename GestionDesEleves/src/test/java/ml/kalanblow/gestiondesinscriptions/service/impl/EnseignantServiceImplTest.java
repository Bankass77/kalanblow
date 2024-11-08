package ml.kalanblow.gestiondesinscriptions.service.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.TestPropertySource;


import ml.kalanblow.gestiondesinscriptions.enums.Gender;
import ml.kalanblow.gestiondesinscriptions.enums.MaritalStatus;
import ml.kalanblow.gestiondesinscriptions.enums.UserRole;
import ml.kalanblow.gestiondesinscriptions.model.Address;
import ml.kalanblow.gestiondesinscriptions.model.Disponibilite;
import ml.kalanblow.gestiondesinscriptions.model.Email;
import ml.kalanblow.gestiondesinscriptions.model.Enseignant;
import ml.kalanblow.gestiondesinscriptions.model.Etablissement;
import ml.kalanblow.gestiondesinscriptions.model.PhoneNumber;
import ml.kalanblow.gestiondesinscriptions.model.User;
import ml.kalanblow.gestiondesinscriptions.model.UserName;
import ml.kalanblow.gestiondesinscriptions.repository.EnseignantRepository;
import ml.kalanblow.gestiondesinscriptions.repository.EtablissementRepository;
import ml.kalanblow.gestiondesinscriptions.util.KaladewnUtility;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@TestPropertySource(locations = "classpath:application.yaml")
//@Import(TestcontainersConfiguration.class)
@EmbeddedKafka(bootstrapServersProperty = "spring.kafka.bootstrap-servers")
class EnseignantServiceImplTest {

    @InjectMocks
    private EnseignantServiceImpl enseignantService;
    @Mock
    private EnseignantRepository enseignantRepository;
    @Mock
    private  ModelMapper modelMapper;

    @Mock
    private EtablissementRepository etablissementRepository;

    @InjectMocks
    private EtablissementServiceImpl etablissementService;

    Enseignant enseignant = new Enseignant();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Préparation de l'établissement mocké
        Etablissement etablissementMock = new Etablissement();
        etablissementMock.setEtablisementScolaireId(1L);
        etablissementMock.setNomEtablissement("Lycée des Sciences");
        etablissementMock.setIdentiantEtablissement("LYC123456");
        Mockito.when(etablissementService.findEtablissementScolaireByIdentiantEtablissement("LYC123456"))
                .thenReturn(Optional.of(etablissementMock));

        // Configuration de l'enseignant pour le test
        enseignant.setEnseignantId(1L);
        enseignant.setDisponible(true);
        enseignant.setLeMatricule(KaladewnUtility.generatingandomAlphaNumericStringWithFixedLength());
        enseignant.setEtablissement(etablissementMock);

        // Configuration de l'utilisateur de l'enseignant
        User user = new User();
        user.setGender(Gender.FEMALE);
        user.setPassword("gYTTUhjhhbhknk");
        user.setMaritalStatus(MaritalStatus.SINGLE);
        user.setRoles(new HashSet<>(Set.of(UserRole.TEACHER)));
        user.setUser_phoneNumber(new PhoneNumber("00223789543"));
        user.setCreatedDate(LocalDateTime.now());
        user.setLastModifiedDate(LocalDateTime.now());
        user.setUserEmail(new Email("enseignant1@lyceedessciences.fr"));
        user.setUserName(new UserName("enseignant1", "enseignant1"));

        Address address = new Address();
        address.setCity("Segou");
        address.setCountry("Mali");
        address.setCodePostale(99999);
        address.setStreetNumber(154);
        address.setStreet("rue Nieleni");
        user.setAddress(address);
        enseignant.setUser(user);

        // Configuration des disponibilités
        Set<Disponibilite> disponibilites = new HashSet<>();
        Disponibilite disponibilite = new Disponibilite();
        disponibilite.setHeureDebut(LocalTime.of(9, 00));
        disponibilite.setHeureFin(LocalTime.of(18, 00));
        disponibilites.add(disponibilite);
        enseignant.setDisponibilites(Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY));
        enseignant.setHueresDisponibilites(Map.of(DayOfWeek.MONDAY, disponibilite));

        // Mock de l'enseignantRepository pour le test de création
        Mockito.when(enseignantRepository.save(Mockito.any(Enseignant.class))).thenReturn(enseignant);
    }

    @Test
    void createEnseignant() {

        Mockito.when(enseignantRepository.saveAndFlush(Mockito.any(Enseignant.class))).thenReturn(enseignant);
        //TODO: comment car nécessite une connexion à Kafka Zookeeper
        //verify(enseignantRepository, times(1)).saveAndFlush(enseignant);
        Enseignant newEnseignant = enseignantService.createEnseignant(enseignant);
        assertNotNull(newEnseignant);
        assertEquals(enseignant.getEnseignantId(), newEnseignant.getEnseignantId());
        assertEquals(enseignant.getLeMatricule(), newEnseignant.getLeMatricule());

    }

    @Test
    void updateEnseignant() {

        enseignant.setLeMatricule("NEW123456");
        Mockito.when(enseignantRepository.findById(1L)).thenReturn(Optional.of(enseignant));
        Mockito.when(enseignantRepository.save(Mockito.any(Enseignant.class))).thenReturn(enseignant);
        Enseignant result = enseignantService.updateEnseignant(1L, enseignant);

        assertEquals("NEW123456", result.getLeMatricule());
        verify(enseignantRepository, times(1)).save(enseignant);
    }

    @Test
    void findByLeMatricule() {
        Mockito.when(enseignantRepository.findByLeMatricule(enseignant.getLeMatricule())).thenReturn(Optional.of(enseignant));
        Optional<Enseignant> result = enseignantService.findByLeMatricule(enseignant.getLeMatricule());

        assertTrue(result.isPresent());
        assertEquals(enseignant.getEnseignantId(), result.get().getEnseignantId());
    }
    @Test
    void findByEtablissement() {
        Mockito.when(enseignantRepository.findByEtablissement(Mockito.any(Etablissement.class)))
                .thenReturn(List.of(enseignant));

        List<Enseignant> result = enseignantService.findByEtablissement(enseignant.getEtablissement());

        assertEquals(1, result.size());
        assertEquals(enseignant.getEnseignantId(), result.get(0).getEnseignantId());
    }

    @Test
    void findById() {
        Long enseignantId = 1L;
        Mockito.when(enseignantRepository.findById(enseignantId)).thenReturn(Optional.of(enseignant));

        Optional<Enseignant> result = enseignantService.findEnseignantById(enseignantId);

        assertTrue(result.isPresent());
        assertEquals(enseignant.getEnseignantId(), result.get().getEnseignantId());
        verify(enseignantRepository, times(1)).findById(enseignantId);
    }

    @Test
    void searchAllByEmailIsLike() {
        Email email = new Email("enseignant1@lyceedessciences.fr");
        Mockito.when(enseignantRepository.findByUserEmail(email.asString())).thenReturn(Optional.of(enseignant));

        Optional<Enseignant> result = enseignantService.searchAllByEmailIsLike(email);

        assertTrue(result.isPresent());
        assertEquals(email.asString(), result.get().getUser().getUserEmail().asString());
        verify(enseignantRepository, times(1)).findByUserEmail(email.asString());
    }

    @Test
    void getEnseignantByUserCreatedDateIsBetween() {
        LocalDate debut = LocalDate.of(2023, 1, 1);
        LocalDate fin = LocalDate.of(2023, 12, 31);
        Mockito.when(enseignantRepository.getEnseignantByUserCreatedDateIsBetween(debut, fin))
                .thenReturn(List.of(enseignant));

        List<Enseignant> result = enseignantService.getEnseignantByUserCreatedDateIsBetween(debut, fin);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(enseignantRepository, times(1)).getEnseignantByUserCreatedDateIsBetween(debut, fin);
    }

    @Test
    void getEnseignantByCoursDEnseignementsAndHeureDebutDisponibiliteAndAndHeureFinDisponibilite() {
        DayOfWeek jourDisponible = DayOfWeek.MONDAY;
        LocalTime heureDebut = LocalTime.of(9, 0);
        LocalTime heureFin = LocalTime.of(18, 0);
        Disponibilite disponibilite = new Disponibilite();
        disponibilite.setHeureDebut(heureDebut);
        disponibilite.setHeureFin(heureFin);
        enseignant.setHueresDisponibilites(Map.of(jourDisponible, disponibilite));

        Optional<Enseignant> result = enseignantService.getEnseignantByCoursDEnseignementsAndHeureDebutDisponibiliteAndAndHeureFinDisponibilite(
                enseignant, jourDisponible, heureDebut, heureFin);

        assertTrue(result.isPresent());
        assertEquals(enseignant.getEnseignantId(), result.get().getEnseignantId());
    }

    @Test
    void getAllEnseignants() {
        Mockito.when(enseignantRepository.findAll()).thenReturn(List.of(enseignant));

        Set<Enseignant> result = enseignantService.getAllEnseignants();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(enseignantRepository, times(1)).findAll();
    }

    @Test
    void deleteById() {
        Long enseignantId = 1L;
        Mockito.when(enseignantRepository.findById(enseignantId)).thenReturn(Optional.of(enseignant));
        enseignantService.deleteEnseignant(enseignantId);
        verify(enseignantRepository, times(1)).delete(enseignant);
    }

    @Test
    void deleteEnseignant() {
        Mockito.when(enseignantRepository.findById(enseignant.getEnseignantId())).thenReturn(Optional.of(enseignant));
        enseignantService.deleteEnseignant(enseignant.getEnseignantId());
        verify(enseignantRepository, times(1)).delete(enseignant);
    }

    @Test
    void getEnseignantsDisponibles() {
        DayOfWeek jourDisponible = DayOfWeek.MONDAY;
        LocalTime heureDebut = LocalTime.of(9, 0);
        LocalTime heureFin = LocalTime.of(18, 0);
        Disponibilite disponibilite = new Disponibilite();
        disponibilite.setHeureDebut(heureDebut);
        disponibilite.setHeureFin(heureFin);
        enseignant.setHueresDisponibilites(Map.of(jourDisponible, disponibilite));
        Mockito.when(enseignantRepository.findAll()).thenReturn(List.of(enseignant));

        List<Enseignant> result = enseignantService.getEnseignantsDisponibles(jourDisponible, heureDebut, heureFin);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(enseignant.getEnseignantId(), result.get(0).getEnseignantId());
    }

}