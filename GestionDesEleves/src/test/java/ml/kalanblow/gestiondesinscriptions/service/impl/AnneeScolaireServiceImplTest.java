package ml.kalanblow.gestiondesinscriptions.service.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import ml.kalanblow.gestiondesinscriptions.config.KaladewnPropertiesConfig;
import ml.kalanblow.gestiondesinscriptions.exception.KaladewnManagementException;
import ml.kalanblow.gestiondesinscriptions.model.AnneeScolaire;
import ml.kalanblow.gestiondesinscriptions.repository.AnneeScolaireRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnneeScolaireServiceImplTest {

    @Mock
    private AnneeScolaireRepository anneeScolaireRepository;

    @Mock
    private KaladewnPropertiesConfig propertiesConfig;

    @InjectMocks
    private AnneeScolaireServiceImpl anneeScolaireService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById() {

        // Arrange
        long anneeScolaireId = 1L;
        AnneeScolaire anneeScolaire = new AnneeScolaire();
        anneeScolaire.setAnneeScolaireFin(2025);
        anneeScolaire.setAnneeScolaireDebut(2024);
        anneeScolaire.setAnneeScolaireId(anneeScolaireId);
        anneeScolaire.setClasses(new HashSet<>());
        anneeScolaire.setEleves(new HashSet<>());
        anneeScolaire.setVersion(1L);

        //Mock
        when(anneeScolaireRepository.findById(anneeScolaireId)).thenReturn(Optional.of(anneeScolaire));

        //act
        Optional<AnneeScolaire> result = anneeScolaireService.findById(anneeScolaireId);

        assertNotNull(result);
        assertEquals(1L, result.get().getAnneeScolaireId());
    }

    @Test
    void findByAnneeScolaireDebutAndAnneeScolaireFin() {
    }

    @Test
    void findAll() {
        // Arrange
        AnneeScolaire anneeScolaire = new AnneeScolaire();
        anneeScolaire.setAnneeScolaireFin(2025);
        anneeScolaire.setAnneeScolaireDebut(2024);
        anneeScolaire.setClasses(new HashSet<>());
        anneeScolaire.setEleves(new HashSet<>());
        anneeScolaire.setVersion(1L);

        AnneeScolaire anneeScolaire2 = new AnneeScolaire();
        anneeScolaire2.setAnneeScolaireFin(2024);
        anneeScolaire2.setAnneeScolaireDebut(2023);
        anneeScolaire2.setClasses(new HashSet<>());
        anneeScolaire2.setEleves(new HashSet<>());
        anneeScolaire2.setVersion(1L);
        List<AnneeScolaire> anneeScolaires = Arrays.asList(anneeScolaire2, anneeScolaire);

        //mock
        when(anneeScolaireRepository.findAll()).thenReturn(anneeScolaires);

        //Act
        List<AnneeScolaire> result = anneeScolaireService.findAll();

        //Assert
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void createNewAnneeScolaire() {

        // Arrange
        AnneeScolaire anneeScolaire = new AnneeScolaire();
        anneeScolaire.setAnneeScolaireFin(2025);
        anneeScolaire.setAnneeScolaireDebut(2024);
        anneeScolaire.setClasses(new HashSet<>());
        anneeScolaire.setEleves(new HashSet<>());
        anneeScolaire.setVersion(1L);

        // Mock
        when(anneeScolaireRepository.save(any(AnneeScolaire.class))).thenReturn(anneeScolaire);

        //Act
        AnneeScolaire result = anneeScolaireService.createNewAnneeScolaire(anneeScolaire).get();

        //Assert
        assertNotNull(result);
    }

    @Test
    void mettreAJourAnneeScolaire_avec_succes() {

        //arrange
        long id = 1L;
        AnneeScolaire existingAnneeScolaire = new AnneeScolaire();
        existingAnneeScolaire.setAnneeScolaireDebut(2024);
        existingAnneeScolaire.setAnneeScolaireFin(2025);
        existingAnneeScolaire.setAnneeScolaireId(id);
        existingAnneeScolaire.setClasses(new HashSet<>());
        existingAnneeScolaire.setEleves(new HashSet<>());
        existingAnneeScolaire.setVersion(1L);

        AnneeScolaire updateAnneeScolaire = existingAnneeScolaire;
        updateAnneeScolaire.setAnneeScolaireDebut(2022);
        updateAnneeScolaire.setAnneeScolaireFin(2023);
        updateAnneeScolaire.setClasses(new HashSet<>());
        updateAnneeScolaire.setEleves(new HashSet<>());
        updateAnneeScolaire.setVersion(1L);

        when(anneeScolaireRepository.findById(id)).thenReturn(Optional.of(existingAnneeScolaire));
        when(anneeScolaireRepository.save(any(AnneeScolaire.class))).thenReturn(updateAnneeScolaire);

        //Act
        Optional<AnneeScolaire> result = anneeScolaireService.mettreAJourAnneeScolaire(id, updateAnneeScolaire);

        // Assert: Vérifier que le résultat n'est pas null et que les informations sont mises à jour
        assertTrue(result.isPresent(), "Le résultat ne devrait pas être null après la mise à jour.");
        assertEquals(2023, result.get().getAnneeScolaireFin(), "L'année scolaire de fin devrait être mise à jour à 2023.");
        assertEquals(2022, result.get().getAnneeScolaireDebut(), "L'année scolaire de début devrait être mise à jour à 2022.");
    }

    @Test
    void mettreAJourAnneeScolaire_ExceptionSiNonTrouve() {
        // Arrange
        long id = 99L;
        AnneeScolaire updatedAnneeScolaire = new AnneeScolaire();
        updatedAnneeScolaire.setAnneeScolaireDebut(2022);
        updatedAnneeScolaire.setAnneeScolaireFin(2023);

        // Mock: Configurer le repository pour lancer une exception si l'ID n'existe pas
        when(anneeScolaireRepository.findById(id)).thenReturn(Optional.empty());

        // Assert: Vérifier que l'exception est levée
        assertThrows(KaladewnManagementException.class, () -> {
            anneeScolaireService.mettreAJourAnneeScolaire(id, updatedAnneeScolaire);
        }, "L'année scolaire avec l'ID " + id + " n'a pas été trouvée");
    }
}