package foodbank.login.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginDTO {
	
	@NotNull
	@JsonProperty("username")
	private String username;
	
	@NotNull
	@JsonProperty("password")
	private String password;
	
	@JsonProperty("usertype")
	private String usertype;
	
	public LoginDTO(@JsonProperty("username") String username, @JsonProperty("password") String password, @JsonProperty("usertype") String usertype) {
		this.username = username;
		this.password = password;
		this.usertype = usertype;
	}

	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUsertype() {
		return usertype;
	}
	
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	
}
