package foodbank.util;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseDTO {

	public enum Status {
		SUCCESS, FAIL
	}
	
	@NotNull
	@JsonProperty("status")
	private Status status;
	
	@NotNull
	@JsonProperty("result")
	private Object result;
	
	@JsonProperty("message")
	private String message;
	
	public ResponseDTO(@JsonProperty("status") Status status, @JsonProperty("result") Object result, @JsonProperty("message") String message) {
		this.status = status;
		this.result = result;
		this.message = message;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	public Object getResult() {
		return result;
	}
	
	public void setResult(Object result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
