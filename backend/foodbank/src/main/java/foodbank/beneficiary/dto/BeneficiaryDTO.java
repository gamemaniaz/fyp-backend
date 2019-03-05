package foodbank.beneficiary.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public class BeneficiaryDTO {
	
	// Core Details required for a User
	@NotNull
	@JsonProperty("username")
	private String username;
	
	@NotNull
	private String password;
	
	private final String usertype = "beneficiary";
	
	@NotNull
	@JsonProperty("name")
	private String name;
	
	@NotNull
	@JsonProperty("email")
	private String email;
	
	// Core Details required for a Beneficiary
	@NotNull
	@JsonProperty("numBeneficiary")
	private Integer numBeneficiary;
	
	@NotNull
	@JsonProperty("address")
	private String address;
	
	@NotNull
	@JsonProperty("score")
	private Double score;
	
	@NotNull
	@JsonProperty("contactPerson")
	private String contactPerson;
	
	@NotNull
	@JsonProperty("contactNumber")
	private String contactNumber;
	
	@NotNull
	@JsonProperty("memberType")
	private String memberType;
	
	@NotNull
	@JsonProperty("hasTransport")
	private Boolean hasTransport;
	
	protected BeneficiaryDTO() {}

	public BeneficiaryDTO(String username, String name, String email, Integer numBeneficiary, String address,
			Double score, String contactPerson, String contactNumber, String memberType, Boolean hasTransport) {
		this.username = username;
		this.name = name;
		this.email = email;
		this.numBeneficiary = numBeneficiary;
		this.address = address;
		this.score = score;
		this.contactPerson = contactPerson;
		this.contactNumber = contactNumber;
		this.memberType = memberType;
		this.hasTransport = hasTransport;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}
	
	@JsonProperty("password")
	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsertype() {
		return usertype;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getNumBeneficiary() {
		return numBeneficiary;
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

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
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

	public Boolean getHasTransport() {
		return hasTransport;
	}

	public void setHasTransport(Boolean hasTransport) {
		this.hasTransport = hasTransport;
	}

	public void setNumBeneficiary(Integer numBeneficiary) {
		this.numBeneficiary = numBeneficiary;
	}

	public void setScore(Double score) {
		this.score = score;
	}

}
