package fr.eni.baralivre.back.service;

import fr.eni.baralivre.back.entity.Emprunt;
import fr.eni.baralivre.back.repository.EmpruntRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
                .livreIsbn(1234567890)
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


}
