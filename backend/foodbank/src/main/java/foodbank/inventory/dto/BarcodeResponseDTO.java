package foodbank.inventory.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BarcodeResponseDTO {
	
	@NotNull
	@JsonProperty("category")
	private String category;
	
	@NotNull
	@JsonProperty("classification")
	private String classification;
	
	@NotNull
	@JsonProperty("description")
	private String description;

	public BarcodeResponseDTO(String category, String classification, String description) {
		this.category = category;
		this.classification = classification;
		this.description = description;
	}

}
