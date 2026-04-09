package fr.eni.baralivre.back.service;

import fr.eni.baralivre.back.dto.LivreDTO;
import fr.eni.baralivre.back.entity.Categorie;
import fr.eni.baralivre.back.entity.Livre;
import fr.eni.baralivre.back.repository.LivreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LivreServiceImpl implements LivreService {
    public LivreRepository livreRepository;

    @Override
    public List<Livre> findAllLivre() {
        return livreRepository.findAll();
    }

    @Override
    public Optional<Livre> findLivreByTitre(String titre) {
        return livreRepository.findByTitre(titre);
    }

    @Override
    public Optional<Livre> findLivreByIsbn(String isbn) {
        return livreRepository.findByIsbn(isbn);
    }

    @Override
    public Optional<Livre> findLivreByAuteur(String auteur) {
        return livreRepository.findByAuteur(auteur);
    }

    @Override
    public Optional<Livre> findAllLivreByCategorie(Categorie categorie) {
        return livreRepository.findByCategorie(categorie);
    }

    @Override
    public Livre ajouterLivre(LivreDTO livreDTO) {

        if (livreDTO==null) {
            throw new RuntimeException("Le livre est obligatoire");
        }

        try {
        Livre livre = Livre.builder()
                .isbn(livreDTO.getIsbn())
                .titre(livreDTO.getTitre())
                .auteur(livreDTO.getAuteur())
                .couverture(livreDTO.getCouverture())
                .nbExemplaire(livreDTO.getNb_exemplaire())
                .build();

        return livreRepository.save(livre);

        } catch (Exception e) {
            throw new RuntimeException("Impossible de sauver - " + livreDTO.toString());
        }
    }

    @Override
    public Livre retirerLivre(String isbn) {
        if (isbn == null) {
            throw new RuntimeException("L'ISBN ne peut pas être null.");
        }

        Livre livre = livreRepository.findById(isbn)
                .orElseThrow(() -> new RuntimeException("Ce livre n'existe pas."));

        livre.setActive(false);

        return livreRepository.save(livre);
    }

    @Override
    public Livre update(LivreDTO livreDTO) {

        if (livreDTO==null) {
            throw new RuntimeException("Le livre est obligatoire");
        }

        try {
            Livre livre = Livre.builder()
                    .isbn(livreDTO.getIsbn())
                    .titre(livreDTO.getTitre())
                    .auteur(livreDTO.getAuteur())
                    .couverture(livreDTO.getCouverture())
                    .nbExemplaire(livreDTO.getNb_exemplaire())
                    .build();

            return livreRepository.save(livre);

        } catch (Exception e) {
            throw new RuntimeException("Impossible de sauver - " + livreDTO.toString());
        }
    }
}
