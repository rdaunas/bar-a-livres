package fr.eni.baralivre.back.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LivreDTO {

    @NotBlank(message = "L'ISBN est obligatoire")
    @Size(max = 20, message = "L'ISBN ne peut pas dépasser 20 caractères")
    private String isbn;

    @Size(max = 255, message = "Le chemin de couverture ne peut pas dépasser 255 caractères")
    private String couverture;

    @NotBlank(message = "Le titre est obligatoire")
    @Size(max = 255, message = "Le titre ne peut pas dépasser 255 caractères")
    private String titre;

    @NotBlank(message = "L'auteur est obligatoire")
    @Size(max = 255, message = "Le nom de l'auteur ne peut pas dépasser 255 caractères")
    private String auteur;

    @Size(max = 2000, message = "La description ne peut pas dépasser 2000 caractères")
    private String description;

    @Min(value = 0, message = "Le nombre d'exemplaires ne peut pas être négatif")
    @Max(value = 10, message = "Le nombre d'exemplaires ne peut pas dépasser 10")
    private int nbExemplaire;

    private LocalDateTime dateAjout;

    private boolean isActive;

    @NotNull(message = "La liste des catégories ne peut pas être nulle")
    private List<CategorieDTO> categories;
}