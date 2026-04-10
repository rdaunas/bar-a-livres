package fr.eni.baralivre.back.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Builder
@Table(name = "emprunt")
public class Emprunt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id",  nullable = false)
    private Integer userId;

    @Column(name = "livre_isbn",   nullable = false, length = 13)
    private Long livreIsbn;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    private Status status;

    @Column(name = "date_demande",   nullable = false)
    private LocalDateTime dateDemande;

    @Column(name = "date_emprunt",   nullable = true)
    private LocalDateTime dateEmprunt;

    @Column(name = "date_retour_previsionnel",   nullable = true)
    private LocalDateTime dateRetourPrevisionnel;

}
