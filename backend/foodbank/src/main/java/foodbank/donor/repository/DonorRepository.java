package foodbank.donor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import foodbank.donor.entity.Donor;

public interface DonorRepository extends JpaRepository<Donor, Long> {

	Donor findByName(String name);
	
	Donor findById(Long id);
	
}
