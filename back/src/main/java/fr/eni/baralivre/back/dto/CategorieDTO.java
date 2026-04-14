package fr.eni.baralivre.back.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CategorieDTO {

    public Integer id;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 50, message = "Le nom de la catégorie ne peut pas dépasser 50 caractères")
    public String nom;
}
