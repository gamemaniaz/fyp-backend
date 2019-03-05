package foodbank.reporting.entity;

import java.util.ArrayList;
import java.util.List;

import foodbank.beneficiary.entity.Beneficiary;
import foodbank.inventory.entity.FoodItem;
import foodbank.packing.entity.PackedFoodItem;
import foodbank.packing.entity.PackingList;

public class InvoiceData {
	
	private String invoiceNumber;
	private String invoiceLabel;
	private String packedDate;
	
	private String deliveryDate;
	private String deliveryTime;
	private String issuedBy;
	private String comments;
	
	private boolean deliveryRequired;
	private boolean reportGenerated;

	private String organizationToBill;
	private String organizationToBillAddress;
	private String organizationToBillContactPerson;
	private String organizationToBillContactNumber;
	
	private String receivingOrganization;
	private String receivingOrganizationAddress;
	private String receivingOrganizationContactPerson;
	private String receivingOrganizationContactNumber;
	
	private List<InvoiceLineItem> invoiceLineItem;
	
	private double combinedTotalValue = 0;
	
	protected InvoiceData() {}
	
	public InvoiceData(Invoice invoice, Beneficiary billingOrganization, 
			Beneficiary receivingOrganization, PackingList packingList) {
		this.invoiceNumber = String.valueOf(invoice.getId());
		this.invoiceLabel = invoice.getInvoiceLabel();
		this.packedDate = invoice.getGenerationDate() == null ? "" : invoice.getGenerationDate().toString();
		this.deliveryDate = invoice.getDeliveryDate() == null ? "" : invoice.getDeliveryDate().toString();
		this.deliveryTime = invoice.getDeliveryTime() == null ? "" : invoice.getDeliveryTime().toString();
		this.issuedBy = invoice.getIssuedBy();
		this.comments = invoice.getComments();
		this.deliveryRequired = invoice.getDeliveryStatus();
		this.reportGenerated = invoice.getGenerationStatus();
		this.organizationToBill = billingOrganization.getUser().getName();
		this.organizationToBillAddress = billingOrganization.getAddress();
		this.organizationToBillContactPerson = billingOrganization.getContactPerson();
		this.organizationToBillContactNumber = billingOrganization.getContactNumber();
		this.receivingOrganization = receivingOrganization.getUser().getName();
		this.receivingOrganizationAddress = receivingOrganization.getAddress();
		this.receivingOrganizationContactPerson = receivingOrganization.getContactPerson();
		this.receivingOrganizationContactNumber = receivingOrganization.getContactNumber();
		this.invoiceLineItem =  generateInvoiceLineItemData(packingList.getPackedItems());
		calculateInvoiceTotalValue(invoiceLineItem);
	}

	public String getInvoiceLabel() {
		return invoiceLabel;
	}

	public void setInvoiceLabel(String invoiceLabel) {
		this.invoiceLabel = invoiceLabel;
	}

	public boolean isDeliveryRequired() {
		return deliveryRequired;
	}

	public void setDeliveryRequired(boolean deliveryRequired) {
		this.deliveryRequired = deliveryRequired;
	}

	public boolean isReportGenerated() {
		return reportGenerated;
	}

	public void setReportGenerated(boolean reportGenerated) {
		this.reportGenerated = reportGenerated;
	}

	public String getInvoiceId() {
		return invoiceNumber;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceNumber = invoiceId;
	}
	
	public String getPackedDate() {
		return packedDate;
	}

	public void setPackedDate(String packedDate) {
		this.packedDate = packedDate;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getIssuedBy() {
		return issuedBy;
	}

	public void setIssuedBy(String issuedBy) {
		this.issuedBy = issuedBy;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getOrganizationToBill() {
		return organizationToBill;
	}

	public void setOrganizationToBill(String organizationToBill) {
		this.organizationToBill = organizationToBill;
	}

	public String getOrganizationToBillAddress() {
		return organizationToBillAddress;
	}

	public void setOrganizationToBillAddress(String organizationToBillAddress) {
		this.organizationToBillAddress = organizationToBillAddress;
	}

	public String getOrganizationToBillContactPerson() {
		return organizationToBillContactPerson;
	}

	public void setOrganizationToBillContactPerson(String organizationToBillContactPerson) {
		this.organizationToBillContactPerson = organizationToBillContactPerson;
	}

	public String getOrganizationToBillContactNumber() {
		return organizationToBillContactNumber;
	}

	public void setOrganizationToBillContactNumber(String organizationToBillContactNumber) {
		this.organizationToBillContactNumber = organizationToBillContactNumber;
	}

	public String getReceivingOrganization() {
		return receivingOrganization;
	}

	public void setReceivingOrganization(String receivingOrganization) {
		this.receivingOrganization = receivingOrganization;
	}

	public String getReceivingOrganizationAddress() {
		return receivingOrganizationAddress;
	}

	public void setReceivingOrganizationAddress(String receivingOrganizationAddress) {
		this.receivingOrganizationAddress = receivingOrganizationAddress;
	}

	public String getReceivingOrganizationContactPerson() {
		return receivingOrganizationContactPerson;
	}

	public void setReceivingOrganizationContactPerson(String receivingOrganizationContactPerson) {
		this.receivingOrganizationContactPerson = receivingOrganizationContactPerson;
	}

	public String getReceivingOrganizationContactNumber() {
		return receivingOrganizationContactNumber;
	}

	public void setReceivingOrganizationContactNumber(String receivingOrganizationContactNumber) {
		this.receivingOrganizationContactNumber = receivingOrganizationContactNumber;
	}
	
	private List<InvoiceLineItem> generateInvoiceLineItemData(List<PackedFoodItem> packedItems) {
		List<InvoiceLineItem> lineItems = new ArrayList<InvoiceLineItem>();
		packedItems.forEach(item -> {
			FoodItem dbFoodItem = item.getPackedFoodItem();
			Long id = dbFoodItem.getId();
			String category = dbFoodItem.getCategory();
			String classification = dbFoodItem.getClassification();
			String description = dbFoodItem.getDescription();
			Integer packedQuantity = item.getPackedQuantity();
			Double value = dbFoodItem.getValue();
			lineItems.add(new InvoiceLineItem(String.valueOf(id), category, classification, description, packedQuantity, value));
		});
		return lineItems;
	}

	public List<InvoiceLineItem> getInvoiceLineItem() {
		return invoiceLineItem;
	}

	public void setInvoiceLineItem(List<InvoiceLineItem> invoiceLineItem) {
		this.invoiceLineItem = invoiceLineItem;
	}
	
	private void calculateInvoiceTotalValue(List<InvoiceLineItem> invoiceLineItems) {
		double combinedTotalValue = 0;
		for(InvoiceLineItem item : invoiceLineItems) {
			combinedTotalValue += item.getTotalValue();
		}
		this.setCombinedTotalValue(combinedTotalValue);
	}

	public double getCombinedTotalValue() {
		return combinedTotalValue;
	}

	public void setCombinedTotalValue(double combinedTotalValue) {
		this.combinedTotalValue = combinedTotalValue;
	}
	
}