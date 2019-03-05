package foodbank.allocation.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import foodbank.inventory.entity.FoodItem;

@Entity
public class AllocatedFoodItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "all_fi_seq_gen")
	@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "all_fi_seq_gen", sequenceName = "allocated_food_item_sequence")
	private Long id;
	
	@ManyToOne(cascade = CascadeType.ALL, 
			fetch = FetchType.LAZY, 
			optional = true, targetEntity = FoodItem.class)
	@JoinColumn(name = "inventory_id", nullable = true)
	private FoodItem allocatedFoodItem;
	
	private Integer requestedQuantity;
	
	private Integer allocatedQuantity;
	
	@ManyToOne
	@JoinColumn(name = "allocation_id")
	private Allocation allocation;
	
	protected AllocatedFoodItem() {}

	public AllocatedFoodItem(FoodItem foodItem, Integer requestedQuantity, Integer allocatedQuantity) {
		this.allocatedFoodItem = foodItem;
		this.requestedQuantity = requestedQuantity;
		this.allocatedQuantity = allocatedQuantity;
	}

	public FoodItem getFoodItem() {
		return allocatedFoodItem;
	}

	public void setFoodItem(FoodItem foodItem) {
		this.allocatedFoodItem = foodItem;
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
	
	public Allocation getAllocation() {
		return allocation;
	}

	public void setAllocation(Allocation allocation) {
		this.allocation = allocation;
	}

}
