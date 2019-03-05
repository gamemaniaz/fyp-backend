package foodbank.packing.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import foodbank.beneficiary.entity.Beneficiary;

@Entity
@Table(name = "packing_list")
@EntityListeners(AuditingEntityListener.class)
public class PackingList {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "packing_list_seq_gen")
	@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "packing_list_seq_gen", sequenceName = "packing_list_sequence")
	private Long id;
	
	@ManyToOne(cascade = CascadeType.ALL,
			fetch = FetchType.LAZY,
			optional = true, targetEntity = Beneficiary.class)
	@JoinColumn(name = "beneficiary_user_id")
	private Beneficiary beneficiary;
	
	@OneToMany(mappedBy = "packingList", 
			cascade = CascadeType.ALL, 
			fetch = FetchType.LAZY)
			//orphanRemoval = true)
	private List<PackedFoodItem> packedItems;
	
	/*
	@OneToOne(mappedBy = "packingList",
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY)
	//@JoinColumn(name = "invoice_id")
	private Invoice invoice;
	*/
	
	private Boolean packingStatus;
	
	@CreatedDate
	@Temporal(TemporalType.DATE)
	private Date creationDate;
	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	protected PackingList() {}

	public PackingList(Beneficiary beneficiary, List<PackedFoodItem> packedItems) {
		this(beneficiary, packedItems, Boolean.FALSE);
	}

	public PackingList(Beneficiary beneficiary, List<PackedFoodItem> packedItems, Boolean packingStatus) {
		this.beneficiary = beneficiary;
		this.packedItems = packedItems;
		this.packingStatus = packingStatus;
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

	public List<PackedFoodItem> getPackedItems() {
		return packedItems;
	}

	public void setPackedItems(List<PackedFoodItem> packedItems) {
		this.packedItems = packedItems;
	}

	public Boolean getPackingStatus() {
		return packingStatus;
	}

	public void setPackingStatus(Boolean packingStatus) {
		this.packingStatus = packingStatus;
	}

}
