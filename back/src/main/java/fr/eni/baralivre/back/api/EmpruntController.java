package fr.eni.baralivre.back.api;

import fr.eni.baralivre.back.dto.EmpruntResquestDTO;
import fr.eni.baralivre.back.entity.Emprunt;
import fr.eni.baralivre.back.security.JwtUtil;
import fr.eni.baralivre.back.service.EmpruntService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
@AllArgsConstructor
@CrossOrigin
public class EmpruntController {

    private EmpruntService empruntService;
    private JwtUtil jwtUtil;

    @GetMapping("/{id}")
    public ResponseEntity<Emprunt> getEmpruntById(@PathVariable int id) {
        try {
            Emprunt emprunt = empruntService.chargerUnEmprunt(id);
            return ResponseEntity.ok(emprunt);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Emprunt>> getAllEmprunts() {
        List<Emprunt> emprunts = empruntService.chargerToutLesEmprunt();
        return ResponseEntity.ok(emprunts);
    }

    public Integer extractUserId(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Token manquant");
        }

        String token = authHeader.substring(7);
        return Integer.valueOf(jwtUtil.getUserIdFromToken(token));
    }

    @GetMapping("/my")
    public ResponseEntity<List<Emprunt>> getMesEmprunts(HttpServletRequest request) {
        try {
            Integer userId = extractUserId(request);

            List<Emprunt> emprunts = empruntService.chargerToutLesEmpruntParUserId(userId);
            return ResponseEntity.ok(emprunts);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping
    public ResponseEntity<Emprunt> emprunterLivre(
            @RequestBody EmpruntResquestDTO request,
            HttpServletRequest httpRequest
    ) {
        try {
            int userId = this.extractUserId(httpRequest);
            Emprunt emprunt = empruntService.creerEmprunt(userId, request.getLivreIsbn());
            return ResponseEntity.ok(emprunt);

        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }
    @PutMapping("/{id}/return")
    public ResponseEntity<Emprunt> retournerLivre(@PathVariable int id) {
        try {
            Emprunt emprunt = empruntService.retournerEmprunt(id);
            return ResponseEntity.ok(emprunt);
        }catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/books/{isbn}/available")
    public ResponseEntity<Boolean> isAvailable(@PathVariable String isbn) {
        return ResponseEntity.ok(empruntService.isLivreDisponible(isbn));
    }

}
