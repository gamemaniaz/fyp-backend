package foodbank.history.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import foodbank.beneficiary.entity.Beneficiary;

@Entity
@Table(name = "request_history")
public class RequestHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "req_hist_log_seq_gen")
	@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "req_hist_log_seq_gen", sequenceName = "request_history_log_sequence")
	private Long id;
	
	@ManyToOne(//cascade = CascadeType.ALL,
			fetch = FetchType.LAZY,
			optional = true, targetEntity = Beneficiary.class)
	@JoinColumn(name = "beneficiary_user_id")
	private Beneficiary beneficiary;
	
	@OneToMany(mappedBy = "requestHistory",
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY,
			orphanRemoval = true)
	private List<PastRequest> pastRequests;
	
	public RequestHistory(Beneficiary beneficiary, List<PastRequest> pastRequests) {
		this.beneficiary = beneficiary;
		this.pastRequests = pastRequests;
	}
	
	protected RequestHistory() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Beneficiary getBeneficiary() {
		return beneficiary;
	}

	public void setBeneficiary(Beneficiary beneficiary) {
		this.beneficiary = beneficiary;
	}

	public List<PastRequest> getPastRequests() {
		return pastRequests;
	}

	public void setPastRequests(List<PastRequest> pastRequests) {
		this.pastRequests = pastRequests;
	}
	
	/*
	public Date getRequestCreationDate() {
		return requestCreationDate;
	}

	public void setRequestCreationDate(Date requestCreationDate) {
		this.requestCreationDate = requestCreationDate;
	}
	*/
	
}
