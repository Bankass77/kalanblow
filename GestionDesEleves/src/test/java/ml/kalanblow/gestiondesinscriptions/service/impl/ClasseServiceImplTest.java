package ml.kalanblow.gestiondesinscriptions.service.impl;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import ml.kalanblow.gestiondesinscriptions.exception.KaladewnManagementException;
import ml.kalanblow.gestiondesinscriptions.model.Address;
import ml.kalanblow.gestiondesinscriptions.model.AnneeScolaire;
import ml.kalanblow.gestiondesinscriptions.model.ChefEtablissement;
import ml.kalanblow.gestiondesinscriptions.model.Classe;
import ml.kalanblow.gestiondesinscriptions.model.Etablissement;
import ml.kalanblow.gestiondesinscriptions.model.PhoneNumber;
import ml.kalanblow.gestiondesinscriptions.repository.AnneeScolaireRepository;
import ml.kalanblow.gestiondesinscriptions.repository.ClasseRepository;
import ml.kalanblow.gestiondesinscriptions.repository.EtablissementRepository;
import ml.kalanblow.gestiondesinscriptions.util.KaladewnUtility;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

//@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class ClasseServiceImplTest {

    @MockBean
    private ClasseRepository classeRepository;
    @MockBean
    private AnneeScolaireRepository anneeScolaireRepository;
    @MockBean
    private EtablissementRepository etablissementRepository;
    @MockBean
    private ModelMapper modelMapper;
    @MockBean
    private KaladewnManagementException exception;

    @Autowired
    private ClasseServiceImpl classeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createClasse() {

        //Arrange:Initialisation de l'objet Classe à tester
        Classe classe = new Classe();
        classe.setNom("6A");

        // Initialisation d'un mock d'anneeScolaire et configuration des retours
        AnneeScolaire anneeScolaire = new AnneeScolaire();
        anneeScolaire.setAnneeScolaireId(1L);
        anneeScolaire.setAnneeScolaireDebut(2024);
        anneeScolaire.setAnneeScolaireFin(2025);
        classe.setAnneeScolaire(anneeScolaire);
        // Simulation de la récupération de l'année scolaire existante
        Mockito.when(anneeScolaireRepository.findById(1L)).thenReturn(Optional.of(anneeScolaire));

        // Initialisation d'un mock d'etablissementScolaire
        Etablissement etablissementScolaire = new Etablissement();
        etablissementScolaire.setEtablisementScolaireId(1L);
        etablissementScolaire.setAddress(new Address());
        etablissementScolaire.setCreatedDate(LocalDateTime.now());
        etablissementScolaire.setLastModifiedDate(LocalDateTime.now());
        etablissementScolaire.setNomEtablissement("Lycéee de jeune Fille");
        etablissementScolaire.setPhoneNumber(new PhoneNumber("002236768795"));
        etablissementScolaire.setEnseignants(new HashSet<>());
        etablissementScolaire.setChefEtablissement(new ChefEtablissement());
        etablissementScolaire.setIdentiantEtablissement(KaladewnUtility.generatingandomAlphaNumericStringWithFixedLength());
         classe.setEtablissement(etablissementScolaire);

        // Simulation de la récupération de l'établissement existant
        Mockito.when(etablissementRepository.findByEtablisementScolaireId(1L)).thenReturn(etablissementScolaire);

        //Mock
        Mockito.when(classeRepository.save(any(Classe.class))).thenReturn(classe);

        //Act
        Classe newClasse = classeService.createClasse(classe);

        // Assert: Vérifier que la classe a bien été créée avec les valeurs attendues
        assertNotNull(newClasse, "La classe ne devrait pas être null après la création.");
        assertEquals("6A", newClasse.getNom(), "Le nom de la classe devrait être '6A'.");
        assertEquals("Lycéee de jeune Fille", newClasse.getEtablissement().getNomEtablissement(),
                "Le nom de l'établissement devrait être 'Lycéee de jeune Fille'.");
    }


    @Test
    void updateClasse() {
    }

    @Test
    void deleteClasse() {
    }

    @Test
    void findByClasseId() {
    }

    @Test
    void findByNom() {
    }

    @Test
    void findByEtablissement() {
    }

    @Test
    void findByAnneeScolaire() {
    }

    @Test
    void findByClasseIdAndEtablissement() {
    }

    @Test
    void countByEtablissement() {
    }

    @Test
    void findByClasseName() {
    }
}