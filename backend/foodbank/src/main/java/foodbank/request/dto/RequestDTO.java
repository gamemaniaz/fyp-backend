package foodbank.request.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestDTO {

	@NotNull
	@JsonProperty("beneficiary")
	private String beneficiary;
	
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
	@JsonProperty("quantity")
	private Integer quantity;
	
	public RequestDTO(@JsonProperty("beneficiary") String beneficiary, @JsonProperty("category") String category, 
			@JsonProperty("classification") String classification, @JsonProperty("description") String description, 
			@JsonProperty("quantity") Integer quantity) {
		this.beneficiary = beneficiary;
		this.category = category;
		this.classification = classification;
		this.description = description;
		this.quantity = quantity;
	}

	public String getBeneficiary() {
		return beneficiary;
	}

	public void setBeneficiary(String beneficiary) {
		this.beneficiary = beneficiary;
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

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
}
