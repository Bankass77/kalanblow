package ml.kalanblow.gestiondesinscriptions.service.impl;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import ml.kalanblow.gestiondesinscriptions.model.AnneeScolaire;
import ml.kalanblow.gestiondesinscriptions.model.Classe;
import ml.kalanblow.gestiondesinscriptions.model.Etablissement;
import ml.kalanblow.gestiondesinscriptions.repository.AnneeScolaireRepository;
import ml.kalanblow.gestiondesinscriptions.repository.ClasseRepository;
import ml.kalanblow.gestiondesinscriptions.repository.EtablissementRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ClasseServiceImplTest {

    @Mock
    private ClasseRepository classeRepository;

    @Mock
    private AnneeScolaireRepository anneeScolaireRepository;

    @Mock
    private EtablissementRepository etablissementRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ClasseServiceImpl classeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createClasse() {
        Classe classe = new Classe();
        classe.setNom("6A");

        AnneeScolaire anneeScolaire = new AnneeScolaire();
        anneeScolaire.setAnneeScolaireId(1L);
        anneeScolaire.setAnneeScolaireDebut(2024);
        anneeScolaire.setAnneeScolaireFin(2025);
        classe.setAnneeScolaire(anneeScolaire);

        when(anneeScolaireRepository.findById(1L)).thenReturn(Optional.of(anneeScolaire));

        Etablissement etablissementScolaire = new Etablissement();
        etablissementScolaire.setEtablisementScolaireId(1L);
        etablissementScolaire.setNomEtablissement("Lycée de jeune Fille");
        classe.setEtablissement(etablissementScolaire);

        when(etablissementRepository.findByEtablisementScolaireId(anyLong())).thenReturn(Optional.of(etablissementScolaire));
        when(classeRepository.save(any(Classe.class))).thenReturn(classe);

        Classe newClasse = classeService.createClasse(classe);

        assertNotNull(newClasse);
        assertEquals("6A", newClasse.getNom());
        assertEquals("Lycée de jeune Fille", newClasse.getEtablissement().getNomEtablissement());
    }

    @Test
    void updateClasse() {
        Long classeId = 1L;
        Classe classe = new Classe();
        classe.setNom("6A");

        when(classeRepository.findById(classeId)).thenReturn(Optional.of(classe));
        when(classeRepository.save(any(Classe.class))).thenReturn(classe);

        Optional<Classe> updatedClasse = classeService.updateClasse(classeId, classe);

        assertNotNull(updatedClasse);
        verify(classeRepository, times(1)).save(classe);
    }

    @Test
    void deleteClasseById() {
        Long classeId = 1L;
        Classe classe = new Classe();
        classe.setNom("6A");
        classe.setClasseId(classeId);

        when(classeRepository.findClasseByClasseId(classeId)).thenReturn(Optional.of(classe));

        //TODO: à corriger l'appel de doNothing()
        //doNothing().when(classeRepository).deleteClasseById(classe.getClasseId());

        classeService.deleteClasseById(classeId);

        verify(classeRepository, times(1)).deleteClasseByClasseId(classeId);
    }

    @Test
    void findByClasseId() {
        Long classeId = 1L;
        Classe classe = new Classe();
        classe.setNom("6A");

        when(classeRepository.findById(classeId)).thenReturn(Optional.of(classe));

        Optional<Classe> foundClasse = classeService.findByClasseById(classeId);

        assertTrue(foundClasse.isPresent());
        assertEquals("6A", foundClasse.get().getNom());
    }

    @Test
    void findByNom() {
        String nomClasse = "6A";
        Classe classe = new Classe();
        classe.setNom(nomClasse);

        when(classeRepository.findClasseByNom(anyString())).thenReturn(List.of(classe));

        Optional<Classe> foundClasse = classeService.findByClasseName(nomClasse);

        assertTrue(foundClasse.isPresent());
        assertEquals(nomClasse, foundClasse.get().getNom());
    }

    @Test
    void findByEtablissement() {
        // Arrange: Création d'un établissement et de classes associées
        Long etablissementId = 1L;
        Etablissement etablissement = new Etablissement();
        etablissement.setEtablisementScolaireId(etablissementId);

        Classe classe = new Classe();
        classe.setNom("6A");
        classe.setEtablissement(etablissement);

        List<Classe> classes = List.of(classe);  // Liste contenant une classe

        // Simulation de la méthode findByEtablissement pour retourner la liste de classes
        when(classeRepository.findByEtablissement(any(Etablissement.class))).thenReturn(classes);

        // Act: Appel de la méthode de service
        List<Classe> foundClasses = classeService.findClasseByEtablissement(etablissement);

        // Assert: Vérification que la liste trouvée contient bien la classe attendue
        assertNotNull(foundClasses);
        assertEquals(1, foundClasses.size());
        assertEquals("6A", foundClasses.get(0).getNom());
    }

    @Test
    void findByAnneeScolaire() {
        AnneeScolaire anneeScolaire = new AnneeScolaire();
        anneeScolaire.setAnneeScolaireId(1L);
        Classe classe = new Classe();
        classe.setAnneeScolaire(anneeScolaire);
        when(classeRepository.findByAnneeScolaire(anneeScolaire)).thenReturn(List.of(classe));

        List<Classe> foundClasse = classeService.findByAnneeScolaire(anneeScolaire);

        assertEquals(1,foundClasse.size());
    }

    @Test
    void findByClasseIdAndEtablissement() {
        Long classeId = 1L;
        Etablissement etablissement = new Etablissement();
        etablissement.setEtablisementScolaireId(1L);

        when(classeRepository.findByClasseIdAndEtablissement(classeId, etablissement)).thenReturn(Optional.of(new Classe()));

        Optional<Classe> foundClasse = classeService.findByClasseIdAndEtablissement(classeId, etablissement);

        assertTrue(foundClasse.isPresent());
    }

    @Test
    void countByEtablissement() {
        Etablissement etablissement = new Etablissement();
        etablissement.setEtablisementScolaireId(1L);

        when(classeRepository.countByEtablissement(etablissement)).thenReturn(10L);

        long count = classeService.countByEtablissement(etablissement);

        assertEquals(10, count);
    }

    @Test
    void findByClasseName() {
        String nomClasse = "6A";
        Classe classe = new Classe();
        classe.setNom(nomClasse);

        when(classeRepository.findClasseByNom(nomClasse)).thenReturn(List.of(classe));

        Optional<Classe> foundClasse = classeService.findByClasseName(nomClasse);

        assertTrue(foundClasse.isPresent());
        assertEquals(nomClasse, foundClasse.get().getNom());
    }
}