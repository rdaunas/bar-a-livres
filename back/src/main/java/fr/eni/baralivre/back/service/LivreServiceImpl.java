package fr.eni.baralivre.back.service;

import fr.eni.baralivre.back.dto.CategorieDTO;
import fr.eni.baralivre.back.dto.LivreDTO;

import fr.eni.baralivre.back.entity.Livre;
import fr.eni.baralivre.back.repository.LivreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LivreServiceImpl implements LivreService {
    @Autowired
    public LivreRepository livreRepository;

    private LivreDTO toDTO(Livre livre) {

        LivreDTO dto = new LivreDTO();

        dto.setIsbn(livre.getIsbn());
        dto.setTitre(livre.getTitre());
        dto.setAuteur(livre.getAuteur());
        dto.setCouverture(livre.getCouverture());
        dto.setDescription(livre.getDescription());
        dto.setNbExemplaire(livre.getNbExemplaire());
        dto.setActive(livre.isActive());

        if (livre.getCategories() != null) {
            dto.setCategories(
                    livre.getCategories()
                            .stream()
                            .map(this::toCategorieDTO)
                            .toList()
            );
        }

        return dto;
    }

    private CategorieDTO toCategorieDTO(fr.eni.baralivre.back.entity.Categorie c) {

        CategorieDTO dto = new CategorieDTO();
        dto.setId(c.getId());
        dto.setNom(c.getNom());

        return dto;
    }

    @Override
    public List<LivreDTO> findAllLivre() {
        return livreRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }
    public Page<LivreDTO> findAllLivre(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return livreRepository.findAll(pageable)
                .map(this::toDTO);
    }

    @Override
    public List<LivreDTO> findLivreByTitre(String titre) {
        return livreRepository.findByTitre(titre)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public Optional<LivreDTO> findLivreByIsbn(String isbn) {
        return livreRepository.findByIsbn(isbn)
                .map(this::toDTO);
    }

    @Override
    public Optional<LivreDTO> findLivreByAuteur(String auteur) {
        return livreRepository.findByAuteur(auteur)
                .map(this::toDTO);
    }

    @Override
    public List<LivreDTO> findLivreByCategorie(List<Integer> categorieIds) {
        return livreRepository.findDistinctByCategoriesIdIn(categorieIds)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public List<LivreDTO> search(List<Integer> categorieIds, String titre) {

        if (categorieIds != null && categorieIds.isEmpty()) {
            categorieIds = null;
        }
        if (titre != null && titre.isBlank()) {
            titre = null;
        }

        return livreRepository.search(categorieIds, titre)
                .stream()
                .map(this::toDTO)
                .toList();
    }

//    @Override
//    public List<Livre> findLivreByCategorieAndTitre(List<Integer> categorieIds, String titre) {
//        return livreRepository.findDistinctByCategoriesIdInAndTitreContainingIgnoreCase(categorieIds, titre);
//    }

    @Override
    public Livre ajouterLivre(Livre livre) {

        if (livre==null) {
            throw new RuntimeException("Le livre est obligatoire");
        }

        try {
        Livre livreCree = Livre.builder()
                .couverture(livre.getCouverture())
                .titre(livre.getTitre())
                .auteur(livre.getAuteur())
                .description(livre.getDescription())
                .nbExemplaire(livre.getNbExemplaire())
                .dateAjout(LocalDateTime.now())
                .build();

        return livreRepository.save(livreCree);

        } catch (Exception e) {
            throw new RuntimeException("Impossible de sauver - " + livre.getTitre().toString());
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
    public Livre update(Livre livre) {

        if (livre==null) {
            throw new RuntimeException("Le livre est obligatoire");
        }

        try {
            Livre livreUpdate = Livre.builder()
                    .titre(livre.getTitre())
                    .auteur(livre.getAuteur())
                    .couverture(livre.getCouverture())
                    .nbExemplaire(livre.getNbExemplaire())
                    .build();

            return livreRepository.save(livreUpdate);

        } catch (Exception e) {
            throw new RuntimeException("Impossible de sauver - " + livre.getTitre().toString());
        }
    }
}
