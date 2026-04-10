package fr.eni.baralivre.back.service;

import fr.eni.baralivre.back.entity.Emprunt;
import fr.eni.baralivre.back.repository.EmpruntRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class EmpruntServiceImpl implements EmpruntService {

    private EmpruntRepository empruntRepository;

    @Override
    public Emprunt chargerUnEmprunt(int id) {
        if (id <= 0) {
            throw new RuntimeException("id invalide");
        }
        final List<Emprunt> emprunt = empruntRepository.findEmpruntById(id);
        if (!emprunt.isEmpty()) {
            return emprunt.get(0);
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
        final LocalDateTime todayDate = LocalDateTime.now();
        final List<Emprunt> empruntsRetard = empruntRepository.findEmpruntByUserIdAndStatus_TypeStatusAndDateRetourPrevisionnelBefore(userID, "En cours",todayDate);
        return empruntsRetard;
    }
}
