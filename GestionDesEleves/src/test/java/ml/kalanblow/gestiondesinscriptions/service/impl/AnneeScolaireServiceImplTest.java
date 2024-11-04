package ml.kalanblow.gestiondesinscriptions.service.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.utility.TestcontainersConfiguration;

import ml.kalanblow.gestiondesinscriptions.exception.AnnneeScolaireAlreadyExistsException;
import ml.kalanblow.gestiondesinscriptions.exception.KaladewnManagementException;
import ml.kalanblow.gestiondesinscriptions.model.AnneeScolaire;
import ml.kalanblow.gestiondesinscriptions.repository.AnneeScolaireRepository;
import ml.kalanblow.gestiondesinscriptions.util.ErrorMessages;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@TestPropertySource(locations = "classpath:application.yaml")
@Import(TestcontainersConfiguration.class)
@ExtendWith(MockitoExtension.class)
class AnneeScolaireServiceImplTest {

    @Mock
    private AnneeScolaireRepository anneeScolaireRepository;

    @InjectMocks
    private AnneeScolaireServiceImpl anneeScolaireService;

    @Test
    void findByIdSuccess() {
        // Arrange
        long anneeScolaireId = 1L;
        AnneeScolaire anneeScolaire = new AnneeScolaire();
        anneeScolaire.setAnneeScolaireFin(2025);
        anneeScolaire.setAnneeScolaireDebut(2024);
        anneeScolaire.setAnneeScolaireId(anneeScolaireId);
        anneeScolaire.setClasses(new HashSet<>());
        anneeScolaire.setEleves(new HashSet<>());
        anneeScolaire.setVersion(1L);
        anneeScolaire.setAnneeScolaireId(anneeScolaireId);

        // Mock
        when(anneeScolaireRepository.findById(1L)).thenReturn(Optional.of(anneeScolaire));

        // Act
        Optional<AnneeScolaire> result = anneeScolaireService.findById(anneeScolaireId);
        verify(anneeScolaireRepository, times(1)).findById(anneeScolaireId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(anneeScolaireId, result.get().getAnneeScolaireId());
        assertEquals(2025, result.get().getAnneeScolaireFin());
        assertEquals(2024, result.get().getAnneeScolaireDebut());
        assertEquals(1L, result.get().getVersion());
        assertNotNull(result.get().getClasses());
        assertNotNull(result.get().getEleves());
    }

    @Test
    public void findByIdUnSuccess() {
        when(anneeScolaireRepository.findById(anyLong())).thenReturn(Optional.empty());

        AnnneeScolaireAlreadyExistsException exp = assertThrows(AnnneeScolaireAlreadyExistsException.class, () -> anneeScolaireService.findById(1L).orElseThrow(()->
                new AnnneeScolaireAlreadyExistsException(ErrorMessages.ERROR_AnnneeScolaire_NOT_FOUND + 1L)));
        assertEquals(ErrorMessages.ERROR_AnnneeScolaire_NOT_FOUND +1, exp.getMessage());
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
        anneeScolaire.setAnneeScolaireId(1L);

        // Mock
        when(anneeScolaireRepository.save(anneeScolaire)).thenReturn(anneeScolaire);

        // Act
        Optional<AnneeScolaire> result = anneeScolaireService.createNewAnneeScolaire(anneeScolaire);

        result.ifPresent(scolaire -> assertEquals(anneeScolaire, scolaire));

        // Assert
        assertNotNull(result);
        verify(anneeScolaireRepository, times(1)).save(anneeScolaire);

    }

    @Test
    void mettreAJourAnneeScolaire_avec_succes() {
        // Arrange
        long id = 1L;
        AnneeScolaire existingAnneeScolaire = new AnneeScolaire();
        existingAnneeScolaire.setAnneeScolaireDebut(2024);
        existingAnneeScolaire.setAnneeScolaireFin(2025);
        existingAnneeScolaire.setAnneeScolaireId(id);

        AnneeScolaire updateAnneeScolaire = new AnneeScolaire();
        updateAnneeScolaire.setAnneeScolaireDebut(2022);
        updateAnneeScolaire.setAnneeScolaireFin(2023);

        when(anneeScolaireRepository.findById(id)).thenReturn(Optional.of(existingAnneeScolaire));
        when(anneeScolaireRepository.save(any(AnneeScolaire.class))).thenReturn(updateAnneeScolaire);

        // Act
        Optional<AnneeScolaire> result = anneeScolaireService.mettreAJourAnneeScolaire(id, updateAnneeScolaire);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(2023, result.get().getAnneeScolaireFin());
        assertEquals(2022, result.get().getAnneeScolaireDebut());
    }

    @Test
    void testMettreAJourAnneeScolaire_ExceptionSiNonTrouve() {
        long id = 99L;
        AnneeScolaire updatedAnneeScolaire = new AnneeScolaire();

        when(anneeScolaireRepository.findById(id)).thenReturn(Optional.empty());

        AnnneeScolaireAlreadyExistsException exception = assertThrows(AnnneeScolaireAlreadyExistsException.class, () -> {
            anneeScolaireService.mettreAJourAnneeScolaire(id, updatedAnneeScolaire);
        });

        assertEquals(ErrorMessages.ERROR_AnnneeScolaire_NOT_FOUND + 99L, exception.getMessage());
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

        // Mock
        when(anneeScolaireRepository.findAll()).thenReturn(anneeScolaires);

        // Act
        List<AnneeScolaire> result = anneeScolaireService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify( anneeScolaireRepository,times(1)).findAll();

        // Vérifie les données du premier élément
        assertEquals(2023, result.get(0).getAnneeScolaireDebut());
        assertEquals(2024, result.get(0).getAnneeScolaireFin());

        // Vérifie les données du second élément
        assertEquals(2024, result.get(1).getAnneeScolaireDebut());
        assertEquals(2025, result.get(1).getAnneeScolaireFin());
    }

    @Test
    void findByAnneeScolaireDebutAndAnneeScolaireFin() {
        // Arrange
        int debut = 2024;
        int fin = 2025;
        AnneeScolaire anneeScolaire = new AnneeScolaire();
        anneeScolaire.setAnneeScolaireDebut(debut);
        anneeScolaire.setAnneeScolaireFin(fin);
        anneeScolaire.setAnneeScolaireId(1L);
        anneeScolaire.setClasses(new HashSet<>());
        anneeScolaire.setEleves(new HashSet<>());
        anneeScolaire.setVersion(1L);

        // Mock
        when(anneeScolaireRepository.findByAnneeScolaireDebutAndAnneeScolaireFin(debut, fin)).thenReturn(Optional.of(anneeScolaire));

        // Act
        Optional<AnneeScolaire> result = anneeScolaireService.findByAnneeScolaireDebutAndAnneeScolaireFin(debut, fin);

        // Assert
        result.ifPresent(scolaire -> assertEquals(anneeScolaire, scolaire));
        //result.isPresent(debut1 -> assertEquals(anneeScolaire.getAnneeScolaireDebut(), debut1));
        //assertEquals(fin, result.get().getAnneeScolaireFin());
    }

}