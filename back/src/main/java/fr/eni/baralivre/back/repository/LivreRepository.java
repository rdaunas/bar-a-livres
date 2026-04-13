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

}