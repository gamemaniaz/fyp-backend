package foodbank.inventory.entity;

import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import foodbank.allocation.entity.AllocatedFoodItem;
import foodbank.donor.entity.DonatedNPFoodItem;
import foodbank.history.entity.PastRequest;
import foodbank.packing.entity.PackedFoodItem;
import foodbank.request.entity.Request;

@Entity
@Table(name = "inventory")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "item_cache")
public class FoodItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "inv_seq_gen", sequenceName = "inventory_sequence")
	private Long id;
	
	private String category;
	private String classification;
	private String description;
	private Integer quantity;
	private Double value = 0.0;
	
	@OneToMany(mappedBy = "foodItem")//, orphanRemoval = true)
	private List<Request> requests;
	
	@OneToMany(mappedBy = "allocatedFoodItem")//, orphanRemoval = true)
	private List<AllocatedFoodItem> allocatedItems;
	
	@OneToMany(mappedBy = "previouslyRequestedItem")//, orphanRemoval = true)
	private List<PastRequest> previouslyRequestedItem;
	
	@OneToMany(mappedBy = "packedFoodItem")//, orphanRemoval = true)
	private List<PackedFoodItem> packedItems;
	
	@OneToMany(mappedBy = "scannedItem")//, orphanRemoval = true)
	private List<Barcode> barcodes;
	
	@OneToMany(mappedBy = "donatedItem")
	private List<DonatedNPFoodItem> donatedItems;
	
	protected FoodItem() {}
	
	/*
	public FoodItem(Long id, String category, String classification, String description, Integer quantity,
			Double value) {
		this.id = id;
		this.category = category;
		this.classification = classification;
		this.description = description;
		this.quantity = quantity;
		this.value = value;
	}
	*/

	public FoodItem(String category, String classification, String description, Integer quantity, Double value) {
		this.category = category;
		this.classification = classification;
		this.description = description;
		this.quantity = quantity;
		this.value = value;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}
	
}
