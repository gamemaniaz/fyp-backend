package foodbank.allocation.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import foodbank.beneficiary.entity.Beneficiary;
import foodbank.history.entity.PastRequest;

@Entity
@Table(name = "allocation")
public class Allocation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "alloc_seq_gen")
	@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "alloc_seq_gen", sequenceName = "allocation_sequence")
	private Long id;
	
	@OneToOne(//cascade = CascadeType.ALL,
			fetch = FetchType.LAZY,
			orphanRemoval = true,
			optional = true, targetEntity = Beneficiary.class)
	@JoinColumn(name = "beneficiary_user_id")
	private Beneficiary beneficiary;
	
	@OneToMany(mappedBy = "allocation", 
			cascade = CascadeType.ALL, 
			fetch = FetchType.LAZY, orphanRemoval = true)
			//orphanRemoval = true)
	private List<AllocatedFoodItem> allocatedItems;
	
	private Boolean approvalStatus;
	
	protected Allocation() {}

	public Allocation(Beneficiary beneficiary, List<AllocatedFoodItem> allocatedItems, Boolean approvalStatus) {
		this.beneficiary = beneficiary;
		this.allocatedItems = allocatedItems;
		this.approvalStatus = approvalStatus;
	}

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
	
	public List<AllocatedFoodItem> getAllocatedItems() {
		return allocatedItems;
	}

	public void setAllocatedItems(List<AllocatedFoodItem> allocatedItems) {
		this.allocatedItems = allocatedItems;
	}

	public Boolean getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(Boolean approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	
}
