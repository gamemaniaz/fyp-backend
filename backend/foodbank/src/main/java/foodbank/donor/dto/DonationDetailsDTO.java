package foodbank.donor.dto;

public class DonationDetailsDTO {
	
	private String category;
	private String classification;
	private String description;
	private Integer donatedQuantity;
	private Double value;
	private Double totalValue;
	
	public DonationDetailsDTO(String category, String classification, String description, Integer donatedQuantity,
			Double value, Double totalValue) {
		this.category = category;
		this.classification = classification;
		this.description = description;
		this.donatedQuantity = donatedQuantity;
		this.value = value;
		this.totalValue = totalValue;
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
	
	public Integer getDonatedQuantity() {
		return donatedQuantity;
	}
	
	public void setDonatedQuantity(Integer donatedQuantity) {
		this.donatedQuantity = donatedQuantity;
	}
	
	public Double getValue() {
		return value;
	}
	
	public void setValue(Double value) {
		this.value = value;
	}
	
	public Double getTotalValue() {
		return totalValue;
	}
	
	public void setTotalValue(Double totalValue) {
		this.totalValue = totalValue;
	}
	
}
