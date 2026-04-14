package fr.eni.baralivre.back.service;

import fr.eni.baralivre.back.dto.LivreDTO;
import fr.eni.baralivre.back.entity.Categorie;
import fr.eni.baralivre.back.entity.Livre;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface LivreService {
    Page<LivreDTO> findAllLivre(int page, int size);

    Optional<LivreDTO> findLivreByIsbn(String isbn);

    Page<LivreDTO> search(List<Integer> categorieIds, String titre, int page, int size);

    LivreDTO ajouterLivre(LivreDTO livreDTO);

    LivreDTO retirerLivre(String isbn);

    LivreDTO update(LivreDTO livreDTO);
}
