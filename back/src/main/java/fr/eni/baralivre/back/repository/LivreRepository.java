package fr.eni.baralivre.back.repository;

import fr.eni.baralivre.back.entity.Categorie;
import fr.eni.baralivre.back.entity.Livre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LivreRepository extends JpaRepository<Livre, String> {

    Optional<Livre> findByIsbn(String isbn);

    // Rechercher une liste de livre par son auteur
    Optional<Livre> findByAuteur(String auteur);

    // Rechercher un livre par son titre
    Optional<Livre> findByTitre(String titre);

    Optional<Livre> findByCategorie(Categorie categorie);
}