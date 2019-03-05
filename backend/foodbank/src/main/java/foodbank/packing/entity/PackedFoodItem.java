package foodbank.packing.entity;

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
public class PackedFoodItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "packed_fi_seq_gen")
	@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "packed_fi_seq_gen", sequenceName = "packed_food_item_sequence")
	private Long id;
	
	@ManyToOne(cascade = CascadeType.ALL, 
			fetch = FetchType.LAZY, 
			optional = true, targetEntity = FoodItem.class)
	@JoinColumn(name = "inventory_id", nullable = false)
	private FoodItem packedFoodItem;
	
	private Integer allocatedQuantity;
	
	private Integer packedQuantity;
	
	@ManyToOne
	@JoinColumn(name = "packing_list_id")
	private PackingList packingList;

	protected PackedFoodItem() {}

	public PackedFoodItem(FoodItem packedFoodItem, Integer allocatedQuantity) {
		this(packedFoodItem, allocatedQuantity, Integer.valueOf(0));
	}
	
	public PackedFoodItem(FoodItem packedFoodItem, Integer allocatedQuantity, Integer packedQuantity) {
		this.packedFoodItem = packedFoodItem;
		this.allocatedQuantity = allocatedQuantity;
		this.packedQuantity = packedQuantity;
	}

	public PackedFoodItem(Long id, FoodItem packedFoodItem, Integer allocatedQuantity, Integer packedQuantity,
			PackingList packingList) {
		this.id = id;
		this.packedFoodItem = packedFoodItem;
		this.allocatedQuantity = allocatedQuantity;
		this.packedQuantity = packedQuantity;
		this.packingList = packingList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FoodItem getPackedFoodItem() {
		return packedFoodItem;
	}

	public void setPackedFoodItem(FoodItem packedFoodItem) {
		this.packedFoodItem = packedFoodItem;
	}
	
	public Integer getAllocatedQuantity() {
		return allocatedQuantity;
	}

	public void setAllocatedQuantity(Integer allocatedQuantity) {
		this.allocatedQuantity = allocatedQuantity;
	}

	public Integer getPackedQuantity() {
		return packedQuantity;
	}

	public void setPackedQuantity(Integer packedQuantity) {
		this.packedQuantity = packedQuantity;
	}

	public PackingList getPackingList() {
		return packingList;
	}

	public void setPackingList(PackingList packingList) {
		this.packingList = packingList;
	}
	
}
