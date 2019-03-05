package foodbank.packing.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import foodbank.packing.entity.PackingList;

public interface PackingRepository extends JpaRepository<PackingList, Long> {

	PackingList findByBeneficiaryUserUsername(String beneficiary);
	
	PackingList findById(Long id);
	
	List<PackingList> findListById(Long id);
	
	List<PackingList> findByPackingStatusFalse();
	
	List<PackingList> findByCreationDateBetween(Date startDate, Date endDate);
	
}
