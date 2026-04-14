package fr.eni.baralivre.back.service;

import fr.eni.baralivre.back.dto.CategorieDTO;
import fr.eni.baralivre.back.dto.LivreDTO;

import fr.eni.baralivre.back.entity.Categorie;
import fr.eni.baralivre.back.entity.Livre;
import fr.eni.baralivre.back.repository.CategorieRepository;
import fr.eni.baralivre.back.repository.LivreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class LivreServiceImpl implements LivreService {

    @Autowired
    public LivreRepository livreRepository;

    @Autowired
    public CategorieRepository categorieRepository;

    private LivreDTO toDTO(Livre livre) {
        LivreDTO dto = new LivreDTO();
        dto.setIsbn(livre.getIsbn());
        dto.setTitre(livre.getTitre());
        dto.setAuteur(livre.getAuteur());
        dto.setCouverture(livre.getCouverture());
        dto.setDescription(livre.getDescription());
        dto.setNbExemplaire(livre.getNbExemplaire());
        dto.setDateAjout(livre.getDateAjout());
        dto.setActive(livre.isActive());

        dto.setCategories(
                livre.getCategories() != null
                        ? livre.getCategories().stream().map(this::toCategorieDTO).toList()
                        : List.of()
        );
        return dto;
    }

    private CategorieDTO toCategorieDTO(Categorie c) {
        CategorieDTO dto = new CategorieDTO();
        dto.setId(c.getId());
        dto.setNom(c.getNom());
        return dto;
    }

    @Override
    public Page<LivreDTO> findAllLivre(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return livreRepository.findAll(pageable)
                .map(this::toDTO);
    }

    @Override
    public Optional<LivreDTO> findLivreByIsbn(String isbn) {
        return livreRepository.findByIsbn(isbn)
                .map(this::toDTO);
    }

    @Override
    public Page<LivreDTO> search(List<Integer> categorieIds, String titre, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        if (categorieIds != null && categorieIds.isEmpty()) {
            categorieIds = null;
        }
        if (titre != null && titre.isBlank()) {
            titre = null;
        }
        return livreRepository.search(categorieIds, titre, pageable)
                .map(this::toDTO);
    }

    @Override
    public LivreDTO ajouterLivre(LivreDTO livreDTO) {
        if (livreDTO == null) {
            throw new RuntimeException("Les informations du livre sont obligatoires");
        }

        if (livreRepository.existsById(livreDTO.getIsbn())) {
            throw new RuntimeException("Un livre avec l'ISBN " + livreDTO.getIsbn() + " existe déjà.");
        }

        Set<Categorie> categories = null;
        if (livreDTO.getCategories() != null) {
            List<Integer> ids = livreDTO.getCategories()
                    .stream()
                    .map(CategorieDTO::getId)
                    .toList();

            categories = new HashSet<>(categorieRepository.findAllById(ids));

            if (categories.size() != ids.size()) {
                throw new RuntimeException("Une ou plusieurs catégories sont introuvables.");
            }
        }

        Livre livre = Livre.builder()
                .isbn(livreDTO.getIsbn())
                .couverture(livreDTO.getCouverture())
                .titre(livreDTO.getTitre())
                .auteur(livreDTO.getAuteur())
                .description(livreDTO.getDescription())
                .nbExemplaire(livreDTO.getNbExemplaire())
                .dateAjout(LocalDateTime.now())
                .isActive(true)
                .categories(categories)
                .build();
        return toDTO(livreRepository.save(livre));
    }

    @Override
    public LivreDTO retirerLivre(String isbn) {
        if (isbn == null) {
            throw new RuntimeException("L'ISBN ne peut pas être null.");
        }
        Livre livre = livreRepository.findById(isbn)
                .orElseThrow(() -> new RuntimeException("Ce livre n'existe pas."));
        livre.setActive(false);
        return toDTO(livreRepository.save(livre));
    }

    @Override
    public LivreDTO update(LivreDTO livreDTO) {
        if (livreDTO == null) {
            throw new RuntimeException("Les informations du livre sont obligatoires");
        }

        Livre livre = livreRepository.findById(livreDTO.getIsbn())
                .orElseThrow(() -> new RuntimeException("Un livre avec l'ISBN " + livreDTO.getIsbn() + " n'existe pas."));

        livre.setCouverture(livreDTO.getCouverture());
        livre.setTitre(livreDTO.getTitre());
        livre.setAuteur(livreDTO.getAuteur());
        livre.setDescription(livreDTO.getDescription());
        livre.setNbExemplaire(livreDTO.getNbExemplaire());

        if (livreDTO.getCategories() != null) {
            List<Integer> ids = livreDTO.getCategories()
                    .stream()
                    .map(CategorieDTO::getId)
                    .toList();

            Set<Categorie> categories = new HashSet<>(categorieRepository.findAllById(ids));

            if (categories.size() != ids.size()) {
                throw new RuntimeException("Une ou plusieurs catégories sont introuvables.");
            }

            livre.setCategories(categories);
        }

        return toDTO(livreRepository.save(livre));
    }
}

