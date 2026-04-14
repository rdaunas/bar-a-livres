package fr.eni.baralivre.back.service;

import fr.eni.baralivre.back.entity.Emprunt;

import java.util.List;

public interface EmpruntService {

    Emprunt chargerUnEmprunt(int id);

    List<Emprunt> chargerToutLesEmprunt();

    List<Emprunt> chargerToutLesEmpruntParUserId(Integer userId);

    List<Emprunt> chargerLesStatusDesEmpruntParUserId(Integer userID);

    List<Emprunt> chargerLesStatusDesEmpruntParIsbn(String livreIsbn);

    Emprunt creerEmprunt(Integer userId, String livreIsbn);

    Emprunt retournerEmprunt(int empruntId);

    boolean isLivreDisponible(String isbn);

}
