package fr.eni.baralivre.back.api;

import fr.eni.baralivre.back.dto.LivreDTO;
import fr.eni.baralivre.back.entity.Livre;
import fr.eni.baralivre.back.service.LivreService;
import jakarta.validation.Valid;
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
    public ResponseEntity<?> findAllBooks() {
        List<LivreDTO> livres = livreService.findAllLivre();
        if (livres == null || livres.isEmpty()) {
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
        return new ResponseEntity<>(livreByIsbn, HttpStatus.OK);
    }

//    @GetMapping("/books/{by-titre}")
//    public ResponseEntity<?> findLivreByTitre(@PathVariable("titre") String titre) {
//        List<Livre> livreByTitre = livreService.findLivreByTitre(titre);
//        if (livreByTitre == null || livreByTitre.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(livreByTitre, HttpStatus.OK);
//    }

//    @GetMapping("/books/{by-auteur}")
//    public ResponseEntity<?> findLivreByAuteur(@PathVariable("auteur") String auteur) {
//        Optional<Livre> livreByAuteur = livreService.findLivreByAuteur(auteur);
//        if (livreByAuteur == null || livreByAuteur.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(livreByAuteur, HttpStatus.OK);
//    }

//    @GetMapping("/books/search")
//    public ResponseEntity<?> findLivreByCategoriesAndTitre(
//            @Parameter(description = "Liste des IDs de catégories")
//            @RequestParam(required = false) List<Integer> categorieIds,
//
//            @Parameter(description = "Titre du livre")
//            @RequestParam(required = false) String titre ) {
//
//        List<Livre> livres;
//
//        boolean hasCategories = categorieIds != null && !categorieIds.isEmpty();
//        boolean hasTitre = titre != null && !titre.isBlank();
//
//        if (hasCategories && hasTitre) {
//            livres = livreService.findLivreByCategorieAndTitre(categorieIds, titre);
//
//        } else if (hasCategories) {
//            livres = livreService.findLivreByCategorie(categorieIds);
//
//        } else if (hasTitre) {
//            livres = livreService.findLivreByTitre(titre);
//
//        } else {
//            return ResponseEntity.badRequest()
//                    .body("Vous devez fournir au moins un filtre (categorieIds ou titre)");
//        }
//
//        if (livres == null || livres.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//        return new ResponseEntity<>(livres, HttpStatus.OK);
//    }

    @GetMapping("/books/search")
    public ResponseEntity<?> findLivreByCategoriesAndTitre(
            @RequestParam(required = false) List<Integer> categorieIds,
            @RequestParam(required = false) String titre) {

        List<LivreDTO> livres = livreService.search(categorieIds, titre);

        if (livres.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(livres);
    }

    @PostMapping
    public ResponseEntity<?> ajouterLivre(@Valid @RequestBody Livre livre ) {
        try {
            livreService.ajouterLivre(livre);
        return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

}
