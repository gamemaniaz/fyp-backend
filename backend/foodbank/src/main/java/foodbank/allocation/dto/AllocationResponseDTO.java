package foodbank.allocation.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import foodbank.beneficiary.dto.BeneficiaryDTO;
import foodbank.beneficiary.entity.Beneficiary;

public class AllocationResponseDTO {
	
	@JsonProperty("beneficiary")
	private BeneficiaryDTO beneficiary;
	
	@JsonProperty("allocatedItems")
	private List<AllocatedFoodItemDTO> allocatedItems;
	
	@JsonProperty("approvalStatus")
	private Boolean approvalStatus;

	protected AllocationResponseDTO() {}
	
	public AllocationResponseDTO(BeneficiaryDTO beneficiary, List<AllocatedFoodItemDTO> allocatedItems,
			Boolean approvalStatus) {
		this.beneficiary = beneficiary;
		this.allocatedItems = allocatedItems;
		this.approvalStatus = approvalStatus;
	}

	public BeneficiaryDTO getBeneficiary() {
		return beneficiary;
	}

	public void setBeneficiary(BeneficiaryDTO beneficiary) {
		this.beneficiary = beneficiary;
	}

	public List<AllocatedFoodItemDTO> getAllocatedItems() {
		return allocatedItems;
	}

	public void setAllocatedItems(List<AllocatedFoodItemDTO> allocatedItems) {
		this.allocatedItems = allocatedItems;
	}

	public Boolean getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(Boolean approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

}
