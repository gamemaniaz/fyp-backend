package foodbank.request.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import foodbank.beneficiary.entity.Beneficiary;
import foodbank.inventory.entity.FoodItem;

@Entity
@Table(name = "request")
@EntityListeners(AuditingEntityListener.class)
public class Request {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "req_seq_gen")
	@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "req_seq_gen", sequenceName = "request_sequence")
	private Long id;
	
	@ManyToOne(//cascade = CascadeType.ALL, 
			fetch = FetchType.LAZY, 
			optional = true, targetEntity = Beneficiary.class)
	@JoinColumn(name = "beneficiary_user_id", unique = false)
	@JsonIgnore
	private Beneficiary beneficiary;
	
	@ManyToOne(//cascade = CascadeType.ALL,
			fetch = FetchType.EAGER,
			optional = false, targetEntity = FoodItem.class)
	@JoinColumn(name = "inventory_id", unique = false, nullable = true)
	private FoodItem foodItem;
	
	@CreatedDate
	@Temporal(TemporalType.DATE)
	private Date requestCreationDate;
	
	private Integer requestedQuantity;

	protected Request() {}
	
	public Request(Beneficiary beneficiary, FoodItem foodItem, Integer requestedQuantity) {
		this.beneficiary = beneficiary;
		this.foodItem = foodItem;
		this.requestedQuantity = requestedQuantity;
	}

	public Integer getRequestedQuantity() {
		return requestedQuantity;
	}

	public void setRequestedQuantity(Integer requestedQuantity) {
		this.requestedQuantity = requestedQuantity;
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

	public FoodItem getFoodItem() {
		return foodItem;
	}

	public void setFoodItem(FoodItem foodItem) {
		this.foodItem = foodItem;
	}

	public Date getRequestCreationDate() {
		return requestCreationDate;
	}

	public void setRequestCreationDate(Date requestCreationDate) {
		this.requestCreationDate = requestCreationDate;
	}
	
	/*
	public String getRequestCreationDate() {
		return requestCreationDate;
	}

	public void setRequestCreationDate(String requestCreationDate) {
		this.requestCreationDate = requestCreationDate;
	}
	*/

}
