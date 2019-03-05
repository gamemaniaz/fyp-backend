package foodbank.packing.service;

import java.util.List;

import foodbank.packing.dto.PackingListDTO;
import foodbank.packing.entity.PackingList;

public interface PackingService {
	
	List<PackingListDTO> retrieveAllPackingLists();
	
	PackingListDTO findById(final String id);
	
	PackingListDTO findByBeneficiary(final String beneficiary);
	
	PackingList findDbListByBeneficiary(final String beneficiary);
	
	PackingList findDbListById(final Long id);
	
	void generatePackingList();
	
	void updatePackedQuantities(final PackingListDTO packingList);
	
	Boolean reviewAllPackingStatus();
	
	void generateDbInvoices(final PackingList packingList);
	
	List<PackingListDTO> retrieveAllPackingListsInWindow();
			
}
