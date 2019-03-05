package foodbank.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import foodbank.login.entity.ResetToken;

public interface ResetTokenRepository extends JpaRepository<ResetToken, Long> {
	
	ResetToken findByToken(String token);

}
