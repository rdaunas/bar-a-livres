package fr.eni.baralivre.back.repository;

import fr.eni.baralivre.back.entity.Livre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LivreRepository extends JpaRepository<Livre, String> {

    Optional<Livre> findByIsbn(String isbn);

    // Rechercher une liste de livre par son auteur
    Optional<Livre> findByAuteur(String auteur);

    // Rechercher un livre par son titre
    List<Livre> findByTitre(String titre);

    // Rechercher un livre par son/ses catégorie
    List<Livre> findDistinctByCategoriesIdIn(List<Integer> categorieIds);

    @Query("""
            SELECT l
            FROM Livre l
            WHERE (:titre IS NULL OR LOWER(l.titre) LIKE LOWER(CONCAT('%', :titre, '%')))
            AND (
                :categorieIds IS NULL OR EXISTS (
                    SELECT c FROM l.categories c WHERE c.id IN :categorieIds
                )
            )
            """)
    Page<Livre> search(@Param("categorieIds") List<Integer> categorieIds, @Param("titre") String titre, Pageable pageable);

    // Rechercher un livre par son/ses catégorie et/ou son titre
    List<Livre> findDistinctByCategoriesIdInAndTitreContainingIgnoreCase(List<Integer> categorieIds, String titre);
}