package foodbank.allocation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import foodbank.allocation.entity.Allocation;

public interface AllocationRepository extends JpaRepository<Allocation, Long>{
	
	Allocation findByBeneficiaryUserUsername(String beneficiary);
	
	Allocation findById(String id);

}
