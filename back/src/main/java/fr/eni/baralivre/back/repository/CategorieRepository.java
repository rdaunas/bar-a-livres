package fr.eni.baralivre.back.repository;

import fr.eni.baralivre.back.entity.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieRepository extends JpaRepository<Categorie, Integer> {
}
