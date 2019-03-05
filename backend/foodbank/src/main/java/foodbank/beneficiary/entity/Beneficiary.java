package foodbank.beneficiary.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import foodbank.allocation.entity.Allocation;
import foodbank.history.entity.RequestHistory;
import foodbank.packing.entity.PackingList;
import foodbank.request.entity.Request;
import foodbank.user.entity.User;

@Entity
@Table(name = "beneficiary")
/*
@DiscriminatorValue(value = "beneficiary")
@PrimaryKeyJoinColumn(name = "user_id")
*/
@JsonSerialize
//@PrimaryKeyJoinColumn(name = "user_id")
public class Beneficiary {
	
	@Id
	//@GeneratedValue//(strategy = GenerationType.SEQUENCE, generator = "user_seq_gen")
	//@Column(name = "user_id")
	private Long id;
	
	private int numBeneficiary;
	private String address;
	private double score;
	private String contactPerson;
	private String contactNumber;
	private String memberType;
	private boolean hasTransport;
	
	@OneToOne(cascade = CascadeType.ALL, 
			fetch = FetchType.LAZY,  
			orphanRemoval = true, targetEntity = User.class)
	//@JoinColumn(name = "id", nullable = false)
	@MapsId
	@JsonIgnore
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToOne(mappedBy = "beneficiary", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private Allocation allocation;
	
	@OneToMany(mappedBy = "beneficiary", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<RequestHistory> requestHistories;
	
	@OneToMany(mappedBy = "beneficiary", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<PackingList> packingList;
	
	/*
	@OneToMany(mappedBy = "billingOrganization")
	private List<Invoice> billingList;
	*/
	/*
	@OneToMany(mappedBy = "receivingOrganization")
	private List<Invoice> receivingList;
	*/
	
	public List<PackingList> getPackingList() {
		return packingList;
	}

	public void setPackingList(List<PackingList> packingList) {
		this.packingList = packingList;
	}

	public Allocation getAllocation() {
		return allocation;
	}

	public void setAllocation(Allocation allocation) {
		this.allocation = allocation;
	}

	@OneToMany(mappedBy = "beneficiary", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Request> requests;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	protected Beneficiary() {}

	/*
	public Beneficiary(String username, String password, String usertype,
			String name, String email, int numBeneficiary, String address, 
			double score, String contactPerson, String contactNumber,
			String memberType, boolean hasTransport) {
		if(this.user == null) {
			this.user = new User(username, password, usertype, name, email);
		}
		this.numBeneficiary = numBeneficiary;
		this.address = address;
		this.score = score;
		this.contactPerson = contactPerson;
		this.contactNumber = contactNumber;
		this.memberType = memberType;
		this.hasTransport = hasTransport;
	}
	*/
	
	public int getNumBeneficiary() {
		return numBeneficiary;
	}
	
	public Beneficiary(int numBeneficiary, String address, double score, String contactPerson, String contactNumber,
			String memberType, boolean hasTransport) {
		this.numBeneficiary = numBeneficiary;
		this.address = address;
		this.score = score;
		this.contactPerson = contactPerson;
		this.contactNumber = contactNumber;
		this.memberType = memberType;
		this.hasTransport = hasTransport;
	}

	public void setNumBeneficiary(int numBeneficiary) {
		this.numBeneficiary = numBeneficiary;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public double getScore() {
		return score;
	}
	
	public void setScore(double score) {
		this.score = score;
	}
	
	public String getContactPerson() {
		return contactPerson;
	}
	
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	
	public String getContactNumber() {
		return contactNumber;
	}
	
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	
	public String getMemberType() {
		return memberType;
	}
	
	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}
	
	public boolean getTransportationStatus() {
		return hasTransport;
	}
	
	public void setTransportationStatus(boolean hasTransport) {
		this.hasTransport = hasTransport;
	}

	public List<Request> getRequests() {
		return requests;
	}

	public void setRequests(List<Request> requests) {
		this.requests = requests;
	}

}
