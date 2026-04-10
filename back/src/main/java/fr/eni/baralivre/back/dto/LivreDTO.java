package fr.eni.baralivre.back.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LivreDTO {
    public String isbn;
    public String couverture;
    public String titre;
    public String auteur;
    public String description;
    public int nbExemplaire;
    public boolean isActive;
    public List<CategorieDTO> categories;
}
