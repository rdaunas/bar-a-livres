package fr.eni.baralivre.back.api;

import fr.eni.baralivre.back.dto.EmpruntResquestDTO;
import fr.eni.baralivre.back.entity.Emprunt;
import fr.eni.baralivre.back.service.EmpruntService;
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

    @GetMapping("/my")
    public ResponseEntity<List<Emprunt>> getMesEmprunts() {
        try {
            // TODO: Récupérer userId avec l'authentification
            Integer userId = 4;
            List<Emprunt> emprunts = empruntService.chargerToutLesEmpruntParUserId(userId);
            return ResponseEntity.ok(emprunts);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping
    public ResponseEntity<Emprunt> emprunterLivre(@RequestBody EmpruntResquestDTO request) {
        try {
            Integer userId = 4;
            Emprunt emprunt = empruntService.creerEmprunt(userId, request.getLivreIsbn());
            return ResponseEntity.ok(emprunt);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @PutMapping("/{id}/return")
    public ResponseEntity<Emprunt> retournerLivre(@PathVariable int id) {
        try {
            Emprunt emprunt = empruntService.retournerEmprunt(id);
            return ResponseEntity.ok(emprunt);
        }catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
