package foodbank.packing.dto;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import foodbank.beneficiary.dto.BeneficiaryDTO;

public class PackingListDTO {

	@NotNull
	@JsonProperty("id")
	private Long id;

	@NotNull
	@JsonProperty("beneficiary")
	private BeneficiaryDTO beneficiary;
	
	@NotNull
	@JsonProperty("packedItems")
	private List<PackedItemDTO> packedItems;
	
	@NotNull
	@JsonProperty("packingStatus")
	private Boolean packingStatus;
	
	protected PackingListDTO() {}

	public PackingListDTO(Long id, BeneficiaryDTO beneficiary, List<PackedItemDTO> packedItems, Boolean packingStatus) {
		this.id = id;
		this.beneficiary = beneficiary;
		this.packedItems = packedItems;
		this.packingStatus = packingStatus;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BeneficiaryDTO getBeneficiary() {
		return beneficiary;
	}

	public void setBeneficiary(BeneficiaryDTO beneficiary) {
		this.beneficiary = beneficiary;
	}

	public List<PackedItemDTO> getPackedItems() {
		return packedItems;
	}

	public void setPackedItems(List<PackedItemDTO> packedItems) {
		this.packedItems = packedItems;
	}

	public Boolean getPackingStatus() {
		return packingStatus;
	}

	public void setPackingStatus(Boolean packingStatus) {
		this.packingStatus = packingStatus;
	}
	
}
