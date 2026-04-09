package fr.eni.baralivre.back.repository;

import fr.eni.baralivre.back.entity.Categorie;
import fr.eni.baralivre.back.entity.Livre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LivreRepository extends JpaRepository<Livre, String> {

    Optional<Livre> findByIsbn(String isbn);

    // Rechercher une liste de livre par son auteur
    // List<Livre> findByAuteur(@Param("auteur") String auteur);

    // Rechercher un livre par son titre
    Optional<Livre> findByTitre(String titre);

    List<Livre> findByCategorie(Categorie categorie);
}