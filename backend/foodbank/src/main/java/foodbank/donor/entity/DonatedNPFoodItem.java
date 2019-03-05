package foodbank.donor.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Temporal;

import foodbank.inventory.entity.FoodItem;

@Entity
@Table(name = "nonperishable_donations")
public class DonatedNPFoodItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "np_don_seq_gen")
	@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "np_don_seq_gen", sequenceName = "nonperishable_donations_sequence")
	private Long id;
	
	@ManyToOne(cascade = CascadeType.ALL, 
			fetch = FetchType.LAZY, 
			optional = true, targetEntity = FoodItem.class)
	@JoinColumn(name = "inventory_id", nullable = false)
	private FoodItem donatedItem;
	
	private Integer donatedQuantity;
	
	@Temporal(TemporalType.DATE)
	private Date donationDate;
	
	@OneToOne(cascade = CascadeType.ALL,
			fetch = FetchType.LAZY,
			orphanRemoval = true,
			optional = true, targetEntity = Donor.class)
	@JoinColumn(name = "donor_id")
	@JsonIgnore
	private Donor donor;
	
	protected DonatedNPFoodItem() {}

	public DonatedNPFoodItem(FoodItem donatedItem, Integer donatedQuantity, Date donationDate) {
		this.donatedItem = donatedItem;
		this.donatedQuantity = donatedQuantity;
		this.donationDate = donationDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FoodItem getDonatedItem() {
		return donatedItem;
	}

	public void setDonatedItem(FoodItem donatedItem) {
		this.donatedItem = donatedItem;
	}

	public Integer getDonatedQuantity() {
		return donatedQuantity;
	}

	public void setDonatedQuantity(Integer donatedQuantity) {
		this.donatedQuantity = donatedQuantity;
	}

	public Date getDonationDate() {
		return donationDate;
	}

	public void setDonationDate(Date donationDate) {
		this.donationDate = donationDate;
	}

	public Donor getDonor() {
		return donor;
	}

	public void setDonor(Donor donor) {
		this.donor = donor;
	}

}
