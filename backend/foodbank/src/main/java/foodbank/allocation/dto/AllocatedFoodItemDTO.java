package foodbank.allocation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AllocatedFoodItemDTO {

	@JsonProperty("category")
	private String category;
	
	@JsonProperty("classification")
	private String classification;
	
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("inventoryQuantity")
	private Integer inventoryQuantity;
	
	@JsonProperty("requestedQuantity")
	private Integer requestedQuantity;
	
	@JsonProperty("allocatedQuantity")
	private Integer allocatedQuantity;

	protected AllocatedFoodItemDTO() {}
	
	public AllocatedFoodItemDTO(String category, String classification, String description, Integer inventoryQuantity,
			Integer requestedQuantity, Integer allocatedQuantity) {
		this.category = category;
		this.classification = classification;
		this.description = description;
		this.inventoryQuantity = inventoryQuantity;
		this.requestedQuantity = requestedQuantity;
		this.allocatedQuantity = allocatedQuantity;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getInventoryQuantity() {
		return inventoryQuantity;
	}

	public void setInventoryQuantity(Integer inventoryQuantity) {
		this.inventoryQuantity = inventoryQuantity;
	}

	public Integer getRequestedQuantity() {
		return requestedQuantity;
	}

	public void setRequestedQuantity(Integer requestedQuantity) {
		this.requestedQuantity = requestedQuantity;
	}

	public Integer getAllocatedQuantity() {
		return allocatedQuantity;
	}

	public void setAllocatedQuantity(Integer allocatedQuantity) {
		this.allocatedQuantity = allocatedQuantity;
	}
	
}
