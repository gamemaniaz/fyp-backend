package foodbank.beneficiary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import foodbank.beneficiary.entity.Beneficiary;

public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Long> {
		
	Beneficiary findByUserUsername(String username);
		
}
