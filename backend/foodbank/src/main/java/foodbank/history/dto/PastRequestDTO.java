package foodbank.history.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PastRequestDTO {
	
	@JsonProperty("category")
	private String category;
	
	@JsonProperty("classification")
	private String classification;
	
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("requestedQuantity")
	private Integer requestedQuantity;
	
	@JsonProperty("allocatedQuantity")
	private Integer allocatedQuantity;
	
	@JsonProperty("requestCreationDate")
	private String requestCreationDate;

	public PastRequestDTO(String category, String classification, String description, Integer requestedQuantity,
			Integer allocatedQuantity, Date requestCreationDate) {
		this.category = category;
		this.classification = classification;
		this.description = description;
		this.requestedQuantity = requestedQuantity;
		this.allocatedQuantity = allocatedQuantity;
		this.requestCreationDate = requestCreationDate.toString();
	}

}
