package fr.eni.baralivre.back.service;

import fr.eni.baralivre.back.dto.LivreDTO;
import fr.eni.baralivre.back.entity.Categorie;
import fr.eni.baralivre.back.entity.Livre;

import java.util.List;
import java.util.Optional;

public interface LivreService {
    List<Livre> findAllLivre();

    Optional<Livre> findLivreByTitre(String titre);

    Optional<Livre> findLivreByIsbn(String isbn);

    Optional<Livre> findLivreByAuteur(String auteur);

    Optional<Livre> findAllLivreByCategorie(Categorie categorie);

    Livre ajouterLivre(LivreDTO livreDTO);

    Livre retirerLivre(String isbn);

    Livre update(LivreDTO livreDTO);
}
