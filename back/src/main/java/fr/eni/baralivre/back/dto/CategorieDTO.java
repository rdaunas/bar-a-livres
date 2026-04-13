package fr.eni.baralivre.back.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CategorieDTO {
    public Integer id;
    public String nom;
}
