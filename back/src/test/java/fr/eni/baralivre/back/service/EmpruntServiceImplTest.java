package fr.eni.baralivre.back.service;

import fr.eni.baralivre.back.entity.Emprunt;
import fr.eni.baralivre.back.entity.Status;
import fr.eni.baralivre.back.repository.EmpruntRepository;
import fr.eni.baralivre.back.repository.StatusRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmpruntServiceImplTest {
    @Mock
    private EmpruntRepository empruntRepository;
    @Mock
    private StatusRepository statusRepository;

    @InjectMocks
    private EmpruntServiceImpl empruntService;

    private Emprunt emprunt;

    @BeforeEach
    void setUp() {
        emprunt = Emprunt.builder()
                .id(1)
                .userId(1)
                .livreIsbn("1234567890L")
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
                .livreIsbn("9876543210L")
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
        Integer userId = 1;
        when(empruntRepository.findEmpruntByUserId(userId)).thenReturn(List.of(emprunt));

        // When
        List<Emprunt> result = empruntService.chargerToutLesEmpruntParUserId(userId);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(emprunt, result.getFirst());
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
        Integer userId = 1;
        when(empruntRepository.findEmpruntByUserIdAndStatus_TypeStatusAndDateRetourPrevisionnelBefore(
                eq(userId), eq("En cours"), any(LocalDateTime.class)))
                .thenReturn(List.of(emprunt));

        // When
        List<Emprunt> result = empruntService.chargerLesStatusDesEmpruntParUserId(userId);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(emprunt, result.getFirst());
    }

    @Test
    void chargerLesStatusDesEmpruntParUserId_devraitRetournerListeVide_quandAucunEmpruntEnRetard() {
        // Given
        Integer userId = 1;
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
    @Test
    void creerEmprunt_devraitCreerEmprunt_quandConditionsValides() {
        // Given
        Integer userId = 1;
        String livreIsbn = "1234567890L";

        when(empruntRepository.findEmpruntByUserIdAndStatus_TypeStatus(userId, "En cours"))
                .thenReturn(Collections.emptyList());
        when(empruntRepository.findEmpruntByUserIdAndStatus_TypeStatusAndDateRetourPrevisionnelBefore(
                eq(userId), eq("En cours"), any(LocalDateTime.class)))
                .thenReturn(Collections.emptyList());

        Emprunt empruntSauvegarde = Emprunt.builder()
                .id(1)
                .userId(userId)
                .livreIsbn(livreIsbn)
                .build();

        when(empruntRepository.save(any(Emprunt.class))).thenReturn(empruntSauvegarde);

        // When
        Emprunt result = empruntService.creerEmprunt(userId, livreIsbn);

        // Then
        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        assertEquals(livreIsbn, result.getLivreIsbn());
        verify(empruntRepository).save(any(Emprunt.class));
    }

    @Test
    void creerEmprunt_devraitLeverException_quandUserIdNull() {
        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> empruntService.creerEmprunt(null, "1234567890L"));
        assertEquals("Utilisateur inconnu", exception.getMessage());
    }

    @Test
    void creerEmprunt_devraitLeverException_quandLivreIsbnNull() {
        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> empruntService.creerEmprunt(1, null));
        assertEquals("ISBN du livre invalide", exception.getMessage());
    }

    @Test
    void creerEmprunt_devraitLeverException_quandMax3Emprunts() {
        // Given
        Integer userId = 1;
        String livreIsbn = "1234567890L";

        List<Emprunt> empruntsEnCours = List.of(
                Emprunt.builder().id(1).build(),
                Emprunt.builder().id(2).build(),
                Emprunt.builder().id(3).build()
        );

        when(empruntRepository.findEmpruntByUserIdAndStatus_TypeStatus(userId, "En cours"))
                .thenReturn(empruntsEnCours);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> empruntService.creerEmprunt(userId, livreIsbn));
        assertEquals("Vous avez atteint le nombre maximum d'emprunts", exception.getMessage());
    }

    @Test
    void creerEmprunt_devraitLeverException_quandEmpruntsEnRetard() {
        // Given
        Integer userId = 1;
        String livreIsbn = "1234567890L";

        Emprunt empruntRetard = Emprunt.builder()
                .id(1)
                .userId(userId)
                .dateRetourPrevisionnel(LocalDateTime.now().minusDays(5))
                .build();

        when(empruntRepository.findEmpruntByUserIdAndStatus_TypeStatus(userId, "En cours"))
                .thenReturn(Collections.emptyList());
        when(empruntRepository.findEmpruntByUserIdAndStatus_TypeStatusAndDateRetourPrevisionnelBefore(
                eq(userId), eq("En cours"), any(LocalDateTime.class)))
                .thenReturn(List.of(empruntRetard));

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> empruntService.creerEmprunt(userId, livreIsbn));
        assertEquals("Vous avez des emprunts en retard", exception.getMessage());
    }

    @Test
    void retournerEmprunt_devraitRetournerEmprunt_quandEmpruntEnCours() {
        // Given
        int empruntId = 1;

        Status statusEnCours = Status.builder()
                .id(1)
                .typeStatus("En cours")
                .build();

        Status statusRetourne = Status.builder()
                .id(2)
                .typeStatus("Retourné")
                .build();

        Emprunt empruntEnCours = Emprunt.builder()
                .id(empruntId)
                .userId(1)
                .livreIsbn("1234567890L")
                .status(statusEnCours)
                .dateRetourPrevisionnel(LocalDateTime.now().plusDays(7))
                .build();

        when(empruntRepository.findEmpruntById(empruntId))
                .thenReturn(List.of(empruntEnCours));
        when(statusRepository.findByTypeStatus("Terminé"))
                .thenReturn(Optional.of(statusRetourne));
        when(empruntRepository.save(any(Emprunt.class)))
                .thenReturn(empruntEnCours);

        // When
        Emprunt result = empruntService.retournerEmprunt(empruntId);

        // Then
        assertNotNull(result);
        verify(empruntRepository).save(any(Emprunt.class));
        verify(statusRepository).findByTypeStatus("Terminé");
    }

    @Test
    void retournerEmprunt_devraitLeverException_quandIdInvalide() {
        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> empruntService.retournerEmprunt(0));
        assertEquals("id d'emprunt invalide", exception.getMessage());
    }

    @Test
    void retournerEmprunt_devraitLeverException_quandEmpruntNonEnCours() {
        // Given
        int empruntId = 1;

        Status statusRetourne = Status.builder()
                .id(2)
                .typeStatus("Retourné")
                .build();

        Emprunt empruntDejaRetourne = Emprunt.builder()
                .id(empruntId)
                .userId(1)
                .status(statusRetourne)
                .build();

        when(empruntRepository.findEmpruntById(empruntId))
                .thenReturn(List.of(empruntDejaRetourne));

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> empruntService.retournerEmprunt(empruntId));
        assertEquals("Emprunt non en cours", exception.getMessage());
    }

    @Test
    void retournerEmprunt_devraitLeverException_quandStatusRetourneNonTrouve() {
        // Given
        int empruntId = 1;

        Status statusEnCours = Status.builder()
                .id(1)
                .typeStatus("En cours")
                .build();

        Emprunt empruntEnCours = Emprunt.builder()
                .id(empruntId)
                .status(statusEnCours)
                .build();

        when(empruntRepository.findEmpruntById(empruntId))
                .thenReturn(List.of(empruntEnCours));
        when(statusRepository.findByTypeStatus("Terminé"))
                .thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> empruntService.retournerEmprunt(empruntId));
        assertEquals("Status de retour non trouvé", exception.getMessage());
    }
}
