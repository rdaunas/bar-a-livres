package fr.eni.baralivre.back.service;

import fr.eni.baralivre.back.dto.LivreDTO;
import fr.eni.baralivre.back.entity.Categorie;
import fr.eni.baralivre.back.entity.Livre;

import java.util.List;
import java.util.Optional;

public interface LivreService {
    List<LivreDTO> findAllLivre();

    List<LivreDTO> findLivreByTitre(String titre);

    Optional<LivreDTO> findLivreByIsbn(String isbn);

    Optional<LivreDTO> findLivreByAuteur(String auteur);

    List<LivreDTO> findLivreByCategorie(List<Integer> categorieIds);

    List<LivreDTO> search(List<Integer> categorieIds, String titre);

    // List<Livre> findLivreByCategorieAndTitre(List<Integer> categorieIds, String titre);

    Livre ajouterLivre(Livre livre);

    Livre retirerLivre(String isbn);

    Livre update(Livre livre);
}
