package foodbank.inventory.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FoodItemDTO {
	
	@NotNull
	@JsonProperty("donorName")
	private String donorName = null;
	
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
	private int quantity;
	
	@NotNull
	@JsonProperty("value")
	private double value;
	
	@NotNull
	@JsonProperty("barcode")
	private String barcode;
	
//	public FoodItemDTO(@JsonProperty("category") String category, @JsonProperty("classification") String classification, 
//			@JsonProperty("description") String description, @JsonProperty("quantity") int quantity) {
//		this.category = category;
//		this.classification = classification;
//		this.description = description;
//		this.quantity = quantity;
//	}
	
	public FoodItemDTO(@JsonProperty("category") String category, @JsonProperty("classification") String classification, 
			@JsonProperty("description") String description, @JsonProperty("quantity") int quantity, 
			@JsonProperty("value") double value, @JsonProperty("donorName") String donorName) {
		this.category = category;
		this.classification = classification;
		this.description = description;
		this.quantity = quantity;
		this.value = value;
		this.donorName = donorName;
	}

	public String getDonorName() {
		return donorName;
	}

	public void setDonorName(String donorName) {
		this.donorName = donorName;
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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public double getValue() {
		return value;
	}
	
	public void setValue(double value) {
		this.value = value;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	
}
