package fr.eni.baralivre.back.repository;


import fr.eni.baralivre.back.entity.Emprunt;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EmpruntRepository extends JpaRepository<Emprunt, Long> {

    @NullMarked
    @Override
    List<Emprunt> findAll();

    List<Emprunt> findEmpruntById(int id);

    List<Emprunt> findEmpruntByUserId(Integer userId);

    List<Emprunt> findEmpruntByUserIdAndStatus_TypeStatusAndDateRetourPrevisionnelBefore(Integer userId, String statusTypeStatus, LocalDateTime dateRetourPrevisionnel);

    List<Emprunt> findEmpruntByUserIdAndStatus_TypeStatus(Integer userId, String statusTypeStatus);


}
