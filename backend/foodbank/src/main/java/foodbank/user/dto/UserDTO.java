package foodbank.user.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDTO {
	
	@NotNull
	@JsonProperty("username")
	private String username;
	
	@NotNull
	@JsonProperty("password")
	private String password;
	
	@NotNull
	@JsonProperty("usertype")
	private String usertype;
	
	@NotNull
	@JsonProperty("name")
	private String name;
	
	@NotNull
	@JsonProperty("email")
	private String email;
	
	public UserDTO(@JsonProperty("username") String username, @JsonProperty("password") String password, 
			@JsonProperty("usertype") String usertype, @JsonProperty("name") String name, @JsonProperty("email") String email) {
		this.username = username;
		this.password = password;
		this.usertype = usertype;
		this.name = name;
		this.email = email;
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
	
}
