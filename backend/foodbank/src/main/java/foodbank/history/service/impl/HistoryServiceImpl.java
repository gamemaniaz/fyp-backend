package foodbank.history.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import foodbank.history.dto.PastRequestDTO;
import foodbank.history.dto.PastRequestsByBeneficiaryDTO;
import foodbank.history.dto.RequestHistoryDTO;
import foodbank.history.entity.PastRequest;
import foodbank.history.entity.RequestHistory;
import foodbank.history.repository.HistoryRepository;
import foodbank.history.service.HistoryService;
import foodbank.inventory.entity.FoodItem;
import foodbank.util.EntityManager;
import foodbank.util.EntityManager.DTOKey;
import foodbank.util.MessageConstants.ErrorMessages;
import foodbank.util.exceptions.InvalidBeneficiaryException;

@Service
public class HistoryServiceImpl implements HistoryService {

	@Autowired
	private HistoryRepository historyRepository;
	
	@Override
	public List<RequestHistoryDTO> retrieveAllPastRequests() {
		// TODO Auto-generated method stub
		List<RequestHistory> requestHistoryList = historyRepository.findAll();
		List<RequestHistoryDTO> results = new ArrayList<RequestHistoryDTO>();
		for(RequestHistory requestHistory : requestHistoryList) {
			results.add((RequestHistoryDTO)EntityManager.convertToDTO(DTOKey.RequestHistoryDTO, requestHistory));
		}
		return results;
	}

	@Override
	public List<PastRequestsByBeneficiaryDTO> retrieveAllPastRequestsByBeneficiary(String beneficiary) {
		// TODO Auto-generated method stub
		RequestHistory requestHistory = historyRepository.findByBeneficiaryUserUsername(beneficiary);
		List<PastRequestsByBeneficiaryDTO> results = new ArrayList<PastRequestsByBeneficiaryDTO>();
		if(requestHistory == null) {
			throw new InvalidBeneficiaryException(ErrorMessages.NO_SUCH_BENEFICIARY);
		}
		Map<Date, List<PastRequest>> pastRequestsByDate = new HashMap<Date, List<PastRequest>>();
		List<PastRequest> pastRequests = requestHistory.getPastRequests();
		for(PastRequest pastRequest : pastRequests) {
			Date requestDate = pastRequest.getRequestCreationDate();
			List<PastRequest> pastRequestsOnDate = pastRequestsByDate.get(requestDate);
			if(pastRequestsOnDate == null) {
				pastRequestsOnDate = new ArrayList<PastRequest>();
				pastRequestsByDate.put(requestDate, pastRequestsOnDate);
			}
			pastRequestsOnDate.add(pastRequest);
			pastRequestsByDate.replace(requestDate, pastRequestsOnDate);
		}
		for(Entry<Date, List<PastRequest>> entry : pastRequestsByDate.entrySet()) {
			Date key = entry.getKey();
			List<PastRequest> value = entry.getValue();
			List<PastRequestDTO> pastRequestDTOList = new ArrayList<PastRequestDTO>();
			for(PastRequest pastRequest : value) {
				FoodItem foodItem = pastRequest.getPreviouslyRequestedItem();
				String category = foodItem.getCategory();
				String classification = foodItem.getClassification();
				String description = foodItem.getDescription();
				Integer requestedQuantity = pastRequest.getRequestedQuantity();
				Integer allocatedQuantity = pastRequest.getAllocatedQuantity();
				pastRequestDTOList.add(new PastRequestDTO(category, classification, description, requestedQuantity, allocatedQuantity, pastRequest.getRequestCreationDate()));
			}
			PastRequestsByBeneficiaryDTO individualResultEntry = new PastRequestsByBeneficiaryDTO(key, pastRequestDTOList);
			results.add(individualResultEntry);
		}
		results.sort(Comparator.comparing(PastRequestsByBeneficiaryDTO::getRequestCreationDate));
		Collections.reverse(results);
		return results;
	}

}
