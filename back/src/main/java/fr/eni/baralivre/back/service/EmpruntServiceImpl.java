package fr.eni.baralivre.back.service;

import fr.eni.baralivre.back.dto.LivreDTO;
import fr.eni.baralivre.back.entity.Emprunt;
import fr.eni.baralivre.back.entity.Status;
import fr.eni.baralivre.back.repository.EmpruntRepository;
import fr.eni.baralivre.back.repository.StatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class EmpruntServiceImpl implements EmpruntService {

    private EmpruntRepository empruntRepository;
    private StatusRepository statusRepository;
    private LivreService livreService;


    @Override
    public Emprunt chargerUnEmprunt(int id) {
        if (id <= 0) {
            throw new RuntimeException("id invalide");
        }
        final List<Emprunt> emprunt = empruntRepository.findEmpruntById(id);
        if (!emprunt.isEmpty()) {
            return emprunt.getFirst();
        }
        throw new RuntimeException("emprunt not found");
    }

    @Override
    public List<Emprunt> chargerToutLesEmprunt() {
        return empruntRepository.findAll();
    }

    @Override
    public List<Emprunt> chargerToutLesEmpruntParUserId(Integer userId) {
        if(userId == null){
            throw new RuntimeException("Utilisateur inconnu");
        }
        return empruntRepository.findEmpruntByUserId(userId);
    }

    @Override
    public List<Emprunt> chargerLesStatusDesEmpruntParUserId(Integer userID) {
        if (userID == null){
            throw new RuntimeException("Utilisateur inconnu");
        }

        return empruntRepository.findEmpruntByUserIdAndStatus_TypeStatus(userID, "En cours");
    }

    @Override
    public List<Emprunt> chargerLesStatusDesEmpruntParIsbn(String livreIsbn) {
        if (livreIsbn == null){
            throw new RuntimeException("ISBN du livre invalide");
        }
        return empruntRepository.findEmpruntByLivreIsbnAndStatus_TypeStatus(livreIsbn, "En cours");
    }

    @Override
    public Emprunt creerEmprunt(Integer userId, String livreIsbn) {
        if (userId == null){
            throw new RuntimeException("Utilisateur inconnu");
        }
        if (livreIsbn == null){
            throw new RuntimeException("ISBN du livre invalide");
        }

        List<Emprunt> empruntsEnCours = empruntRepository.findEmpruntByUserIdAndStatus_TypeStatus(userId, "En cours");
        if(empruntsEnCours.size() >=3){
            throw new RuntimeException("Vous avez atteint le nombre maximum d'emprunts");
        }
        List<Emprunt> emprunts = chargerLesStatusDesEmpruntParUserId(userId);
        for (Emprunt emprunt : emprunts) {
            if (LocalDateTime.now().isAfter(emprunt.getDateRetourPrevisionnel())) {
                throw new RuntimeException("Vous avez des emprunts en retard");
            }
        }
        LivreDTO exemplaires = livreService.findLivreByIsbn(livreIsbn).orElseThrow();
        int nbExmplaires = exemplaires.getNbExemplaire();
        int nbExemplairesDispo = nbExmplaires - empruntRepository.findEmpruntByLivreIsbnAndStatus_TypeStatus(livreIsbn, "En cours").size();
        if (nbExemplairesDispo <= 0){
            throw new RuntimeException("Le livre n'est plus disponible");
        }

        final LocalDateTime todayDate = LocalDateTime.now();
        Status statusEmprunt = statusRepository.findByTypeStatus("En cours").orElseThrow(() -> new RuntimeException("Statut 'En cours' non trouvé"));
        Emprunt emprunt = Emprunt.builder()
                .userId(userId)
                .livreIsbn(livreIsbn)
                .dateDemande(todayDate)
                .dateEmprunt(todayDate)
                .dateRetourPrevisionnel(todayDate.plusDays(14))
                .status(statusEmprunt)
                .build();
        return empruntRepository.save(emprunt);
    }

    @Override
    public Emprunt retournerEmprunt(int empruntId) {
        if (empruntId <= 0){
            throw new RuntimeException("id d'emprunt invalide");
        }
        Emprunt emprunt = chargerUnEmprunt(empruntId);

        if(!emprunt.getStatus().getTypeStatus().equals("En cours")){
            throw new RuntimeException("Emprunt non en cours");
        }
    Status statusRetourne = statusRepository.findByTypeStatus("Terminé").orElseThrow(() -> new RuntimeException("Status de retour non trouvé"));
    emprunt.setStatus(statusRetourne);

        return empruntRepository.save(emprunt);
    }

}
