package foodbank.allocation.dto;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AllocationUpdateDTO {
	
	@JsonProperty("id")
	private String id;
	
	@JsonProperty("beneficiary")
	private String beneficiary;
	
	@NotNull
	@JsonProperty("allocatedItems")
	private List<Map<String, Object>> allocatedItems;
	
	protected AllocationUpdateDTO() {}

	public AllocationUpdateDTO(@JsonProperty("id") String id, @JsonProperty("beneficiary") String beneficiary, @JsonProperty("allocatedItems") List<Map<String, Object>> allocatedItems) {
		this.id = id;
		this.beneficiary = beneficiary;
		this.allocatedItems = allocatedItems;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBeneficiary() {
		return beneficiary;
	}

	public void setBeneficiary(String beneficiary) {
		this.beneficiary = beneficiary;
	}

	public List<Map<String, Object>> getAllocatedItems() {
		return allocatedItems;
	}

	public void setAllocatedItems(List<Map<String, Object>> allocatedItems) {
		this.allocatedItems = allocatedItems;
	}

}
