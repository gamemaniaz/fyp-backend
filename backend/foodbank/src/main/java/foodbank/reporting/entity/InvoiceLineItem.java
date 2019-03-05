package foodbank.reporting.entity;

public class InvoiceLineItem {
	
	private String itemNo;
	private String category;
	private String classification;
	private String description;
	private Integer quantity;
	private Double value;
	private Double totalValue;
	
	protected InvoiceLineItem() {}
	
	public InvoiceLineItem(String itemNo, String category, String classification, String description,
			Integer quantity, Double value) {
		this.itemNo = itemNo;
		this.category = category;
		this.classification = classification;
		this.description = description;
		this.quantity = quantity;
		this.value = value;
		this.totalValue = quantity * value;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
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
