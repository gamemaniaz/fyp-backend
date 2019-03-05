package foodbank.history.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import foodbank.beneficiary.dto.BeneficiaryDTO;

public class RequestHistoryDTO {
	
	@NotNull
	@JsonProperty("beneficiary")
	private BeneficiaryDTO beneficiary;
	
	@NotNull
	@JsonProperty("pastRequests")
	private List<PastRequestDTO> pastRequests;

	protected RequestHistoryDTO() {}
	
	public RequestHistoryDTO(BeneficiaryDTO beneficiary, List<PastRequestDTO> pastRequests) {
		this.beneficiary = beneficiary;
		this.pastRequests = pastRequests;
	}

	public BeneficiaryDTO getBeneficiary() {
		return beneficiary;
	}

	public void setBeneficiary(BeneficiaryDTO beneficiary) {
		this.beneficiary = beneficiary;
	}

	public List<PastRequestDTO> getPastRequests() {
		return pastRequests;
	}

	public void setPastRequests(List<PastRequestDTO> pastRequests) {
		this.pastRequests = pastRequests;
	}
	
}
