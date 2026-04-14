package fr.eni.baralivre.back.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = "categories")
@Builder

@Entity
@Table(name = "livre")
public class Livre {

    @Id
    @Column(name = "isbn", nullable = false, length = 13)
    @EqualsAndHashCode.Include
    String isbn;

    @Column(name = "couverture", length = 255)
    String couverture;

    @Column(name = "titre", nullable = false, length = 255)
    String titre;

    @Column(name = "auteur", nullable = false, length = 255)
    String auteur;

    String description;

    @Column(name = "nb_exemplaires")
    int nbExemplaire;

    @Column(name = "date_ajout")
    LocalDateTime dateAjout;


    @Column(name = "is_active")
    boolean isActive;

    @ManyToMany
    @JoinTable(
            name = "livre_categorie",
            joinColumns = @JoinColumn(name = "livre_isbn"),
            inverseJoinColumns = @JoinColumn(name = "categorie_id")
    )

    private Set<Categorie> categories;
}
