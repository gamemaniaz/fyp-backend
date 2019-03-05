package foodbank.security.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import foodbank.security.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
