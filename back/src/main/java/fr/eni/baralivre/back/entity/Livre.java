package fr.eni.baralivre.back.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder

@Entity
@Table(name = "livre")
public class Livre {

    @Id
    @Column(name = "isbn", nullable = false, length = 13)
    String isbn;

    @Column(name = "couverture", length = 255)
    String couverture;

    @Column(name = "titre", nullable = false, length = 255)
    String titre;

    @Column(name = "auteur", nullable = false, length = 255)
    String auteur;

    @Column(name = "description", nullable = false)
    String description;

    @Column(name = "nb_exemplaires")
    Integer nbExemplaire;

    @Column(name = "date_ajout")
    LocalDateTime dateAjout;

    @Column(name = "is_active")
    boolean isActive;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

}
