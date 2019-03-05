package foodbank.allocation.service;

import java.util.List;

import foodbank.allocation.dto.AllocatedFoodItemDTO;
import foodbank.allocation.dto.AllocationResponseDTO;
import foodbank.allocation.dto.AllocationUpdateDTO;

public interface AllocationService {

	List<AllocationResponseDTO> retrieveAllAllocations();
	
	List<AllocatedFoodItemDTO> retrieveAllocationByBeneficiary(final String beneficiary);
	
	void generateAllocationList();
	
	void updateAllocation(final AllocationUpdateDTO allocation);
	
	void approveAllocations();
	
	Boolean checkApproveStatus();
	
}
