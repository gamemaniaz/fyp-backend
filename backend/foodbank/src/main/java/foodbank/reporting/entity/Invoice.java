package foodbank.reporting.entity;

import java.text.DecimalFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.scheduling.annotation.Scheduled;

import foodbank.util.DateParser;

@Entity
public class Invoice {

	// These values are hard-coded because they should not be changed unless the address have been changed
	public static final String COMPANY_NAME = "The Food Bank Singapore Ltd";
	public static final String ADDRESS_LINE_1 = "39 KEPPEL ROAD #01-02/04";
	public static final String ADDRESS_LINE_2 = "TANJONG PAGAR DISTRIPARK";
	public static final String ADDRESS_LINE_3 = "SINGAPORE 089065";
	public static final String COMPANY_WEBSITE = "www.foodbank.sg";
	public static final String CO_REG_NO = "Co Reg No: 201200654E";
	
	private static final String INVOICE_IDENTIFIER = "FBDO";
	
	private final String dateMonth = DateParser.displayMonthYearOnly(new Date());
	
	@Temporal(TemporalType.DATE)
	private Date generationDate;
	
	private static Integer invoiceNumber = Integer.valueOf(1);
	private static final DecimalFormat decimalFormat = new DecimalFormat("0000");
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invoice_seq_gen")
	@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "invoice_seq_gen", sequenceName = "invoice_sequence")
	private Long id;
	
	private String invoiceLabel;
	
	@Temporal(TemporalType.DATE)
	private Date deliveryDate;
	
	@Temporal(TemporalType.TIME)
	private Date deliveryTime;
	
	private String issuedBy = "";
	private String comments = "";
	
	private Boolean deliveryStatus = Boolean.FALSE;
	private Boolean generationStatus = Boolean.FALSE;
	
	@Column(name = "billing_org_id")
	private Long billingOrganizationId;
	
	@Column(name = "receiving_org_id")
	private Long receivingOrganizationId;
	
	@Column(name = "packing_list_id")
	private Long packingListId;
	
	/*
	@ManyToOne(cascade = CascadeType.ALL,
			fetch = FetchType.LAZY,
			optional = true, targetEntity = Beneficiary.class)
	@JoinColumn(name = "beneficiary_user_id")
	private Beneficiary billingOrganization;
	
	@OneToOne(cascade = CascadeType.ALL, 
			fetch = FetchType.LAZY) //mappedBy = "invoice")
	@JoinColumn(name = "packing_list_id")
	private PackingList packingList;
	
	@ManyToOne(cascade = CascadeType.ALL,
			fetch = FetchType.LAZY,
			optional = true, targetEntity = Beneficiary.class)
	@JoinColumn(name = "beneficiary_user_id")
	private Beneficiary receivingOrganization;
	
	private List<PackedFoodItem> packedItems;
	*/
	
	protected Invoice() {}
	
	public Invoice(Long billingOrganizationId, Long receivingOrganizationId, Long packingListId) {
		this.invoiceLabel = INVOICE_IDENTIFIER + "-" + dateMonth + "-"
				+ decimalFormat.format(invoiceNumber);
		this.billingOrganizationId = billingOrganizationId;
		this.receivingOrganizationId = receivingOrganizationId;
		this.packingListId = packingListId;
		invoiceNumber++;
	}
	
	@Scheduled(cron = "0 0 0 L * ? *")
	private void resetInvoiceNumber() {
		invoiceNumber = 1;
	}
	
	/*
	public Invoice(Beneficiary billingOrganization, PackingList packingList) {
		this.invoiceLabel = INVOICE_IDENTIFIER + "-" + dateMonth + "-"
				+ decimalFormat.format(invoiceNumber);
		this.billingOrganization = billingOrganization;
		this.packingList = packingList;
	}
	*/

	public Date getGenerationDate() {
		return generationDate;
	}

	public void setGenerationDate(Date generationDate) {
		this.generationDate = generationDate;
	}

	public static Integer getInvoiceNumber() {
		return invoiceNumber;
	}

	public static void setInvoiceNumber(Integer invoiceNumber) {
		Invoice.invoiceNumber = invoiceNumber;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInvoiceLabel() {
		return invoiceLabel;
	}

	public void setInvoiceLabel(String invoiceLabel) {
		this.invoiceLabel = invoiceLabel;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
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

	public Boolean getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(Boolean deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public Boolean getGenerationStatus() {
		return generationStatus;
	}

	public void setGenerationStatus(Boolean generationStatus) {
		this.generationStatus = generationStatus;
	}

	public Long getBillingOrganizationId() {
		return billingOrganizationId;
	}

	public void setBillingOrganizationId(Long billingOrganizationId) {
		this.billingOrganizationId = billingOrganizationId;
	}

	public Long getReceivingOrganizationId() {
		return receivingOrganizationId;
	}

	public void setReceivingOrganizationId(Long receivingOrganizationId) {
		this.receivingOrganizationId = receivingOrganizationId;
	}

	public Long getPackingListId() {
		return packingListId;
	}

	public void setPackingListId(Long packingListId) {
		this.packingListId = packingListId;
	}

	public String getDateMonth() {
		return dateMonth;
	}
	
}