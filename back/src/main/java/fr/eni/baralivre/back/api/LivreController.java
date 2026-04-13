package fr.eni.baralivre.back.api;

import fr.eni.baralivre.back.dto.LivreDTO;
import fr.eni.baralivre.back.service.LivreService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor

@RestController
@RequestMapping("/api")
public class LivreController {

    private LivreService livreService;

    @GetMapping("/books")
    public ResponseEntity<?> findAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Page<LivreDTO> livres = livreService.findAllLivre(page, size);
        if (livres.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(livres, HttpStatus.OK);
    }

    @GetMapping("/books/{isbn}")
    public ResponseEntity<?> findLivreByIsbn(@PathVariable("isbn") String isbn) {
        Optional<LivreDTO> livreByIsbn = livreService.findLivreByIsbn(isbn);
        if (livreByIsbn.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(livreByIsbn.get(), HttpStatus.OK);
    }

    @GetMapping("/books/search")
    public ResponseEntity<?> findLivreByCategoriesAndTitre(
            @RequestParam(required = false) List<Integer> categorieIds,
            @RequestParam(required = false) String titre,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Page<LivreDTO> livres = livreService.search(categorieIds, titre, page, size);
        if (livres.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(livres);
    }

    @PostMapping("/books")
    public ResponseEntity<?> ajouterLivre(@Valid @RequestBody LivreDTO livreDTO) {
        LivreDTO livreCree = livreService.ajouterLivre(livreDTO);
        return new ResponseEntity<>(livreCree, HttpStatus.CREATED);
    }

    @PutMapping ("/books/{isbn}")
    public ResponseEntity<?> updateLivre(@Valid @RequestBody LivreDTO livreDTO, @PathVariable("isbn") String isbn) {
        if (!isbn.equals(livreDTO.getIsbn())) {
            return new ResponseEntity<>("L'ISBN de l'URL et du body ne correspondent pas", HttpStatus.BAD_REQUEST);
        }

        LivreDTO livreMisAJour = livreService.update(livreDTO);
        return new ResponseEntity<>(livreMisAJour, HttpStatus.OK);
    }

    @DeleteMapping("/books/{isbn}")
    public ResponseEntity<?> deleteLivreByIsbn(@PathVariable("isbn") String isbn) {
        if (isbn == null || isbn.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        LivreDTO livreByIsbn = livreService.retirerLivre(isbn);
        return new ResponseEntity<>(livreByIsbn, HttpStatus.OK);
    }
}
