package foodbank.packing.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PackingUpdateDTO {
	
	@NotNull
	@JsonProperty("id")
	private String id;
	
	@NotNull
	@JsonProperty("itemIndex")
	private Integer itemIndex;
	
	@NotNull
	@JsonProperty("packedQuantity")
	private Integer packedQuantity;
	
	@NotNull
	@JsonProperty("itemPackingStatus")
	private Boolean itemPackingStatus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getItemIndex() {
		return itemIndex;
	}

	public void setItemIndex(Integer itemIndex) {
		this.itemIndex = itemIndex;
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
