package foodbank.history.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import foodbank.history.entity.RequestHistory;

public interface HistoryRepository extends JpaRepository<RequestHistory, Long> {

	RequestHistory findByBeneficiaryUserUsername(final String username);
	
}
