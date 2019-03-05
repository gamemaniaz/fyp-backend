package foodbank.packing.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PackedItemDTO {
	
	@NotNull
	@JsonProperty("category")
	private String category;

	@NotNull
	@JsonProperty("classification")
	private String classification;
	
	@NotNull
	@JsonProperty("description")
	private String description;
	
	@NotNull
	@JsonProperty("inventoryQuantity")
	private Integer inventoryQuantity;
	
	@NotNull
	@JsonProperty("allocatedQuantity")
	private Integer allocatedQuantity;
	
	@NotNull
	@JsonProperty("packedQuantity")
	private Integer packedQuantity;
	
	@NotNull
	@JsonProperty("itemPackingStatus")
	private Boolean itemPackingStatus;
	
	protected PackedItemDTO() {}
	
	public PackedItemDTO(String category, String classification, String description, Integer inventoryQuantity, Integer allocatedQuantity, Integer packedQuantity) {
		this(category, classification, description, inventoryQuantity, allocatedQuantity, packedQuantity, Boolean.FALSE);
	}

	public PackedItemDTO(String category, String classification, String description, Integer inventoryQuantity, 
			Integer allocatedQuantity, Integer packedQuantity, Boolean itemPackingStatus) {
		this.category = category;
		this.classification = classification;
		this.description = description;
		this.inventoryQuantity = inventoryQuantity;
		this.allocatedQuantity = allocatedQuantity;
		this.packedQuantity = packedQuantity;
		this.itemPackingStatus = itemPackingStatus;
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

	public Integer getPackedQuantity() {
		return packedQuantity;
	}

	public void setPackedQuantity(Integer packedQuantity) {
		this.packedQuantity = packedQuantity;
	}

	public Boolean getItemPackingStatus() {
		return itemPackingStatus;
	}

	public void setItemPackingStatus(Boolean itemPackingStatus) {
		this.itemPackingStatus = itemPackingStatus;
	}
	
}
