package fr.eni.baralivre.back.repository;

import fr.eni.baralivre.back.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatusRepository extends JpaRepository<Status, Long> {
    Optional<Status> findByTypeStatus(String typeStatus);
}
