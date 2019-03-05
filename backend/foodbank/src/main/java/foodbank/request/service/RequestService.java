package foodbank.request.service;

import java.util.List;

import foodbank.request.dto.RequestDTO;
import foodbank.request.entity.Request;

public interface RequestService {

	List<Request> getAllRequests();
	
	List<Request> getAllRequestsByBeneficiary(final String beneficiary);
	
	void createAndUpdateRequest(final RequestDTO request);
			
	void deleteRequest(final String id);
		
}
