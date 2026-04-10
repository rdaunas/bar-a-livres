package fr.eni.baralivre.back.api;

import fr.eni.baralivre.back.dto.EmpruntResquestDTO;
import fr.eni.baralivre.back.entity.Emprunt;
import fr.eni.baralivre.back.entity.Status;
import fr.eni.baralivre.back.service.EmpruntService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmpruntControllerTest {

    @Mock
    private EmpruntService empruntService;

    @InjectMocks
    private EmpruntController empruntController;

    private Emprunt emprunt;

    @BeforeEach
    void setUp() {
        Status status = Status.builder()
                .id(1)
                .typeStatus("En cours")
                .build();

        emprunt = Emprunt.builder()
                .id(1)
                .userId(1)
                .livreIsbn("1234567890L")
                .status(status)
                .dateDemande(LocalDateTime.now())
                .dateEmprunt(LocalDateTime.now())
                .dateRetourPrevisionnel(LocalDateTime.now().plusDays(14))
                .build();
    }

    // Tests pour GET /api/loans/{id}
    @Test
    void getEmpruntById_devraitRetourner200_quandEmpruntExiste() {
        // Given
        int empruntId = 1;
        when(empruntService.chargerUnEmprunt(empruntId)).thenReturn(emprunt);

        // When
        ResponseEntity<Emprunt> response = empruntController.getEmpruntById(empruntId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(emprunt.getId(), response.getBody().getId());
        verify(empruntService).chargerUnEmprunt(empruntId);
    }

    @Test
    void getEmpruntById_devraitRetourner404_quandEmpruntNonTrouve() {
        // Given
        int empruntId = 999;
        when(empruntService.chargerUnEmprunt(empruntId))
                .thenThrow(new RuntimeException("emprunt not found"));

        // When
        ResponseEntity<Emprunt> response = empruntController.getEmpruntById(empruntId);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(empruntService).chargerUnEmprunt(empruntId);
    }

    // Tests pour GET /api/loans
    @Test
    void getAllEmprunts_devraitRetourner200AvecListeVide() {
        // Given
        when(empruntService.chargerToutLesEmprunt()).thenReturn(Collections.emptyList());

        // When
        ResponseEntity<List<Emprunt>> response = empruntController.getAllEmprunts();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
        verify(empruntService).chargerToutLesEmprunt();
    }

    @Test
    void getAllEmprunts_devraitRetourner200AvecListe() {
        // Given
        List<Emprunt> emprunts = List.of(emprunt);
        when(empruntService.chargerToutLesEmprunt()).thenReturn(emprunts);

        // When
        ResponseEntity<List<Emprunt>> response = empruntController.getAllEmprunts();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(emprunt.getId(), response.getBody().getFirst().getId());
        verify(empruntService).chargerToutLesEmprunt();
    }

    // Tests pour GET /api/loans/my
    @Test
    void getMesEmprunts_devraitRetourner200AvecListeEmprunts() {
        // Given
        List<Emprunt> emprunts = List.of(emprunt);
        when(empruntService.chargerToutLesEmpruntParUserId(anyInt())).thenReturn(emprunts);

        // When
        ResponseEntity<List<Emprunt>> response = empruntController.getMesEmprunts();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(empruntService).chargerToutLesEmpruntParUserId(anyInt());
    }

    @Test
    void getMesEmprunts_devraitRetourner400_enCasErreur() {
        // Given
        when(empruntService.chargerToutLesEmpruntParUserId(anyInt()))
                .thenThrow(new RuntimeException("Utilisateur inconnu"));

        // When
        ResponseEntity<List<Emprunt>> response = empruntController.getMesEmprunts();

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(empruntService).chargerToutLesEmpruntParUserId(anyInt());
    }

    // Tests pour POST /api/loans
    @Test
    void emprunterLivre_devraitRetourner201_quandEmpruntCree() {
        // Given
        EmpruntResquestDTO request = new EmpruntResquestDTO("1234567890L");
        when(empruntService.creerEmprunt(anyInt(), eq("1234567890L"))).thenReturn(emprunt);

        // When
        ResponseEntity<Emprunt> response = empruntController.emprunterLivre(request);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(emprunt.getId(), response.getBody().getId());
        verify(empruntService).creerEmprunt(anyInt(), eq("1234567890L"));
    }

    @Test
    void emprunterLivre_devraitRetourner400_quandMax3Emprunts() {
        // Given
        EmpruntResquestDTO request = new EmpruntResquestDTO("1234567890L");
        when(empruntService.creerEmprunt(anyInt(), eq("1234567890L")))
                .thenThrow(new RuntimeException("Vous avez atteint le nombre maximum d'emprunts"));

        // When
        ResponseEntity<Emprunt> response = empruntController.emprunterLivre(request);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(empruntService).creerEmprunt(anyInt(), eq("1234567890L"));
    }

    @Test
    void emprunterLivre_devraitRetourner400_quandEmpruntsEnRetard() {
        // Given
        EmpruntResquestDTO request = new EmpruntResquestDTO("1234567890L");
        when(empruntService.creerEmprunt(anyInt(), eq("1234567890L")))
                .thenThrow(new RuntimeException("Vous avez des emprunts en retard"));

        // When
        ResponseEntity<Emprunt> response = empruntController.emprunterLivre(request);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(empruntService).creerEmprunt(anyInt(), eq("1234567890L"));
    }

    @Test
    void emprunterLivre_devraitRetourner400_quandLivreIsbnNull() {
        // Given
        EmpruntResquestDTO request = new EmpruntResquestDTO(null);
        when(empruntService.creerEmprunt(anyInt(), isNull()))
                .thenThrow(new RuntimeException("ISBN du livre invalide"));

        // When
        ResponseEntity<Emprunt> response = empruntController.emprunterLivre(request);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(empruntService).creerEmprunt(anyInt(), isNull());
    }

    // Tests pour PUT /api/loans/{id}/return
    @Test
    void retournerLivre_devraitRetourner200_quandRetourReussi() {
        // Given
        int empruntId = 1;
        Status statusTermine = Status.builder()
                .id(2)
                .typeStatus("Terminé") // ← Changé de "Retourné" à "Terminé"
                .build();
        Emprunt empruntTermine = Emprunt.builder()
                .id(empruntId)
                .userId(1)
                .livreIsbn("1234567890L")
                .status(statusTermine)
                .build();

        when(empruntService.retournerEmprunt(empruntId)).thenReturn(empruntTermine);

        // When
        ResponseEntity<Emprunt> response = empruntController.retournerLivre(empruntId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Terminé", response.getBody().getStatus().getTypeStatus()); // ← Changé
        verify(empruntService).retournerEmprunt(empruntId);
    }

    @Test
    void retournerLivre_devraitRetourner400_quandEmpruntNonEnCours() {
        // Given
        int empruntId = 1;
        when(empruntService.retournerEmprunt(empruntId))
                .thenThrow(new RuntimeException("Emprunt non en cours"));

        // When
        ResponseEntity<Emprunt> response = empruntController.retournerLivre(empruntId);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(empruntService).retournerEmprunt(empruntId);
    }

    @Test
    void retournerLivre_devraitRetourner400_quandIdInvalide() {
        // Given
        int empruntId = 0;
        when(empruntService.retournerEmprunt(empruntId))
                .thenThrow(new RuntimeException("id d'emprunt invalide"));

        // When
        ResponseEntity<Emprunt> response = empruntController.retournerLivre(empruntId);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(empruntService).retournerEmprunt(empruntId);
    }
}