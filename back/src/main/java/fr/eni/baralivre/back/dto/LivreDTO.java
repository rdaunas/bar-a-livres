package fr.eni.baralivre.back.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LivreDTO {
    private String isbn;
    private String couverture;
    private String titre;
    private String auteur;
    private String categorie;
    private Integer nb_exemplaire;
    private String description;
}
