package foodbank.donor.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
public class Donor {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "donor_seq_gen")
	@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "donor_seq_gen", sequenceName = "donor_sequence")
	private Long id;
	
	private String name;
	
	@OneToMany(mappedBy = "donor", 
			cascade = CascadeType.ALL, 
			fetch = FetchType.LAZY)
	private List<DonatedNPFoodItem> nonperishableDonations = new ArrayList<DonatedNPFoodItem>();
	//private List<PerishableDonation> perishableDonations = new ArrayList<PerishableDonation>();
	
	protected Donor() {}
	
	public Donor(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<DonatedNPFoodItem> getNonperishableDonations() {
		return nonperishableDonations;
	}

	public void setNonperishableDonations(List<DonatedNPFoodItem> nonperishableDonations) {
		this.nonperishableDonations = nonperishableDonations;
	}
	
}
