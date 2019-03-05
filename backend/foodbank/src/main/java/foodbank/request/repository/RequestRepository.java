package foodbank.request.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import foodbank.request.entity.Request;

public interface RequestRepository extends JpaRepository<Request, Long>{
	
	List<Request> findByBeneficiaryUserUsername(String beneficiary);
	
	Request findById(Long id);
	
}
