package fr.eni.baralivre.back.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@ToString
@Builder

@Entity
@Table(name = "categorie")
public class Categorie {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "nom",  nullable = false, length = 50)
    String nom;

    @ManyToMany(mappedBy = "categories")
    private Set<Livre> livres;
}
