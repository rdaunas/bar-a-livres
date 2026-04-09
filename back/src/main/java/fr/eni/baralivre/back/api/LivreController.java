package fr.eni.baralivre.back.api;

import fr.eni.baralivre.back.dto.LivreDTO;
import fr.eni.baralivre.back.entity.Livre;
import fr.eni.baralivre.back.service.LivreService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor

@RestController
@RequestMapping("/api")
public class LivreController {
    private LivreService livreService;

    @GetMapping("/books")
    public ResponseEntity<?> findAllBooks() {
        List<Livre> livres = livreService.findAllLivre();
        if (livres == null || livres.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(livres, HttpStatus.OK);
    }

    @GetMapping("/books/{isbn}")
    public ResponseEntity<?> findLivreByIsbn(@PathVariable("isbn") String isbn) {
        Optional<Livre> livreByIsbn = livreService.findLivreByIsbn(isbn);
        if (livreByIsbn == null || livreByIsbn.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(livreByIsbn, HttpStatus.OK);
    }

    @GetMapping("/books/{titre}")
    public ResponseEntity<?> findLivreByTitre(@PathVariable("titre") String titre) {
        Optional<Livre> livreByTitre = livreService.findLivreByTitre(titre);
        if (livreByTitre == null || livreByTitre.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(livreByTitre, HttpStatus.OK);
    }

    @GetMapping("/books/{auteur}")
    public ResponseEntity<?> findLivreByAuteur(@PathVariable("auteur") String auteur) {
        Optional<Livre> livreByAuteur = livreService.findLivreByAuteur(auteur);
        if (livreByAuteur == null || livreByAuteur.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(livreByAuteur, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> ajouterLivre(@Valid @RequestBody LivreDTO livreDTO ) {
        try {
            livreService.ajouterLivre(livreDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

}
