package foodbank.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import foodbank.admin.entity.AdminSettings;

public interface AdminRepository extends JpaRepository<AdminSettings, Long>{

	AdminSettings findById(Long id);
	
}
