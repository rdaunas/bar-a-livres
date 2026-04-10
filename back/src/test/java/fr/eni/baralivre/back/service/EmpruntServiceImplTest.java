package fr.eni.baralivre.back.service;

import fr.eni.baralivre.back.entity.Emprunt;
import fr.eni.baralivre.back.repository.EmpruntRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmpruntServiceImplTest {
    @Mock
    private EmpruntRepository empruntRepository;

    @InjectMocks
    private EmpruntServiceImpl empruntService;

    private Emprunt emprunt;

    @BeforeEach
    void setUp() {
        emprunt = Emprunt.builder()
                .id(1)
                .userId(100)
                .livreIsbn(1234567890L)
                .build();
    }

    @Test
    void chargerUnEmprunt_devraitRetournerEmprunt_quandIdValide() {
        // Given
        int id = 1;
        when(empruntRepository.findEmpruntById(id)).thenReturn(List.of(emprunt));

        // When
        Emprunt result = empruntService.chargerUnEmprunt(id);

        // Then
        assertNotNull(result);
        assertEquals(emprunt.getId(), result.getId());
        assertEquals(emprunt.getUserId(), result.getUserId());
    }

    @Test
    void chargerUnEmprunt_devraitLeverException_quandIdInvalide() {
        // Given
        int id = 0;

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> empruntService.chargerUnEmprunt(id));
        assertEquals("id invalide", exception.getMessage());
    }

    @Test
    void chargerUnEmprunt_devraitLeverException_quandIdNegatif() {
        // Given
        int id = -1;

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> empruntService.chargerUnEmprunt(id));
        assertEquals("id invalide", exception.getMessage());
    }

    @Test
    void chargerUnEmprunt_devraitLeverException_quandEmpruntNonTrouve() {
        // Given
        int id = 999;
        when(empruntRepository.findEmpruntById(id)).thenReturn(Collections.emptyList());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> empruntService.chargerUnEmprunt(id));
        assertEquals("emprunt not found", exception.getMessage());
    }

    @Test
    void chargerToutLesEmprunt_devraitRetournerListeVide_quandAucunEmprunt() {
        // Given
        when(empruntRepository.findAll()).thenReturn(Collections.emptyList());

        // When
        List<Emprunt> result = empruntService.chargerToutLesEmprunt();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void chargerToutLesEmprunt_devraitRetournerTousLesEmprunts() {
        // Given
        Emprunt emprunt2 = Emprunt.builder()
                .id(2)
                .userId(200)
                .livreIsbn(9876543210L)
                .build();
        List<Emprunt> emprunts = List.of(emprunt, emprunt2);
        when(empruntRepository.findAll()).thenReturn(emprunts);

        // When
        List<Emprunt> result = empruntService.chargerToutLesEmprunt();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(emprunts, result);
    }

    @Test
    void chargerToutLesEmpruntParUserId_devraitRetournerEmprunts_quandUserIdValide() {
        // Given
        Integer userId = 100;
        when(empruntRepository.findEmpruntByUserId(userId)).thenReturn(List.of(emprunt));

        // When
        List<Emprunt> result = empruntService.chargerToutLesEmpruntParUserId(userId);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(emprunt, result.get(0));
    }

    @Test
    void chargerToutLesEmpruntParUserId_devraitRetournerListeVide_quandAucunEmprunt() {
        // Given
        Integer userId = 999;
        when(empruntRepository.findEmpruntByUserId(userId)).thenReturn(Collections.emptyList());

        // When
        List<Emprunt> result = empruntService.chargerToutLesEmpruntParUserId(userId);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void chargerToutLesEmpruntParUserId_devraitLeverException_quandUserIdNull() {
        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> empruntService.chargerToutLesEmpruntParUserId(null));
        assertEquals("Utilisateur inconnu", exception.getMessage());
    }

    @Test
    void chargerLesStatusDesEmpruntParUserId_devraitRetournerEmpruntsEnRetard() {
        // Given
        Integer userId = 100;
        when(empruntRepository.findEmpruntByUserIdAndStatus_TypeStatusAndDateRetourPrevisionnelBefore(
                eq(userId), eq("En cours"), any(LocalDateTime.class)))
                .thenReturn(List.of(emprunt));

        // When
        List<Emprunt> result = empruntService.chargerLesStatusDesEmpruntParUserId(userId);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(emprunt, result.get(0));
    }

    @Test
    void chargerLesStatusDesEmpruntParUserId_devraitRetournerListeVide_quandAucunEmpruntEnRetard() {
        // Given
        Integer userId = 100;
        when(empruntRepository.findEmpruntByUserIdAndStatus_TypeStatusAndDateRetourPrevisionnelBefore(
                eq(userId), eq("En cours"), any(LocalDateTime.class)))
                .thenReturn(Collections.emptyList());

        // When
        List<Emprunt> result = empruntService.chargerLesStatusDesEmpruntParUserId(userId);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void chargerLesStatusDesEmpruntParUserId_devraitLeverException_quandUserIdNull() {
        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> empruntService.chargerLesStatusDesEmpruntParUserId(null));
        assertEquals("Utilisateur inconnu", exception.getMessage());
    }

}
