package foodbank.user.entity;

import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;

import foodbank.beneficiary.entity.Beneficiary;
import foodbank.security.model.Role;

@Entity
//@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "user")
//@DiscriminatorValue(value = "user")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "user_cache")
public class User  {
		
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq_gen")
	@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "user_seq_gen", sequenceName = "user_sequence")
	@Column(name = "id", nullable = false)
	private Long id;
	
	@Column(unique = true)
	private String username;
	private String password;
	private String usertype;
	private String name;
	
	@Column(unique = true)
	private String email;
	
	@OneToOne(mappedBy = "user", 
			cascade = CascadeType.ALL, 
			fetch = FetchType.LAZY, 
			optional = true, 
			orphanRemoval = true)
	@JsonIgnore
	private Beneficiary beneficiary;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", 
		joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
		inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	//@JoinColumn(name = "role_id")
	@JsonIgnore
	private Role role;
	
	protected User() {}
	
	public User(String username, String password, String usertype, String name, String email) {
		this.username = username;
		this.password = password;
		this.usertype = usertype;
		this.name = name;
		this.email = email;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
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
	
	public Beneficiary getBeneficiary() {
		return beneficiary;
	}

	public void setBeneficiary(Beneficiary beneficiary) {
		this.beneficiary = beneficiary;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	/*
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	*/
	
	
}
