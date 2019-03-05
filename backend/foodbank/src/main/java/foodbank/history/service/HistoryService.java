package foodbank.history.service;

import java.util.List;

import foodbank.history.dto.PastRequestsByBeneficiaryDTO;
import foodbank.history.dto.RequestHistoryDTO;

public interface HistoryService {
	
	List<RequestHistoryDTO> retrieveAllPastRequests();
	
	List<PastRequestsByBeneficiaryDTO> retrieveAllPastRequestsByBeneficiary(final String beneficiary);

}
