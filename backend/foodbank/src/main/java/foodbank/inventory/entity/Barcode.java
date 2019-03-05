package foodbank.inventory.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Barcode {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "barcode_seq_gen")
	@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "barcode_seq_gen", sequenceName = "barcode_sequence")
	private Long id;
	
	@Column(name = "barcode")
	private String barcode;
	
	@ManyToOne(cascade = CascadeType.ALL, 
			fetch = FetchType.LAZY, 
			optional = true, targetEntity = FoodItem.class)
	@JoinColumn(name = "inventory_id", nullable = false)
	private FoodItem scannedItem;
	
	protected Barcode() {}

	public Barcode(String barcode, FoodItem scannedItem) {
		this.barcode = barcode;
		this.scannedItem = scannedItem;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public FoodItem getScannedItem() {
		return scannedItem;
	}

	public void setScannedItem(FoodItem scannedItem) {
		this.scannedItem = scannedItem;
	}
	
}
