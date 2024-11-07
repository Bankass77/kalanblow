package ml.kalanblow.gestiondesinscriptions.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.TestPropertySource;

import ml.kalanblow.gestiondesinscriptions.enums.EtatEleve;
import ml.kalanblow.gestiondesinscriptions.enums.Gender;
import ml.kalanblow.gestiondesinscriptions.enums.MaritalStatus;
import ml.kalanblow.gestiondesinscriptions.enums.TypeParent;
import ml.kalanblow.gestiondesinscriptions.enums.UserRole;
import ml.kalanblow.gestiondesinscriptions.exception.EntityNotFoundException;
import ml.kalanblow.gestiondesinscriptions.model.Address;
import ml.kalanblow.gestiondesinscriptions.model.AnneeScolaire;
import ml.kalanblow.gestiondesinscriptions.model.Classe;
import ml.kalanblow.gestiondesinscriptions.model.Eleve;
import ml.kalanblow.gestiondesinscriptions.model.Email;
import ml.kalanblow.gestiondesinscriptions.model.Etablissement;
import ml.kalanblow.gestiondesinscriptions.model.Parent;
import ml.kalanblow.gestiondesinscriptions.model.PhoneNumber;
import ml.kalanblow.gestiondesinscriptions.model.User;
import ml.kalanblow.gestiondesinscriptions.model.UserName;
import ml.kalanblow.gestiondesinscriptions.repository.AnneeScolaireRepository;
import ml.kalanblow.gestiondesinscriptions.repository.ClasseRepository;
import ml.kalanblow.gestiondesinscriptions.repository.EleveRepository;
import ml.kalanblow.gestiondesinscriptions.repository.EtablissementRepository;
import ml.kalanblow.gestiondesinscriptions.repository.ParentRepository;
import ml.kalanblow.gestiondesinscriptions.util.CalculateUserAge;
import ml.kalanblow.gestiondesinscriptions.util.KaladewnUtility;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@TestPropertySource(locations = "classpath:application.yaml")
//@Import(TestcontainersConfiguration.class)
@EmbeddedKafka(bootstrapServersProperty = "spring.kafka.bootstrap-servers")
@Disabled
class EleveServiceImplTest {

    @Mock
    private EleveRepository eleveRepository;
    @Mock
    private EtablissementRepository etablissementRepository;
    @Mock
    private ClasseRepository classeRepository;
    @Mock
    private ParentRepository parentRepository;
    @Mock
    private AnneeScolaireRepository anneeScolaireRepository;
    @InjectMocks
    private EtablissementServiceImpl etablissementService;
    @InjectMocks
    private ClasseServiceImpl classeService;
    @InjectMocks
    private AnneeScolaireServiceImpl anneeScolaireService;
    @InjectMocks
    private EleveServiceImpl eleveService;
    @InjectMocks
    private ParentServiceImpl parentService;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByUserUsername() {
        // Arrange: Création d'un élève avec un nom d'utilisateur complet (prénom et nom de famille)
        Eleve eleve = new Eleve();
        User user = new User();
        UserName userName = new UserName("eleveTest", "eleveTest");
        user.setUserName(userName);
        eleve.setUser(user);

        // Simulation du comportement du repository pour retourner une liste d'élèves correspondant au nom complet
        Mockito.when(eleveRepository.findByUserUserNamePrenomAndUserUserNameNomDeFamille("eleveTest", "eleveTest"))
                .thenReturn(List.of(eleve));

        // Act: Appel du service pour retrouver l'élève par prénom et nom de famille
        List<Eleve> foundEleves = eleveService.findByUserUsername("eleveTest", "eleveTest");

        // Assert: Vérification que l'élève trouvé correspond à celui attendu
        assertNotNull(foundEleves);
        assertEquals(1, foundEleves.size());  // Vérifie que la liste contient un seul élève
        assertEquals("eleveTest", foundEleves.get(0).getUser().getUserName().getPrenom());
        assertEquals("eleveTest", foundEleves.get(0).getUser().getUserName().getNomDeFamille());
    }

    @Test
    void findUserByEmail() {
        // Arrange: Création d'un élève avec un email
        Eleve eleve = new Eleve();
        User user = new User();
        user.setUserEmail(new Email("eleveTest@school.com"));
        eleve.setUser(user);

        Mockito.when(eleveRepository.findByUserUserEmailEmail("eleveTest@school.com")).thenReturn(Optional.of(eleve));

        // Act: Recherche de l'élève par son email
        Optional<Eleve> foundEleve = eleveService.findUserByEmail("eleveTest@school.com");

        // Assert: Vérification que l'éléve est bien trouvé et que l'email correspond
        assertNotNull(foundEleve);
        assertEquals("eleveTest@school.com", foundEleve.get().getUser().getUserEmail().getEmail());
    }


    @Test
    void findUserByPhoneNumber() {
        // Arrange: Création d'un élève avec un numéro de téléphone
        Eleve eleve = new Eleve();
        User user = new User();
        user.setUser_phoneNumber(new PhoneNumber("00223789543"));
        eleve.setUser(user);

        Mockito.when(eleveRepository.findByUserUser_phoneNumberPhoneNumber("00223789543")).thenReturn(Optional.of(eleve));

        // Act: Recherche de l'élève par son numéro de téléphone
        Optional<Eleve> foundEleve = eleveService.findUserByPhoneNumber("00223789543");

        // Assert: Vérification que l'élève est bien retrouvé
        assertNotNull(foundEleve);
        assertEquals("00223789543", foundEleve.get().getUser().getUser_phoneNumber().asString());
    }

    @Test
    void findByNomAndClasse() {
        // Arrange: Création d'un élève avec une classe
        Eleve eleve = new Eleve();
        eleve.setUser(new User());
        eleve.getUser().setUserName(new UserName("Alpha", "TRAORE"));
        Classe classe = new Classe();
        classe.setNom("6A");
        eleve.setClasseActuelle(classe);

        Mockito.when(eleveRepository.findByUserUserNameNomDeFamilleAndClasse("TRAORE", "6A"))
                .thenReturn(List.of(eleve));

        // Act: Recherche de l'élève par son nom et sa classe
        List<Eleve> foundEleve = eleveService.findByNomAndClasse("TRAORE", "6A");

        // Assert: Vérification que l'élève trouvé a les bonnes valeurs
        assertNotNull(foundEleve);
        assertEquals("TRAORE", foundEleve.get(0).getUser().getUserName().getNomDeFamille());
        assertEquals("6A", foundEleve.get(0).getClasseActuelle().getNom());
    }

    @Test
    void findByIneNumber() {
        // Arrange: Création d'un élève avec un numéro INE
        Eleve eleve = new Eleve();
        eleve.setIneNumber("12345678");

        Mockito.when(eleveRepository.findByIneNumber("12345678")).thenReturn(Optional.of(eleve));

        // Act: Recherche de l'élève par son INE
        Optional<Eleve> foundEleve = eleveService.findByIneNumber("12345678");

        // Assert: Vérification que l'élève trouvé a bien le bon numéro INE
        assertNotNull(foundEleve);
        assertEquals("12345678", foundEleve.get().getIneNumber());
    }

    @Test
    void inscrireNouveauEleve() {
        // Arrange: Initialisation des objets nécessaires
        Eleve eleve = new Eleve();
        final User user = new User();
        user.setGender(Gender.FEMALE);
        user.setPassword("gYTTUhjhhbhknk");
        user.setMaritalStatus(MaritalStatus.SINGLE);
        user.setRoles(new HashSet<>(Set.of(UserRole.STUDENT)));
        user.setUser_phoneNumber(new PhoneNumber("00223789543"));
        user.setCreatedDate(LocalDateTime.now());
        user.setLastModifiedDate(LocalDateTime.now());
        user.setUserEmail(new Email("eleve2@lyceedessciences.fr"));
        user.setUserName(new UserName("eleve1", "eleve1"));

        Address address = new Address();
        address.setCity("Segou");
        address.setCountry("Mali");
        address.setCodePostale(99999);
        address.setStreetNumber(154);
        address.setStreet("rue Nieleni");
        user.setAddress(address);
        eleve.setUser(user);

        eleve.setIneNumber(KaladewnUtility.generatingandomAlphaNumericStringWithFixedLength());
        eleve.setDateInscription(LocalDateTime.now());
        eleve.setDateDeNaissance(LocalDate.of(1989, Month.APRIL, 25));
        eleve.setAge(CalculateUserAge.calculateAge(LocalDate.of(1989, Month.APRIL, 25)));
        eleve.setEtat(EtatEleve.NOUVEAU);

        Etablissement etablissement = new Etablissement();
        etablissement.setNomEtablissement("Test School");
        etablissement.setIdentiantEtablissement("TEST1234");
        etablissement.setPhoneNumber(new PhoneNumber("002237894532"));
        etablissement.setAddress(address);
        eleve.setEtablissement(etablissement);

        Classe classe = new Classe();
        classe.setClasseId(1L); // Simulez un ID valide
        classe.setNom("6A");

        AnneeScolaire anneeScolaire = new AnneeScolaire();
        anneeScolaire.setAnneeScolaireId(1L); // Simulez un ID valide
        anneeScolaire.setAnneeScolaireDebut(2024);
        anneeScolaire.setAnneeScolaireFin(2025);
        classe.setAnneeScolaire(anneeScolaire);
        classe.setEtablissement(etablissement);
        eleve.setClasseActuelle(classe);

        Parent pere = new Parent();
        pere.setProfession("Docteur");
        pere.setTypeParent(TypeParent.PERE);
        final User userParent = new User();
        userParent.setUserEmail(new Email("parenteleve1@exemple.com"));
        userParent.setPassword("yryttytfut56EZ");
        userParent.setGender(Gender.MALE);
        userParent.setUserName(new UserName("Alpha", "TRAORE"));
        userParent.setUser_phoneNumber(new PhoneNumber("0022374873256"));
        userParent.setAddress(address);
        pere.setUser(userParent);

        eleve.setParents(new HashSet<>(Set.of(pere)));

        // Simulation des appels de services et repository
        Mockito.when(eleveRepository.save(any(Eleve.class))).thenReturn(eleve);
        Mockito.when(etablissementService.trouverEtablissementScolaireParSonNom("Test School")).thenReturn(Optional.of(etablissement));
        Mockito.when(classeService.findClasseByNom("6A")).thenReturn(Collections.singletonList(classe));
        Mockito.when(anneeScolaireService.findAnneeScolaireById(1L)).thenReturn(Optional.of(anneeScolaire));

        // Act: Appel de la méthode de service
        Eleve newEleve = eleveService.inscrireNouveauEleve(eleve);

        // Assert: Vérification que l'élève a bien été inscrit
        assertNotNull(newEleve);
        assertEquals("Test School", newEleve.getEtablissement().getNomEtablissement());
        assertEquals("6A", newEleve.getClasseActuelle().getNom());
    }

    @Test
    void mettreAjourEleve() {
        // Arrange: Création d'un élève
        Eleve eleve = new Eleve();
        eleve.setEleveId(1L); // Ajout d'un ID pour la mise à jour
        eleve.setIneNumber("12345678");
        eleve.setEtat(EtatEleve.NOUVEAU);

        // Simulation de la recherche initiale de l'élève dans le repository
        Mockito.when(eleveRepository.findEleveByEleveId(eleve.getEleveId())).thenReturn(Optional.of(eleve));

        // Simulation de la sauvegarde de l'élève mis à jour
        eleve.setEtat(EtatEleve.PROMU);
        Mockito.when(eleveRepository.save(eleve)).thenReturn(eleve);

        // Act: Mise à jour de l'élève
        Eleve updatedEleve = eleveService.mettreAjourEleve(eleve.getEleveId(), eleve);

        // Assert: Vérification que l'état de l'élève a bien été mis à jour
        assertNotNull(updatedEleve);
        assertEquals(EtatEleve.PROMU, updatedEleve.getEtat());
    }

    @Test
    void supprimerEleve_ExistingEleve() {
        // Arrange: Création d'un élève simulé
        long eleveId = 1L;
        Eleve eleve = new Eleve();
        eleve.setEleveId(eleveId);

        // Configuration du mock pour retourner l'élève
        Mockito.when(eleveRepository.findEleveByEleveId(eleveId)).thenReturn(Optional.of(eleve));

        // Act & Assert: Suppression de l'élève sans exception
        assertDoesNotThrow(() -> eleveService.supprimerEleve(eleveId));
        Mockito.verify(eleveRepository, Mockito.times(1)).deleteEleveByEleveId(eleveId);
    }

    @Test
    void supprimerEleve_NonExistingEleve() {
        // Arrange: Élève non existant
        long eleveId = 1L;

        // Configuration du mock pour retourner un Optional.empty()
        Mockito.when(eleveRepository.findEleveByEleveId(eleveId)).thenReturn(Optional.empty());

        // Act & Assert: Vérification que l'exception EntityNotFoundException est levée
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> eleveService.supprimerEleve(eleveId));
        assertEquals(Eleve.class.getName() +" "+ 1 + " "+ "not found!", exception.getMessage());
    }

    @Test
    void getEleveParents() {
        // Arrange: Création d'un élève avec des parents
        Eleve eleve = new Eleve();
        eleve.setEleveId(1L); // Ajout d'un ID pour identifier l'élève
        Set<Parent> parents = new HashSet<>();
        Parent parent = new Parent();
        parent.setProfession("Docteur");
        parents.add(parent);
        eleve.setParents(parents);

        // Simulation du repository pour renvoyer l'élève avec ses parents
        Mockito.when(eleveRepository.findById(eleve.getEleveId())).thenReturn(Optional.of(eleve));

        // Act: Récupération des parents de l'élève
        Set<Eleve> foundParents = eleveService.getEleveParents(parent);

        // Assert: Vérification que l'élève a des parents associés
        assertNotNull(foundParents);
        //assertEquals(1, foundParents.size());
        //assertEquals("Docteur", foundParents.iterator().next().getParents().iterator().next().getProfession());
    }

    @Test
    void findEleveById() {
        // Arrange: Création d'un élève
        Eleve eleve = new Eleve();
        eleve.setIneNumber("12345678");
        eleve.setEleveId(1L);

        Mockito.when(eleveRepository.findEleveByEleveId(1L)).thenReturn(Optional.of(eleve));

        // Act: Recherche de l'élève par son ID
        Optional<Eleve> foundEleve = eleveService.FindEleveById(1L);

        // Assert: Vérification que l'élève est bien retrouvé
        assertNotNull(foundEleve);
        assertEquals("12345678", foundEleve.get().getIneNumber());
    }

    @Test
    void getElevesPagine() {
        // Arrange: Création d'une liste d'élèves paginée
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Eleve> page = new PageImpl<>(List.of(new Eleve(), new Eleve()));

        Mockito.when(eleveRepository.findAll(pageRequest)).thenReturn(page);

        // Act: Récupération des élèves paginés
        Page<Eleve> foundEleves = eleveService.getElevesPagine(pageRequest.getPageNumber(), pageRequest.getPageSize());

        // Assert: Vérification que la page contient des élèves
        assertNotNull(foundEleves);
        assertEquals(2, foundEleves.getTotalElements());
    }

    @Test
    void getElevesPagineParClasse() {
        // Arrange: Création d'une liste d'élèves paginée pour une classe donnée
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Eleve> page = new PageImpl<>(List.of(new Eleve(), new Eleve()));
        Classe classe = new Classe();
        classe.setNom("6A");

        AnneeScolaire anneeScolaire = new AnneeScolaire();
        anneeScolaire.setAnneeScolaireId(1L);
        anneeScolaire.setAnneeScolaireDebut(2024);
        anneeScolaire.setAnneeScolaireFin(2025);
        classe.setAnneeScolaire(anneeScolaire);
        Mockito.when(eleveRepository.findByClasseActuelle(classe, pageRequest)).thenReturn(page);

        // Act: Récupération des élèves de la classe "6A"
        Page<Eleve> foundEleves = eleveService.getElevesPagineParClasse(classe, pageRequest.getPageNumber(), pageRequest.getPageSize());

        // Assert: Vérification que la page contient des élèves
        assertNotNull(foundEleves);
        assertEquals(2, foundEleves.getTotalElements());
    }
}