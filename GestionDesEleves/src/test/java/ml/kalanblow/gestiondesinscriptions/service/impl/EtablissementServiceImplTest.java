package ml.kalanblow.gestiondesinscriptions.service.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import ml.kalanblow.gestiondesinscriptions.model.Etablissement;
import ml.kalanblow.gestiondesinscriptions.repository.EtablissementRepository;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EtablissementServiceImplTest {
    @Mock
    private EtablissementRepository etablissementRepository;

    @InjectMocks
    private EtablissementServiceImpl etablissementService;

    private Etablissement etablissement;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        etablissement = new Etablissement();
        etablissement.setEtablisementScolaireId(1L);
        etablissement.setNomEtablissement("Test School");
        etablissement.setIdentiantEtablissement("TEST1234");
        etablissement.setCreatedDate(LocalDateTime.now());
        etablissement.setLastModifiedDate(LocalDateTime.now());
    }

    @Test
    public void testCreateEtablissement() {
        when(etablissementRepository.save(any(Etablissement.class))).thenReturn(etablissement);

        Etablissement createdEtablissement = etablissementService.createEtablissement(etablissement);

        assertNotNull(createdEtablissement);
        assertEquals("Test School", createdEtablissement.getNomEtablissement());
        verify(etablissementRepository, times(1)).save(etablissement);
    }

    @Test
    public void testUpdateEtablissement() {
        Long etablissementId = 1L;
        when(etablissementRepository.findByEtablisementScolaireId(etablissementId)).thenReturn(Optional.of(etablissement));
        when(etablissementRepository.save(any(Etablissement.class))).thenReturn(etablissement);

        Etablissement updatedEtablissement = etablissementService.updateEtablissement(etablissementId, etablissement);

        assertNotNull(updatedEtablissement);
        assertEquals("Test School", updatedEtablissement.getNomEtablissement());
        verify(etablissementRepository, times(1)).save(etablissement);
    }

    @Test
    public void testDeleteEtablissement() {
        Long etablissementId = 1L;
        when(etablissementRepository.findByEtablisementScolaireId(etablissementId)).thenReturn(Optional.of(etablissement));

        etablissementService.deleteEtablissement(etablissementId);

        verify(etablissementRepository, times(1)).delete(etablissement);
    }

    @Test
    public void testUploadLogo() throws IOException {
        Long etablissementId = 1L;
        byte[] logoBytes = {1, 2, 3, 4};
        MultipartFile logoFile = mock(MultipartFile.class);

        when(etablissementRepository.findByEtablisementScolaireId(etablissementId)).thenReturn(Optional.of(etablissement));
        when(logoFile.getBytes()).thenReturn(logoBytes);
        when(etablissementRepository.save(any(Etablissement.class))).thenReturn(etablissement);

        Etablissement etablissementWithLogo = etablissementService.uploadLogo(etablissementId, logoFile);

        assertNotNull(etablissementWithLogo);
        assertArrayEquals(logoBytes, etablissementWithLogo.getLogo());
        verify(etablissementRepository, times(1)).save(etablissement);
    }
}