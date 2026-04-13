package fr.eni.baralivre.back;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "livre")
public class Livre {
    @Id
    @Column(name = "isbn", nullable = false, length = 20)
    private String isbn;

    @Column(name = "couverture")
    private String couverture;

    @Column(name = "titre", nullable = false)
    private String titre;

    @Column(name = "auteur")
    private String auteur;

    @Lob
    @Column(name = "description")
    private String description;

    @ColumnDefault("0")
    @Column(name = "nb_exemplaires")
    private Integer nbExemplaires;

    @Column(name = "date_ajout")
    private LocalDate dateAjout;

    @ColumnDefault("1")
    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

}