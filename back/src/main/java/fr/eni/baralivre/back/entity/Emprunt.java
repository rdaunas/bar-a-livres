package fr.eni.baralivre.back.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
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
    private String livreIsbn;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "statut_id")
    @JsonUnwrapped
    private Status status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(name = "date_demande",   nullable = false)
    private LocalDateTime dateDemande;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(name = "date_emprunt")
    private LocalDateTime dateEmprunt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(name = "date_retour_previsionnel")
    private LocalDateTime dateRetourPrevisionnel;

}
