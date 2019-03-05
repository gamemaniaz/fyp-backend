package foodbank.login.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponseDTO {
	
	public enum Status {
		SUCCESS, FAIL
	}
	
	public enum Usertype {
		ADMIN, VOLUNTEER, BENEFICIARY
	}
	
	@NotNull
	@JsonProperty("status")
	private Status status;
	
	@JsonProperty("message")
	private String message;
	
	@JsonProperty("result")
	private String result;
	
	@JsonProperty("usertype")
	private Usertype usertype;
	
	public LoginResponseDTO(@JsonProperty("status") Status status, @JsonProperty("message") String message, @JsonProperty("usertype") Usertype usertype) {
		super();
		this.status = status;
		this.message = message;
		this.usertype = usertype;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public Usertype getUsertype() {
		return usertype;
	}
	
	public void setUsertype(Usertype usertype) {
		this.usertype = usertype;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}	

}
