package foodbank.history.dto;

import java.util.Date;
import java.util.List;

public class PastRequestsByBeneficiaryDTO {
	
	private Date requestCreationDate;
	
	private List<PastRequestDTO> pastRequests;

	protected PastRequestsByBeneficiaryDTO() {}
	
	public PastRequestsByBeneficiaryDTO(Date requestCreationDate, List<PastRequestDTO> pastRequests) {
		this.requestCreationDate = requestCreationDate;
		this.pastRequests = pastRequests;
	}

	public Date getRequestCreationDate() {
		return requestCreationDate;
	}

	public void setRequestCreationDate(Date requestCreationDate) {
		this.requestCreationDate = requestCreationDate;
	}

	public List<PastRequestDTO> getPastRequests() {
		return pastRequests;
	}

	public void setPastRequests(List<PastRequestDTO> pastRequests) {
		this.pastRequests = pastRequests;
	}

}
