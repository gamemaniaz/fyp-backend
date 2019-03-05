package foodbank.reporting.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InvoiceDTO {
	
	@NotNull
	@JsonProperty("invoiceNumber")
	private String invoiceNumber;
	
	@NotNull
	@JsonProperty("beneficiaryName")
	private String beneficiaryName;

	protected InvoiceDTO() {}
	
	public InvoiceDTO(String invoiceNumber, String beneficiaryName) {
		this.invoiceNumber = invoiceNumber;
		this.beneficiaryName = beneficiaryName;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}
	
}
