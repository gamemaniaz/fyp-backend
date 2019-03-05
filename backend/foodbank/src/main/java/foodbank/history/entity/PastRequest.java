package foodbank.history.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import foodbank.inventory.entity.FoodItem;

@Entity
public class PastRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "req_hist_item_seq_gen")
	@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "req_hist_item_seq_gen", sequenceName = "request_history_item_sequence")
	private Long id;
	
	@ManyToOne(cascade = CascadeType.ALL,
			fetch = FetchType.LAZY,
			optional = true, targetEntity = FoodItem.class)
	@JoinColumn(name = "inventory_id", nullable = false)
	private FoodItem previouslyRequestedItem;
	
	@ManyToOne
	@JoinColumn(name = "request_history_id")
	private RequestHistory requestHistory;
	
	private Integer requestedQuantity;
	private Integer allocatedQuantity;

	@Temporal(TemporalType.DATE)
	private Date requestCreationDate;

	protected PastRequest() {}

	public PastRequest(FoodItem previouslyRequestedItem, Integer requestedQuantity,
			Integer allocatedQuantity, Date requestCreationDate) {
		this.previouslyRequestedItem = previouslyRequestedItem;
		this.requestedQuantity = requestedQuantity;
		this.allocatedQuantity = allocatedQuantity;
		this.requestCreationDate = requestCreationDate;
	}

	public Date getRequestCreationDate() {
		return requestCreationDate;
	}

	public void setRequestCreationDate(Date requestCreationDate) {
		this.requestCreationDate = requestCreationDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FoodItem getPreviouslyRequestedItem() {
		return previouslyRequestedItem;
	}

	public void setPreviouslyRequestedItem(FoodItem previouslyRequestedItem) {
		this.previouslyRequestedItem = previouslyRequestedItem;
	}

	public RequestHistory getRequestHistory() {
		return requestHistory;
	}

	public void setRequestHistory(RequestHistory requestHistory) {
		this.requestHistory = requestHistory;
	}

	public Integer getRequestedQuantity() {
		return requestedQuantity;
	}

	public void setRequestedQuantity(Integer requestedQuantity) {
		this.requestedQuantity = requestedQuantity;
	}

	public Integer getAllocatedQuantity() {
		return allocatedQuantity;
	}

	public void setAllocatedQuantity(Integer allocatedQuantity) {
		this.allocatedQuantity = allocatedQuantity;
	}
	
}
