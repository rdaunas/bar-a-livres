package fr.eni.baralivre.back.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = "livres")
@Builder

@Entity
@Table(name = "categorie")
public class Categorie {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    Integer id;

    @Column(name = "nom",  nullable = false, length = 50)
        String nom;

    @JsonIgnore
    @ManyToMany(mappedBy = "categories")
    private Set<Livre> livres;
}
