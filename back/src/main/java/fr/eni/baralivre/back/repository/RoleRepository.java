package fr.eni.baralivre.back.repository;

import fr.eni.baralivre.back.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByLabel(String label);
}
